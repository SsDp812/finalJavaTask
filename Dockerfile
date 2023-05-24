FROM openjdk:17-alpine
ARG JAR_FILE=application/target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]