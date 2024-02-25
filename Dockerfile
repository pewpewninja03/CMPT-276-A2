FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM  eclipse-temurin:21-alpine
COPY --from=build /target/a2-0.0.1-SNAPSHOT.jar a2.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","a2.jar"]