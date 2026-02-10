FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /deployments
COPY --from=build /build/target/quarkus-app/ /deployments/

EXPOSE 8080
ENV JAVA_APP_JAR=/deployments/quarkus-run.jar

ENTRYPOINT ["java","-jar","/deployments/quarkus-run.jar"]
