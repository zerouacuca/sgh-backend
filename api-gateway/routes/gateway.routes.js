const express = require('express');
const axios = require('axios');
// const authMiddleware = require('../middleware/auth'); // Desativado para testes
const router = express.Router();

const USER_SERVICE_URL = process.env.USER_SERVICE_URL;
const AUTH_SERVICE_URL = process.env.AUTH_SERVICE_URL;

// === ROTA PÚBLICA: LOGIN ===
router.post('/auth/login', async (req, res) => {
  try {
    const response = await axios.post(`${AUTH_SERVICE_URL}/login`, req.body);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro no login' });
  }
});

// === [GET] /users — Listar pacientes ===
router.get('/users', async (req, res) => {
  try {
    const response = await axios.get(`${USER_SERVICE_URL}/users`);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao buscar usuários' });
  }
});

// === [POST] /users — Criar paciente ===
router.post('/users', async (req, res) => {
  try {
    const response = await axios.post(`${USER_SERVICE_URL}/users`, req.body);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao criar usuário' });
  }
});

// === [POST] /users/:id/comprar-pontos ===
router.post('/users/:id/comprar-pontos', async (req, res) => {
  const { id } = req.params;
  const { valor, pontos } = req.query;

  try {
    const response = await axios.post(`${USER_SERVICE_URL}/users/${id}/comprar-pontos?valor=${valor}&pontos=${pontos}`);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao comprar pontos' });
  }
});

// === [GET] /users/:id/saldo ===
router.get('/users/:id/saldo', async (req, res) => {
  const { id } = req.params;

  try {
    const response = await axios.get(`${USER_SERVICE_URL}/users/${id}/saldo`);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao consultar saldo' });
  }
});

// === [GET] /users/:id/transacoes ===
router.get('/users/:id/transacoes', async (req, res) => {
  const { id } = req.params;

  try {
    const response = await axios.get(`${USER_SERVICE_URL}/users/${id}/transacoes`);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao buscar transações' });
  }
});

const APPOINTMENT_SERVICE_URL = process.env.APPOINTMENT_SERVICE_URL;

// === [POST] /consultas — Criar consulta ===
router.post('/consultas', async (req, res) => {
  try {
    const response = await axios.post(`${APPOINTMENT_SERVICE_URL}/consultas`, req.body);
    res.status(response.status).json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao criar consulta' });
  }
});

// === [GET] /consultas — Listar todas as consultas ===
router.get('/consultas', async (req, res) => {
  try {
    const response = await axios.get(`${APPOINTMENT_SERVICE_URL}/consultas`);
    res.status(response.status).json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao listar consultas' });
  }
});

// === [GET] /consultas/:id — Buscar consulta por ID ===
router.get('/consultas/:id', async (req, res) => {
  try {
    const response = await axios.get(`${APPOINTMENT_SERVICE_URL}/consultas/${req.params.id}`);
    res.status(response.status).json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao buscar consulta' });
  }
});

// === [PUT] /consultas/:id — Atualizar consulta ===
router.put('/consultas/:id', async (req, res) => {
  try {
    const response = await axios.put(`${APPOINTMENT_SERVICE_URL}/consultas/${req.params.id}`, req.body);
    res.status(response.status).json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao atualizar consulta' });
  }
});

// === [DELETE] /consultas/:id — Deletar consulta ===
router.delete('/consultas/:id', async (req, res) => {
  try {
    const response = await axios.delete(`${APPOINTMENT_SERVICE_URL}/consultas/${req.params.id}`);
    res.status(response.status).send(); // No content
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao deletar consulta' });
  }
});

module.exports = router;
