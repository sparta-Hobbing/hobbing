FROM openjdk:17-jdk-slim

VOLUME /app

ARG JAR_FILE=./com.hobbing.eurekaserver/build/libs/eurekaserver.jar

COPY gradlew /app/
COPY gradle /app/
COPY build.gradle /app/

COPY settings.gradle /app/

# Ensure gradlew is executable
RUN chmod +x /app/gradlew

#Clean with Gradle
RUN /app/gradlew clean

# Build the application
RUN /app/gradlew build -x test --stacktrace

#COPY com.hobbing.eurekaserver/gradlew /app/eurekaserver/
#COPY com.hobbing.eurekaserver/gradle /app/eurekaserver/
COPY com.hobbing.eurekaserver/build.gradle /app/eurekaserver/
COPY com.hobbing.eurekaserver/src /app/eurekaserver/src

# Ensure gradlew is executable
RUN chmod +x /app/eurekaserver/gradlew

#Clean with Gradle
RUN /app/eurekaserver/gradlew clean

# Build the application
RUN /app/eurekaserver/gradlew build -x test --stacktrace

COPY /app/eurekaserver/build/libs/*.jar /eurekaserver.jar

EXPOSE 19000

ENTRYPOINT ["java", "-jar", "/eurekaserver.jar"]