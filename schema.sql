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