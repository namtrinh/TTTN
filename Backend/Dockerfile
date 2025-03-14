
FROM maven:3.9.8-amazoncorretto-22 AS build
FROM amazoncorretto:22

ARG JAR_FILE=target/Backend-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
