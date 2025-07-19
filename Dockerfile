FROM openjdk:21-slim
WORKDIR /app
COPY */BusTicket-0.0.1-SNAPSHOT.jar app.jar
ENV SPRING_PROFILE_DOCKER=docker
ENTRYPOINT ["java", "-jar", "app.jar"]