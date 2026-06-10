# ======================
# Build Stage
# ======================
FROM gradle:8.7-jdk21-alpine AS builder

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .

COPY gradle gradle

RUN gradle dependencies --no-daemon

COPY src src

RUN gradle clean bootJar --no-daemon

# ======================
# Runtime Stage
# ======================
FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]