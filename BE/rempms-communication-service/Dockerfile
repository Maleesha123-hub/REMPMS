FROM openjdk:21-jdk

ARG WAR_FILE=target/rempms-communication-service.war

COPY ${WAR_FILE} rempms-communication-service.war

ENTRYPOINT ["java", "-jar", "/rempms-communication-service.war"]

EXPOSE 8184