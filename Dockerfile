FROM openjdk:8-jdk-alpine

COPY web/build/libs/web-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-Dspring.profiles.active=k8s", "-jar", "/app.jar"]
