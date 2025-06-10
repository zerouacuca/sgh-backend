# Etapa 1: Build com Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia todos os arquivos do projeto para o container
COPY . .

# Executa o build (ignora testes para maior velocidade)
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final mais leve
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado do estágio anterior
COPY --from=builder /app/target/appointment-service-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta usada pela aplicação
EXPOSE 8083

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
