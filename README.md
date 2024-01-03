# Introduction 
This project implements the invoicing API.
This service is configured to listen on a JMS queue (Solace) for any incoming requests, process it and puts the results back on a response queue. 
The project configuration options are externalize and it will be read from spring-cloud-config server.
Please use the application.properties file to configure the config server.

# Getting Started
This is based on Java SpringBoot and you would need, Java (>11) and Maven to build this project.
Once you do a 'mvn clean install' it will download all the necessary dependencies.

# Build and Test
You would need to setup JAVA_HOME, then do a maven build and push this to container registry.
You can use following commands to build and push

export JAVA_HOME=/Users/myuser/Downloads/jdk-17.0.2.jdk/Contents/Home
mci-ts
sudo docker buildx build --platform linux/amd64 -t invoiceservice .
sudo docker tag invoiceservice integrationconreg01.azurecr.io/integration/invoiceservice:1.9
sudo docker push integrationconreg01.azurecr.io/integration/invoiceservice:1.9
kubectl create deployment invoiceservice --image=integrationconreg01.azurecr.io/integration/invoiceservice:1.9

