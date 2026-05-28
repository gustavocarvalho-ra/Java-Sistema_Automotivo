CREATE DATABASE IF NOT EXISTS sistema_automotivo
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE sistema_automotivo;

CREATE TABLE IF NOT EXISTS marcas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_marcas PRIMARY KEY (id),
    CONSTRAINT uk_marca_nome UNIQUE (nome)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS modelos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    marca_id BIGINT NOT NULL,
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_modelos PRIMARY KEY (id),
    CONSTRAINT uk_modelo_nome_marca UNIQUE (nome, marca_id),
    CONSTRAINT fk_modelo_marca FOREIGN KEY (marca_id) REFERENCES marcas(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS veiculos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    modelo_id BIGINT NOT NULL,
    ano_fabricacao INT NOT NULL,
    cor VARCHAR(40) NOT NULL,
    preco DECIMAL(12,2) NOT NULL,
    quilometragem INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_veiculos PRIMARY KEY (id),
    CONSTRAINT fk_veiculo_modelo FOREIGN KEY (modelo_id) REFERENCES modelos(id),
    CONSTRAINT chk_preco_positivo CHECK (preco > 0),
    CONSTRAINT chk_quilometragem_nao_negativa CHECK (quilometragem >= 0),
    CONSTRAINT chk_status_veiculo CHECK (status IN ('DISPONIVEL', 'RESERVADO', 'VENDIDO', 'DESCONTINUADO'))
) ENGINE=InnoDB;

CREATE INDEX idx_modelos_marca_id ON modelos(marca_id);
CREATE INDEX idx_veiculos_modelo_id ON veiculos(modelo_id);
CREATE INDEX idx_veiculos_ano ON veiculos(ano_fabricacao);
CREATE INDEX idx_veiculos_preco ON veiculos(preco);
CREATE INDEX idx_veiculos_status ON veiculos(status);

-- Dados iniciais para teste
INSERT IGNORE INTO marcas (id, nome) VALUES
(1, 'Toyota'),
(2, 'Honda'),
(3, 'Volkswagen'),
(4, 'Chevrolet'),
(5, 'Fiat');

INSERT IGNORE INTO modelos (id, nome, marca_id) VALUES
(1, 'Corolla', 1),
(2, 'Hilux', 1),
(3, 'Civic', 2),
(4, 'HR-V', 2),
(5, 'Gol', 3),
(6, 'Onix', 4),
(7, 'Argo', 5);

INSERT IGNORE INTO veiculos (id, modelo_id, ano_fabricacao, cor, preco, quilometragem, status) VALUES
(1, 1, 2022, 'Prata', 125000.00, 25000, 'DISPONIVEL'),
(2, 2, 2021, 'Branco', 210000.00, 52000, 'RESERVADO'),
(3, 3, 2020, 'Preto', 118000.00, 41000, 'DISPONIVEL'),
(4, 4, 2023, 'Cinza', 145000.00, 12000, 'DISPONIVEL'),
(5, 5, 2018, 'Vermelho', 48000.00, 78000, 'VENDIDO'),
(6, 6, 2024, 'Branco', 89000.00, 8000, 'DISPONIVEL'),
(7, 7, 2022, 'Azul', 73000.00, 22000, 'DESCONTINUADO');
