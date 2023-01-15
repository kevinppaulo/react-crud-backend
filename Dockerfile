FROM openjdk:17-alpine

COPY build/libs/student-crud-api-0.0.1-SNAPSHOT.jar /app/server.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/server.jar"]