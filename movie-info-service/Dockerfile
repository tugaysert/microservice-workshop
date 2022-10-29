
FROM maven:3.8.6-amazoncorretto-18 AS build

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package


FROM openjdk:18
WORKDIR /movie-info-service
COPY --from=build /build/target/*.jar movie-info-service.jar
ENTRYPOINT ["java", "-jar", "movie-info-service.jar"]