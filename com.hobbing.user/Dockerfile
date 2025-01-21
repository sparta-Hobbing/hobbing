FROM openjdk:17-jdk-slim

VOLUME /app

ARG JAR_FILE=com.hobbing.user/build/libs/user.jar

#COPY ${JAR_FILE} hobbing.jar
COPY gradlew /app/
COPY gradle /app/gradle/
COPY build.gradle /app/

COPY common/build.gradle /app/module-common/
COPY common/src /app/module-common/src

COPY com.hobbing.user/build.gradle /app/user/
COPY com.hobbing.user/src /app/user/src
COPY ${JAR_FILE} /user.jar

COPY settings.gradle /app/

# Ensure gradlew is executable
RUN chmod +x /app/gradlew

# Build the application
# RUN /app/gradlew build -x test --stacktrace

#RUN cp ${JAR_FILE} /user.jar

EXPOSE 19020

ENTRYPOINT ["java", "-jar", "/user.jar"]