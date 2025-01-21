FROM openjdk:17-jdk-slim

VOLUME /app

ARG JAR_FILE=com.hobbing.gateway/build/libs/gateway.jar

#COPY ${JAR_FILE} hobbing.jar
COPY gradlew /app/
COPY gradle /app/gradle/
COPY build.gradle /app/

COPY com.hobbing.gateway/build.gradle /app/gateway/
COPY com.hobbing.gateway/src /app/gateway/src
COPY ${JAR_FILE} /gateway.jar

COPY settings.gradle /app/

# Ensure gradlew is executable
RUN chmod +x /app/gradlew

# Build the application
#RUN ./gradlew build -x test --stacktrace

#RUN cp ${JAR_FILE} /gateway.jar

EXPOSE 19006

ENTRYPOINT ["java", "-jar", "/gateway.jar"]