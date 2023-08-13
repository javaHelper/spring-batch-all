FROM maven:3.8.6-eclipse-temurin-17 AS BUILDER

WORKDIR build

COPY src src
COPY pom.xml pom.xml
RUN mvn clean package

FROM openjdk:11.0.6-jre-slim

WORKDIR /usr/app

COPY --from=BUILDER /build/target/*.jar app.jar
#COPY target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]