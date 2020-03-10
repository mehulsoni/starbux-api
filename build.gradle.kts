import com.google.cloud.tools.jib.api.ImageFormat
import org.flywaydb.gradle.task.FlywayMigrateTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    java
    idea
    jacoco
//    checkstyle
    `maven-publish`
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("com.google.cloud.tools.jib") version "2.0.0"
    id("org.flywaydb.flyway") version "6.2.3"
    id("org.sonarqube") version "2.8"
}

group = "com.starbux.domain"
version = "0.1.0-SNAPSHOT"

// ---------------------------------------------------------------------------------------------------------------------
// Extra properties
// Rollbar
val rollbarVersion by extra("1.5.2")

// Spring
val springPluginVersion by extra("1.2.0.RELEASE")

// Springdoc
val springdocVersion by extra("1.2.31")

// Jacoco
val jacocoToolVersion by extra("0.8.4")
val minimumOverallTestCoverage by extra(0.70.toBigDecimal())
val minimumClassTestCoverage by extra(0.65.toBigDecimal())

// Checkstyle
//val checkstyleVersion by extra("8.23")

// Mapstruct
val mapstructVersion by extra("1.3.1.Final")

// Internal maven repository
val mgReleaseArtifactoryUrl: String by project
val mgSnapshotArtifactoryUrl: String by project
val mgReleaseArtifactoryUserName: String by project
val mgReleaseArtifactoryPassword: String by project

val logstashEncoderVersion = "6.3"

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://repo.spring.io/milestone") }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("net.sf.jasperreports:jasperreports:6.11.0")
    implementation("com.lowagie:itext:2.1.7")
    implementation("org.springframework.plugin:spring-plugin-core:$springPluginVersion")
    implementation("org.springdoc:springdoc-openapi-ui:$springdocVersion")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    implementation("org.apache.commons:commons-collections4:4.4")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    runtimeOnly("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.rollbar:rollbar-logback:$rollbarVersion")

//    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit.vintage", "junit-vintage-engine")
    }
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.1")
    testImplementation("org.testcontainers:postgresql:1.11.4")
    testImplementation("org.testcontainers:junit-jupiter:1.12.2")
    testCompile("org.flywaydb:flyway-core")
}

//checkstyle {
//    toolVersion = checkstyleVersion
//    configFile = file("$projectDir/config/checkstyle/google_checks_8_23.xml")
//}

jacoco {
    toolVersion = jacocoToolVersion
}

tasks.withType<JavaCompile> {
    options.compilerArgs = mutableListOf("-Amapstruct.defaultComponentModel=spring")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        showStandardStreams = true
        events("started", "skipped", "failed")
        exceptionFormat = TestExceptionFormat.FULL
    }

    finalizedBy("jacocoTestReport", "jacocoTestCoverageVerification")
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "BUNDLE"
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = minimumOverallTestCoverage
            }

            excludes = listOf(
                    "com.Starbux.StartBuxApplication",
                    "*Dto*",
                    "*entity*",
                    "*mapper*",
                    "*util*",
                    "*exception*",
                    "*common*",
                    "*configuration*",
                    "*SwaggerController*"

            )
        }

        rule {
            element = "CLASS"
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = minimumClassTestCoverage
            }

            excludes = listOf(
                    "com.Starbux.StartBuxApplication",
                    "*Dto*",
                    "*entity*",
                    "*mapper*",
                    "*util*",
                    "*exception*",
                    "*common*",
                    "*configuration*",
                    "*SwaggerController*"
            )
        }
    }
}

flyway {
    encoding = "UTF-8"
}

tasks.create<FlywayMigrateTask>("migrateLocal") {
    configFiles = arrayOf("doc/flyway/flyway_LOCAL.conf")
}

tasks.create<FlywayMigrateTask>("migrateDev") {
    configFiles = arrayOf("doc/flyway/flyway_DEV.conf")
}


jib {
    container {
        format = ImageFormat.Docker
        creationTime = "USE_CURRENT_TIMESTAMP"
    }
}
