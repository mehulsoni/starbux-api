# Starbux Backend Api

This is a starbux api Starbux providing a REST API to a Front end UI.

The entire application is contained written in Java and using spring boot/ docker for running it into container

`build-script.sh` and `run-test.sh` runs a simplistic test and generates the API
documentation below.

`run-docker.sh` generate docker image and run it on 6002
## Port

6002

## Required

Java 11
Postgres
Docker
Gradle


## Run the app

 To build the executable jar you can execute the following command:

$ ./gradlew bootJar
The executable jar is located in the build/libs directory and you can run it by executing the following command:

$ java -jar build/libs/*.jar
Another way to run the application is by executing the following Gradle command:

$ ./gradlew bootRun

## Run the tests

    ./run-tests.sh

## REST API

The REST API to the example app is described below.

http://<ip-address>:6002/api/docs

## Docker Push Command 

Retrieve an authentication token and authenticate your Docker client to your registry.
Use the AWS CLI:
## aws ecr get-login-password --region eu-west-3 | docker login --username AWS --password-stdin 496412940659.dkr.ecr.eu-west-3.amazonaws.com/starbux

Note: If you receive an error using the AWS CLI, make sure that you have the latest version of the AWS CLI and Docker installed.
Build your Docker image using the following command. For information on building a Docker file from scratch see the instructions here . You can skip this step if your image is already built:
## docker build -t starbux .

After the build completes, tag your image so you can push the image to this repository:
## docker tag starbux:latest 496412940659.dkr.ecr.eu-west-3.amazonaws.com/starbux:latest

Run the following command to push this image to your newly created AWS repository:
## docker push 496412940659.dkr.ecr.eu-west-3.amazonaws.com/starbux:latest


