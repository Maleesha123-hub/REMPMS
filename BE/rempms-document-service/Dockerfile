FROM openjdk:21-jdk

ARG WAR_FILE=target/rempms-document-service.war

COPY ${WAR_FILE} rempms-document-service.war

ENTRYPOINT ["java", "-jar", "/rempms-document-service.war"]

EXPOSE 8197