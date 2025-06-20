require('dotenv').config(); // Carrega as variáveis de ambiente do arquivo .env

const express = require('express'); // Framework para construir servidores HTTP
const cors = require('cors'); // Middleware para lidar com CORS

const app = express(); // Inicializa o app
const gatewayRoutes = require('./routes/gateway.routes'); // Importa as rotas do gateway

// Configuração do CORS para permitir o acesso do front-end
app.use(cors({
  origin: 'http://localhost:4200', // Permitir requisições vindas do front-end Angular
  credentials: true // Permitir envio de cookies/autenticação (se necessário)
}));

app.use(express.json()); // Permite receber JSON no corpo das requisições
app.use('/', gatewayRoutes); // Todas as rotas estão no arquivo de rotas importado

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`API Gateway rodando na porta ${PORT}`); // Inicia o servidor
  console.log(`🧪 JWT_SECRET: ${process.env.JWT_SECRET}`); // Exibe o segredo JWT para depuração
});
