
FROM maven:3.8.6-amazoncorretto-18 AS build

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package


FROM openjdk:18
WORKDIR /discovery-service
COPY --from=build /build/target/*.jar discovery-service.jar
ENTRYPOINT ["java", "-jar", "discovery-service.jar"]