
FROM maven:3.8.6-amazoncorretto-18 AS build

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package


FROM openjdk:18
WORKDIR /ratings-data-service
COPY --from=build /build/target/*.jar ratings-data-service.jar
ENTRYPOINT ["java", "-jar", "ratings-data-service.jar"]