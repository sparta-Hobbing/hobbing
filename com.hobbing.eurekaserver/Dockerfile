FROM openjdk:17-jdk-slim

VOLUME /app

ARG JAR_FILE=./com.hobbing.eurekaserver/build/libs/eurekaserver.jar

COPY gradlew /app/eurekaserver/
COPY gradle /app/eurekaserver/
COPY build.gradle /app/eurekaserver/
COPY src /app/eurekaserver/src

COPY settings.gradle /app/

# Ensure gradlew is executable
RUN chmod +x /app/eurekaserver/gradlew

#Clean with Gradle
RUN /app/eurekaserver/gradle clean

# Build the application
RUN /app/eurekaserver/gradle build -x test --stacktrace

#COPY com.hobbing.eurekaserver/gradlew /app/eurekaserver/
#COPY com.hobbing.eurekaserver/gradle /app/eurekaserver/
#COPY eurekaserver/build.gradle /app/eurekaserver/


# Ensure gradlew is executable
#RUN chmod +x /app/eurekaserver/gradlew

#Clean with Gradle
#RUN /app/eurekaserver/gradlew clean

# Build the application
#RUN /app/eurekaserver/gradlew build -x test --stacktrace

COPY /app/eurekaserver/build/libs/*.jar /eurekaserver.jar

EXPOSE 19000

ENTRYPOINT ["java", "-jar", "/eurekaserver.jar"]