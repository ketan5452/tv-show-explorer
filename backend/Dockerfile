# Stage 1: Build the JAR using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Use a minimal JDK image to run the app
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/tvshowapp-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/tvtitles.txt tvtitles.txt

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
