FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el pom y descargamos dependencias (sin el go-offline que da problemas)
COPY pom.xml .
RUN mvn dependency:resolve

# Copiamos el código y empaquetamos
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]