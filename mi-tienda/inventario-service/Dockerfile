FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY ./inventario-service/target/inventario-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]