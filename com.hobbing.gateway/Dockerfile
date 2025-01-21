# Step 1: Base image for building the application
FROM gradle:7.6-jdk17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and configuration files first for caching dependencies
COPY gradlew gradlew
COPY gradle gradle

# Copy the rest of the project files
COPY . .

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Build the project using Gradle and skip tests
RUN ./gradlew build -x test

# Step 2: Base image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar gateway.jar

# Expose the application port
EXPOSE 19006

# Run the application
CMD ["java", "-jar", "gateway.jar"]

#FROM openjdk:17-jdk-slim
#
#VOLUME /app
#
#ARG JAR_FILE=com.hobbing.gateway/build/libs/gateway.jar
#
##COPY ${JAR_FILE} hobbing.jar
#COPY gradlew /app/
#COPY gradle /app/gradle/
#COPY build.gradle /app/
#
#COPY com.hobbing.gateway/build.gradle /app/gateway/
#COPY com.hobbing.gateway/src /app/gateway/src
#COPY ${JAR_FILE} /gateway.jar
#
#COPY settings.gradle /app/
#
## Ensure gradlew is executable
#RUN chmod +x /app/gradlew
#
## Build the application
##RUN ./gradlew build -x test --stacktrace
#
##RUN cp ${JAR_FILE} /gateway.jar
#
#EXPOSE 19006
#
#ENTRYPOINT ["java", "-jar", "/gateway.jar"]