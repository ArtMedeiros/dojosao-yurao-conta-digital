<<<<<<< HEAD
FROM amazoncorretto:11.0.12
COPY target/*.jar /usr/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]
=======
## Builder Image
## With correto jdk version
FROM maven:3.8.3-amazoncorretto-11 AS builder
## FROM maven:3.8.3-openjdk-11 AS builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -Dmaven.test.skip=true

## Runner Image
## With correto jdk version
FROM amazoncorretto:11.0.12-alpine
## FROM openjdk:11-alpine
COPY --from=builder /usr/src/app/target/*.jar /usr/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]
>>>>>>> origin/main
