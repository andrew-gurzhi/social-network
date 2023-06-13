FROM openjdk:17-alpine

WORKDIR /app
COPY target/social-network.jar service.jar
ENTRYPOINT exec java -jar service.jar
