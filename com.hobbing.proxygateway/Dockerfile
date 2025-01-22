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

RUN ./gradlew clean
# Build the project using Gradle and skip tests
RUN ./gradlew build -x test

# Step 2: Base image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/com.hobbing.proxygateway/build/libs/*.jar proxygateway.jar

# Expose the application port
EXPOSE 19005

# Run the application
CMD ["java", "-jar", "proxygateway.jar"]

#FROM openjdk:17-jdk-slim
#
#VOLUME /app
#
#ARG JAR_FILE=com.hobbing.proxygateway/build/libs/proxygateway.jar
#
##COPY ${JAR_FILE} hobbing.jar
#COPY gradlew /app/
#COPY gradle /app/gradle/
#COPY build.gradle /app/
#
#COPY com.hobbing.proxygateway/build.gradle /app/proxygateway/
#COPY com.hobbing.proxygateway/src /app/proxygateway/src
#COPY ${JAR_FILE} /proxygateway.jar
#
#COPY settings.gradle /app/
#
## Ensure gradlew is executable
#RUN chmod +x /app/gradlew
#
## Build the application
##RUN ./gradlew build -x test --stacktrace
#
##RUN cp ${JAR_FILE} /proxygateway.jar
#
#EXPOSE 19005
#
#ENTRYPOINT ["java", "-jar", "/proxygateway.jar"]