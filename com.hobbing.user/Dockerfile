# Step 1: Base image for building the application
FROM gradle:7.6-jdk17 AS builder

WORKDIR /app

COPY gradlew gradlew
COPY gradle gradle

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean

RUN ./gradlew build -x test

# Step 2: Base image for running the application
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/com.hobbing.user/build/libs/user.jar user.jar

EXPOSE 19020

CMD ["java", "-jar", "user.jar"]
