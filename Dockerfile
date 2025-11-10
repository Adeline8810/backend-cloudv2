# Etapa de construcción
FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /app
COPY . .
RUN ./mvnw -DskipTests package

# Etapa de ejecución
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8082
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
