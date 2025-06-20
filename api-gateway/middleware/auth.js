const jwt = require('jsonwebtoken');

// Decodifica o segredo Base64 uma vez ao carregar o middleware
const jwtSecret = Buffer.from(process.env.JWT_SECRET, 'base64');

function authMiddleware(requiredRole) {
  return (req, res, next) => {
    const authHeader = req.headers['authorization'];

    if (!authHeader) {
      console.log("❌ Token não fornecido");
      return res.status(401).json({ error: 'Token não fornecido' });
    }

    const token = authHeader.split(' ')[1];
    if (!token) {
      console.log("❌ Token malformado");
      return res.status(401).json({ error: 'Token inválido' });
    }

    console.log("🔐 Token recebido:", token);
    console.log("🧪 JWT_SECRET (decodificado) usado:", jwtSecret.toString('utf-8'));

    jwt.verify(token, jwtSecret, (err, decoded) => {
      if (err) {
        console.log("❌ Erro ao verificar token:", err.message);
        return res.status(403).json({ error: 'Token inválido ou expirado' });
      }

      req.user = decoded;

      if (requiredRole) {
        console.log("🔍 Verificando role:", requiredRole);
        console.log("📦 Roles no token:", req.user.auth);

        if (!req.user.auth?.includes(`ROLE_${requiredRole.toUpperCase()}`)) {
          console.log("⛔ Permissão negada para o tipo:", requiredRole);
          return res.status(403).json({ error: 'Permissão negada' });
        }
      }

      console.log("✅ Token válido. Permissão concedida.");
      next();
    });
  };
}

module.exports = authMiddleware;