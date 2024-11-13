FROM openjdk:17-slim

RUN apt-get update && apt-get install -y curl

RUN curl -sSL https://github.com/vishnubob/wait-for-it/raw/master/wait-for-it.sh -o /wait-for-it.sh \
    && chmod +x /wait-for-it.sh

WORKDIR /app

COPY recipe-service/target/recipe-service-0.0.1-SNAPSHOT.jar ./recipe-service.jar

EXPOSE 8080

CMD /wait-for-it.sh postgres:5433 -- java -jar recipe-service.jar
