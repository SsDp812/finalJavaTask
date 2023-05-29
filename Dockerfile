FROM openjdk:17.0.2-jdk
ARG JAR_FILE=application/target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]