FROM openjdk:21-jdk

ARG WAR_FILE=target/rempms-recruitment-service.war

COPY ${WAR_FILE} rempms-recruitment-service.war

ENTRYPOINT ["java", "-jar", "/rempms-recruitment-service.war"]

EXPOSE 8189