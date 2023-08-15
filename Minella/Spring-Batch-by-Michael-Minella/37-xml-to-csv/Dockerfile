FROM maven:3.8.6-eclipse-temurin-17 AS MAVEN_BUILDER

WORKDIR build

COPY src /build/src
COPY pom.xml pom.xml
RUN mvn clean package

FROM openjdk:11.0.6-jre-slim

WORKDIR /usr/app

COPY --from=MAVEN_BUILDER /build/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]