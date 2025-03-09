# Use latest Ubuntu image as the build stage
FROM ubuntu:latest AS build

# Update and install OpenJDK 17
RUN apt-get update && apt-get install -y openjdk-17-jdk maven

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use a slim OpenJDK image for the runtime
FROM openjdk:17-jdk-slim

# Expose the application port
EXPOSE 8080

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
