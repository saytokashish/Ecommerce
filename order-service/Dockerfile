# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
COPY user-service/pom.xml ./user-service/
COPY order-service/pom.xml ./order-service/
COPY product-service/pom.xml ./product-service/
COPY order-service/src ./order-service/src
RUN mvn clean package -pl order-service -am -DskipTests

# ---- Run Stage ----
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/order-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"] 