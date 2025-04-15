FROM openjdk:21-jdk

ARG WAR_FILE=target/rempms-location-service.war

COPY ${WAR_FILE} rempms-location-service.war

ENTRYPOINT ["java", "-jar", "/rempms-location-service.war"]

EXPOSE 8185