FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# 👇 cachea dependencias primero
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

# 👇 luego copia código
COPY src ./src

# 👇 build rápido (ya no descarga todo)
RUN mvn -B -q clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]