
FROM eclipse-temurin:27-jdk AS build
WORKDIR /app

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./
RUN chmod +x ./gradlew && ./gradlew --no-daemon dependencies > /dev/null 2>&1 || true

COPY src ./src

FROM build AS test
CMD ["./gradlew", "--no-daemon", "test"]

FROM build AS package
RUN ./gradlew --no-daemon installDist

FROM eclipse-temurin:25-jre AS runtime
WORKDIR /app
COPY --from=package /app/build/install/ ./
CMD ["sh", "-c", "./*/bin/* "]