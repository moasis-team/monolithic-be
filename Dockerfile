FROM openjdk:17
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENV	USE_PROFILE local
ENTRYPOINT ["java","-Dspring.profiles.active=${USE_PROFILE}", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]