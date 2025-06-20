require('dotenv').config(); // Carrega as variáveis de ambiente do arquivo .env
const express = require('express'); // Framework para construir servidores HTTP
const app = express(); // Inicializa o app
const gatewayRoutes = require('./routes/gateway.routes'); // Importa as rotas do gateway

app.use(express.json()); // Permite receber JSON no corpo das requisições
app.use('/', gatewayRoutes); // Todas as rotas estão no arquivo de rotas importado

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`API Gateway rodando na porta ${PORT}`); // Inicia o servidor
  console.log(process.env.JWT_SECRET); // Exibe o segredo JWT para depuração
});
