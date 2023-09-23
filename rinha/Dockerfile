FROM gradle:8.2 as builder
USER root
ENV APP_DIR /app
WORKDIR $APP_DIR
COPY . $APP_DIR
RUN gradle build -x test
USER guest

# -----------------------------------------------------------------------------

FROM openjdk:17-slim-buster
WORKDIR /app
COPY --from=builder /app/build/libs/*SNAPSHOT.jar /app/rinha.jar
ENTRYPOINT ["java", "-jar", "/app/rinha.jar"]
