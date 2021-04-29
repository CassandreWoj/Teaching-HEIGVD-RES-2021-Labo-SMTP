#!/bin/bash

# Ask maven to build the executable jar file from the source files
mvn clean install --file ../SMTP/pom.xml

# Copy the executable jar file in the current directory
cp ../SMTP/target/SMTP-1.0-SNAPSHOT.jar .

cp -r ../SMTP/config .

# Build the Docker image 
docker build --tag gdcw/smtpprank .
