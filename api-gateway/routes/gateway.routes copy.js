const express = require('express');
const axios = require('axios');
const authMiddleware = require('../middleware/auth'); // Middleware de autenticação
const router = express.Router();

// URL base do user-service
const USER_SERVICE_URL = process.env.USER_SERVICE_URL;

// === ROTA PÚBLICA: LOGIN ===
router.post('/auth/login', async (req, res) => {
  try {
    const response = await axios.post(`${process.env.AUTH_SERVICE_URL}/login`, req.body);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro no login' });
  }
});

// === ROTA PROTEGIDA: LISTAR PACIENTES ===
router.get('/users', authMiddleware, async (req, res) => {
  try {
    const response = await axios.get(`${USER_SERVICE_URL}/users`, {
      headers: {
        Authorization: req.headers['authorization'],
      },
    });
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao buscar usuários' });
  }
});

// === ROTA PROTEGIDA: CRIAR PACIENTE ===
router.post('/users', authMiddleware, async (req, res) => {
  try {
    const response = await axios.post(`${USER_SERVICE_URL}/users`, req.body, {
      headers: {
        Authorization: req.headers['authorization'],
      },
    });
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao criar usuário' });
  }
});

// === ROTA PROTEGIDA: COMPRAR PONTOS ===
router.post('/users/:id/comprar-pontos', authMiddleware, async (req, res) => {
  const { id } = req.params;
  const { valor, pontos } = req.query;

  try {
    const response = await axios.post(`${USER_SERVICE_URL}/users/${id}/comprar-pontos?valor=${valor}&pontos=${pontos}`, {}, {
      headers: {
        Authorization: req.headers['authorization'],
      },
    });
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao comprar pontos' });
  }
});

// === ROTA PROTEGIDA: CONSULTAR SALDO ===
router.get('/users/:id/saldo', authMiddleware, async (req, res) => {
  const { id } = req.params;

  try {
    const response = await axios.get(`${USER_SERVICE_URL}/users/${id}/saldo`, {
      headers: {
        Authorization: req.headers['authorization'],
      },
    });
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao consultar saldo' });
  }
});

// === ROTA PROTEGIDA: LISTAR TRANSAÇÕES ===
router.get('/users/:id/transacoes', authMiddleware, async (req, res) => {
  const { id } = req.params;

  try {
    const response = await axios.get(`${USER_SERVICE_URL}/users/${id}/transacoes`, {
      headers: {
        Authorization: req.headers['authorization'],
      },
    });
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao buscar transações' });
  }
});

module.exports = router;
