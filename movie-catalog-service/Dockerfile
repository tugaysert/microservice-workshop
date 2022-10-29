
FROM maven:3.8.6-amazoncorretto-18 AS build

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package


FROM openjdk:18
WORKDIR /movie-catalog-service
COPY --from=build /build/target/*.jar movie-catalog-service.jar
ENTRYPOINT ["java", "-jar", "movie-catalog-service.jar"]