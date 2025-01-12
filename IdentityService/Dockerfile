FROM ubuntu:latest
LABEL authors="cunnc"

ENTRYPOINT ["top", "-b"]

# Sử dụng image OpenJDK làm base image
FROM openjdk:21-jdk-slim

# Tạo thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR từ máy host vào container
COPY target/IdentityService-0.0.1-SNAPSHOT.jar app.jar

# Expose cổng mà ứng dụng Spring Boot sẽ chạy
EXPOSE 8080

# Lệnh khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
