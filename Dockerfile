
FROM openjdk:17-slim

WORKDIR /app

COPY recipe-service/target/recipe-service-0.0.1-SNAPSHOT.jar ./recipe-service.jar

EXPOSE 8080

CMD ["java", "-jar", "recipe-service.jar"]
