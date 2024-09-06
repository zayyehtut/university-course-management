# Use a suitable base image with Java and Maven
FROM openjdk:21-jdk-slim-buster AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project's pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the project using Maven
RUN mvn clean package

# Use a smaller base image for the final image
FROM openjdk:21-jre-slim-buster

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/enrollment-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot application will run on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "app.jar"]