FROM maven:3.6.3-jdk-11-slim as dependencies
WORKDIR /workspace

COPY pom.xml pom.xml
COPY secret-keeper-api/pom.xml secret-keeper-api/pom.xml
RUN mvn dependency:go-offline -B  -Dmaven.wagon.http.retryHandler.count=3


FROM dependencies AS build
WORKDIR /workspace

COPY . .
RUN mvn -B -f pom.xml clean package -DskipTests

FROM openjdk:11-jdk-slim

COPY --from=build /workspace/secret-keeper-api/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]