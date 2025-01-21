FROM openjdk:17-jdk-slim

VOLUME /app

ARG JAR_FILE=./com.hobbing.eurekaserver/build/libs/eurekaserver.jar

#COPY ${JAR_FILE} hobbing.jar
COPY gradlew /app/
COPY gradle /app/gradle/
COPY build.gradle /app/

COPY com.hobbing.eurekaserver/build.gradle /app/eurekaserver/
COPY com.hobbing.eurekaserver/src /app/eurekaserver/src
COPY ${JAR_FILE} /eurekaserver.jar

COPY settings.gradle /app/

# Ensure gradlew is executable
RUN chmod +x /app/gradlew

# Build the application
#RUN /app/gradlew build -x test --stacktrace

EXPOSE 19000

ENTRYPOINT ["java", "-jar", "/eurekaserver.jar"]