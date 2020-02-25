FROM openjdk:8-jre-alpine
RUN mkdir /app
COPY ./build/libs/agora.delivery.jar /app/agora.delivery.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "agora.delivery.jar"]
