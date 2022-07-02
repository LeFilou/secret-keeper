# Dependencies
FROM maven:3-eclipse-temurin-11-alpine as dependencies
WORKDIR /workspace
COPY pom.xml pom.xml
COPY secret-keeper-api/pom.xml secret-keeper-api/pom.xml
RUN mvn dependency:go-offline -B  -Dmaven.wagon.http.retryHandler.count=3

# Build
FROM dependencies AS build
WORKDIR /workspace
COPY . .
RUN mvn -B -f pom.xml clean package -DskipTests

FROM eclipse-temurin:11-alpine

RUN mkdir /app

RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
RUN chown -R javauser:javauser /app
USER javauser

WORKDIR /app

COPY --from=build /workspace/secret-keeper-api/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]