FROM openjdk:latest

COPY build/libs/*.jar starbux.jar
EXPOSE 6002
ENTRYPOINT ["java", "-Dspring.profiles.active=LOCAL","-jar","/starbux.jar"]