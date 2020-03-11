./gradlew clean
./gradlew compileJava
./gradlew compileTestJava
./gradlew migrateLocal
./gradlew build
./gradlew bootjar
./gradlew jibDockerBuild --image=springio/starbux:latest
docker run -e "SPRING_PROFILES_ACTIVE=LOCAL" -t springio/starbux:latest