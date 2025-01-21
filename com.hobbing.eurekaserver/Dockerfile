FROM openjdk:17-jdk-slim

VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} hobbing.jar

ENTRYPOINT ["java", "-jar", "/eurekaserver.jar"]