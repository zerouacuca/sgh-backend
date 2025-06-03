const express = require('express');
const axios = require('axios'); // Usado para enviar requisições HTTP aos serviços
const authMiddleware = require('../middleware/auth'); // Importa o middleware de autenticação
const router = express.Router();

// ROTA PÚBLICA: Login
router.post('/auth/login', async (req, res) => {
  try {
    // Repassa os dados do corpo da requisição para o serviço de autenticação
    const response = await axios.post(`${process.env.AUTH_SERVICE_URL}/login`, req.body);

    res.json(response.data); // Retorna o que o auth-service respondeu (ex: JWT)
  } catch (error) {
    // Em caso de erro (ex: login errado), repassa a mensagem do serviço
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro no login' });
  }
});

// ROTA PROTEGIDA: Buscar lista de usuários
router.get('/users', authMiddleware, async (req, res) => {
  try {
    // Encaminha a requisição para o user-service, incluindo o token original
    const response = await axios.get(`${process.env.USER_SERVICE_URL}/users`, {
      headers: {
        Authorization: req.headers['authorization']
      }
    });

    res.json(response.data); // Retorna a resposta do user-service
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao buscar usuários' });
  }
});

module.exports = router; // Exporta o roteador para uso no server.js
