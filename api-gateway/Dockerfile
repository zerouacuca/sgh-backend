# Usa a imagem oficial do Node.js como base
FROM node:20-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos do projeto para o container
COPY . .

# Instala as dependências do projeto
RUN npm install

# Expõe a porta configurada para o gateway
EXPOSE 8080

# Comando padrão que roda quando o container inicia
CMD ["node", "server.js"]
