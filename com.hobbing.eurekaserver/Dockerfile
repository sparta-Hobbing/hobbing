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
COPY --from=builder /app/build/libs/*.jar eurekaserver.jar

# Expose the application port
EXPOSE 19000

# Run the application
CMD ["java", "-jar", "eurekaserver.jar"]

#FROM openjdk:17-jdk-slim
#
#VOLUME /app
#
#ARG JAR_FILE=/app/eurekaserver/build/libs/eurekaserver.jar
#
##COPY ${JAR_FILE} hobbing.jar
#COPY gradlew /app/
#COPY gradle /app/gradle/
#COPY build.gradle /app/
#
#COPY com.hobbing.eurekaserver/build.gradle /app/eurekaserver/
#COPY com.hobbing.eurekaserver/src /app/eurekaserver/src
#
#COPY settings.gradle /app/
#
## Ensure gradlew is executable
#RUN chmod +x /app/gradlew
#
## Build the application
#RUN ./app/gradlew build
##BUILD /app/gradlew
##COPY ${JAR_FILE} /eurekaserver.jar
#
#EXPOSE 19000
#
#ENTRYPOINT ["java", "-jar", "/app/eurekaserver/build/libs/eurekaserver.jar"]

#FROM openjdk:17-jdk-slim
#
#VOLUME /app
#
#ARG JAR_FILE=./com.hobbing.eurekaserver/build/libs/eurekaserver.jar
#
#COPY gradlew /app/eurekaserver/
#COPY gradle /app/eurekaserver/
#COPY build.gradle /app/eurekaserver/
#COPY src /app/eurekaserver/src
#
#COPY settings.gradle /app/eurekaserver/
#
## Ensure gradlew is executable
#RUN chmod +x /app/eurekaserver/gradlew
#
##Clean with Gradle
##RUN clean
#
## Build the application
#RUN /app/eurekaserver/gradlew
#
##COPY com.hobbing.eurekaserver/gradlew /app/eurekaserver/
##COPY com.hobbing.eurekaserver/gradle /app/eurekaserver/
##COPY eurekaserver/build.gradle /app/eurekaserver/
#
#
## Ensure gradlew is executable
##RUN chmod +x /app/eurekaserver/gradlew
#
##Clean with Gradle
##RUN /app/eurekaserver/gradlew clean
#
## Build the application
##RUN /app/eurekaserver/gradlew build -x test --stacktrace
#
#COPY /app/eurekaserver/build/libs/*.jar /eurekaserver.jar
#
#EXPOSE 19000
#
#ENTRYPOINT ["java", "-jar", "/eurekaserver.jar"]