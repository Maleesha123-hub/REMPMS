FROM openjdk:21-jdk

ARG WAR_FILE=target/rempms-draft-service.war

COPY ${WAR_FILE} rempms-draft-service.war

ENTRYPOINT ["java", "-jar", "/rempms-draft-service.war"]

EXPOSE 8199