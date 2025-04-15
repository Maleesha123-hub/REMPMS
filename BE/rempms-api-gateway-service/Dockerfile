FROM openjdk:21-jdk

ARG WAR_FILE=target/rempms-api-gateway-service.war

COPY ${WAR_FILE} rempms-api-gateway-service.war

ENTRYPOINT ["java", "-jar", "/rempms-api-gateway-service.war"]

EXPOSE 8081