FROM openjdk:17-jdk-slim
VOLUME /tmp
WORKDIR /app
COPY ./productos-service/target/productos-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]