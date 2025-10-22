# Use official Maven image with Eclipse Temurin JDK 17
# Stage 1: Build the Spring Boot app
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Copy source code
COPY src ./src

# Give mvnw execute permission
RUN chmod +x mvnw

# Build the app and skip tests for faster build
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the Spring Boot app
FROM eclipse-temurin:17-jdk-jammy

# Copy the jar from the build stage
COPY --from=build /app/target/todolist-backend-0.0.1-SNAPSHOT.jar /usr/local/lib/todolist-backend.jar

# Expose the port Spring Boot will run on
EXPOSE 8080

# Default command to run the jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/todolist-backend.jar"]
