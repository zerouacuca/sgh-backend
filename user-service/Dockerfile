# Etapa 1: Compilar o projeto
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Criar imagem final para execução
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/user-service-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]