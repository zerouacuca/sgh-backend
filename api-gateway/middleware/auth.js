const jwt = require('jsonwebtoken'); // Biblioteca para trabalhar com JWT

// Middleware para proteger rotas
module.exports = function (req, res, next) {
  const token = req.headers['authorization']; // Lê o header Authorization

  if (!token) return res.status(401).json({ message: 'Token ausente' });

  try {
    // Remove "Bearer " do início do token e valida
    const decoded = jwt.verify(token.replace('Bearer ', ''), process.env.JWT_SECRET);

    req.user = decoded; // Salva os dados do token no request para uso posterior
    next(); // Passa para a próxima função (rota)
  } catch (err) {
    return res.status(403).json({ message: 'Token inválido' }); // Token incorreto
  }
};
