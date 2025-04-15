FROM openjdk:21-jdk

ARG WAR_FILE=target/rempms-candidate-service.war

COPY ${WAR_FILE} rempms-candidate-service.war

ENTRYPOINT ["java", "-jar", "/rempms-candidate-service.war"]

EXPOSE 7080