FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} starbux.jar
EXPOSE 6002
ENTRYPOINT ["java","-jar","/starbux.jar"]