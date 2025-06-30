CREATE TYPE category_type AS ENUM (
    'ALIMENTOS',
    'BEBIDAS',
    'ELETRONICOS',
    'MOVEIS',
    'VESTUARIO',
    'HIGIENE',
    'LIMPEZA',
    'BRINQUEDOS',
    'LIVROS',
    'ESPORTES',
    'AUTOMOTIVO',
    'FERRAMENTAS',
    'PETSHOP',
    'PAPELARIA',
    'SAUDE'
);

CREATE TABLE "product" (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    sku VARCHAR(20) NOT NULL UNIQUE,
    category category_type,
    price DECIMAL(10,2) NOT NULL,
    in_stock BOOLEAN DEFAULT FALSE,
    stock INTEGER CHECK(stock >= 0),
    created_at TIMESTAMPTZ DEFAULT current_timestamp NOT NULL,
    updated_at TIMESTAMPTZ DEFAULT current_timestamp NOT NULL
);

CREATE INDEX idx_sku ON product(sku);