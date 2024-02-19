FROM openjdk:17-oracle

WORKDIR /app

COPY target/*.jar Application.jar

EXPOSE 8080

CMD ["java", "-jar", "Application.jar"]
