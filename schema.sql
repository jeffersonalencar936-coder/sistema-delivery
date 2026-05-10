CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    telefone VARCHAR(20),
    endereco VARCHAR(200)
);

CREATE TABLE restaurante (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    endereco VARCHAR(200),
    avaliacao DOUBLE PRECISION
);

CREATE TABLE entregador (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    telefone VARCHAR(20),
    status VARCHAR(50),
    localizacao VARCHAR(100)
);

CREATE TABLE pedido (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL REFERENCES cliente(id) ON DELETE CASCADE,
    restaurante_id INTEGER NOT NULL REFERENCES restaurante(id) ON DELETE CASCADE,
    entregador_id INTEGER REFERENCES entregador(id) ON DELETE SET NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDENTE',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_entrega TIMESTAMP
);

CREATE TABLE item_pedido (
    id SERIAL PRIMARY KEY,
    pedido_id INTEGER NOT NULL REFERENCES pedido(id) ON DELETE CASCADE,
    nome VARCHAR(100) NOT NULL,
    quantidade INTEGER NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL
);

CREATE TABLE log_auditoria (
    id SERIAL PRIMARY KEY,
    pedido_id INTEGER NOT NULL REFERENCES pedido(id) ON DELETE CASCADE,
    acao VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);