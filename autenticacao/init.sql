-- Criação de tabelas adicionais ou usuários específicos
CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Insere dados iniciais se necessário
INSERT INTO usuarios (nome, email) VALUES 
('Admin', 'admin@email.com')
ON CONFLICT (email) DO NOTHING;