const express = require('express');
const axios = require('axios');
const authMiddleware = require('../middleware/auth'); // <-- Agora ativado
const router = express.Router();

const USER_SERVICE_URL = process.env.USER_SERVICE_URL;
const AUTH_SERVICE_URL = process.env.AUTH_SERVICE_URL;
const APPOINTMENT_SERVICE_URL = process.env.APPOINTMENT_SERVICE_URL;

// === ROTA PÚBLICA: LOGIN ===
router.post('/auth/login', async (req, res) => {
  try {
    const response = await axios.post(`${AUTH_SERVICE_URL}/auth/login`, req.body);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro no login' });
  }
});

// === ROTA PÚBLICA: CADASTRAR USUÁRIO ===
router.post('/auth/cadastrar', async (req, res) => {
  try {
    const response = await axios.post(`${AUTH_SERVICE_URL}/auth/cadastrar`, req.body);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro no cadastro' });
  }
});

// === [GET] /users — Listar pacientes (somente FUNCIONARIO) ===
router.get('/users', authMiddleware('FUNCIONARIO'), async (req, res) => {
  try {
    const response = await axios.get(`${USER_SERVICE_URL}/users`);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao buscar usuários' });
  }
});

// === [POST] /users — Criar paciente (somente FUNCIONARIO) ===
router.post('/users', authMiddleware('FUNCIONARIO'), async (req, res) => {
  try {
    const response = await axios.post(`${USER_SERVICE_URL}/users`, req.body);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao criar usuário' });
  }
});

// === [POST] /users/:id/comprar-pontos — Paciente logado pode comprar ===
router.post('/users/:id/comprar-pontos', authMiddleware('PACIENTE'), async (req, res) => {
  const { id } = req.params;
  const { valor, pontos } = req.query;

  try {
    const response = await axios.post(`${USER_SERVICE_URL}/users/${id}/comprar-pontos?valor=${valor}&pontos=${pontos}`);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao comprar pontos' });
  }
});

// === [GET] /users/:id/saldo — Qualquer usuário autenticado ===
router.get('/users/:id/saldo', authMiddleware(), async (req, res) => {
  try {
    const response = await axios.get(`${USER_SERVICE_URL}/users/${req.params.id}/saldo`);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao consultar saldo' });
  }
});

// === [GET] /users/:id/transacoes — Qualquer usuário autenticado ===
router.get('/users/:id/transacoes', authMiddleware(), async (req, res) => {
  try {
    const response = await axios.get(`${USER_SERVICE_URL}/users/${req.params.id}/transacoes`);
    res.json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao buscar transações' });
  }
});

// === [POST] /consultas — Criar consulta (FUNCIONARIO) ===
router.post('/consultas', authMiddleware('FUNCIONARIO'), async (req, res) => {
  try {
    const response = await axios.post(`${APPOINTMENT_SERVICE_URL}/consultas`, req.body);
    res.status(response.status).json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao criar consulta' });
  }
});

// === [GET] /consultas — Listar consultas ===
router.get('/consultas', authMiddleware(), async (req, res) => {
  try {
    const response = await axios.get(`${APPOINTMENT_SERVICE_URL}/consultas`);
    res.status(response.status).json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao listar consultas' });
  }
});

// === [GET] /consultas/:id — Buscar consulta por ID ===
router.get('/consultas/:id', authMiddleware(), async (req, res) => {
  try {
    const response = await axios.get(`${APPOINTMENT_SERVICE_URL}/consultas/${req.params.id}`);
    res.status(response.status).json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao buscar consulta' });
  }
});

// === [PUT] /consultas/:id — Atualizar consulta (FUNCIONARIO) ===
router.put('/consultas/:id', authMiddleware('FUNCIONARIO'), async (req, res) => {
  try {
    const response = await axios.put(`${APPOINTMENT_SERVICE_URL}/consultas/${req.params.id}`, req.body);
    res.status(response.status).json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao atualizar consulta' });
  }
});

// === [DELETE] /consultas/:id — Deletar consulta (FUNCIONARIO) ===
router.delete('/consultas/:id', authMiddleware('FUNCIONARIO'), async (req, res) => {
  try {
    const response = await axios.delete(`${APPOINTMENT_SERVICE_URL}/consultas/${req.params.id}`);
    res.status(response.status).send();
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao deletar consulta' });
  }
});

// === Agendamentos ===
router.post('/agendamentos', authMiddleware('PACIENTE'), async (req, res) => {
  try {
    const response = await axios.post(`${APPOINTMENT_SERVICE_URL}/agendamentos`, req.body);
    res.status(response.status).json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao agendar consulta' });
  }
});

router.get('/agendamentos', authMiddleware('FUNCIONARIO'), async (req, res) => {
  try {
    const response = await axios.get(`${APPOINTMENT_SERVICE_URL}/agendamentos`);
    res.status(response.status).json(response.data);
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao listar agendamentos' });
  }
});

router.post('/agendamentos/:id/checkin', authMiddleware('PACIENTE'), async (req, res) => {
  try {
    const response = await axios.post(`${APPOINTMENT_SERVICE_URL}/agendamentos/${req.params.id}/checkin`);
    res.status(response.status).send();
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao fazer check-in' });
  }
});

router.post('/agendamentos/:id/comparecimento', authMiddleware('FUNCIONARIO'), async (req, res) => {
  try {
    const response = await axios.post(`${APPOINTMENT_SERVICE_URL}/agendamentos/${req.params.id}/comparecimento`);
    res.status(response.status).send();
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao confirmar comparecimento' });
  }
});

router.post('/agendamentos/:id/resultado', authMiddleware('FUNCIONARIO'), async (req, res) => {
  const { compareceu } = req.query;

  try {
    const response = await axios.post(`${APPOINTMENT_SERVICE_URL}/agendamentos/${req.params.id}/resultado?compareceu=${compareceu}`);
    res.status(response.status).send();
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao registrar resultado' });
  }
});

router.post('/agendamentos/:id/cancelar', authMiddleware('PACIENTE'), async (req, res) => {
  try {
    const response = await axios.post(`${APPOINTMENT_SERVICE_URL}/agendamentos/${req.params.id}/cancelar`);
    res.status(response.status).send();
  } catch (error) {
    res.status(error.response?.status || 500).json(error.response?.data || { error: 'Erro ao cancelar agendamento' });
  }
});

module.exports = router;
