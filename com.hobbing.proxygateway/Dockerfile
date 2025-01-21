FROM openjdk:17-jdk-slim

VOLUME /app

ARG JAR_FILE=com.hobbing.proxygateway/build/libs/proxygateway.jar

#COPY ${JAR_FILE} hobbing.jar
COPY gradlew /app/
COPY gradle /app/gradle/
COPY build.gradle /app/

COPY com.hobbing.proxygateway/build.gradle /app/proxygateway/
COPY com.hobbing.proxygateway/src /app/proxygateway/src
COPY ${JAR_FILE} /proxygateway.jar

COPY settings.gradle /app/

# Ensure gradlew is executable
RUN chmod +x /app/gradlew

# Build the application
#RUN ./gradlew build -x test --stacktrace

#RUN cp ${JAR_FILE} /proxygateway.jar

EXPOSE 19005

ENTRYPOINT ["java", "-jar", "/proxygateway.jar"]