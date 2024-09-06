# Use maven:3.9.6-eclipse-temurin-21-alpine as base image
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Set the working directory in the container
WORKDIR /app

# Copy the parent pom.xml file
COPY pom.xml .

# Copy the student-service pom.xml file
COPY student-service/pom.xml student-service/

# Copy the common module
COPY common common/

# Copy the student-service source code
COPY student-service/src student-service/src

# Modify the parent pom.xml to remove course-service and enrollment-service modules
RUN sed -i '/<module>course-service<\/module>/d' pom.xml && \
    sed -i '/<module>enrollment-service<\/module>/d' pom.xml

# Package the application
RUN mvn clean package -pl student-service -am -DskipTests

# Start with a base image containing Java runtime
FROM eclipse-temurin:21-jre-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/student-service/target/student-service-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]