# Banco de Dados e Endpoints - Sistema Automotivo

## 1. Banco de dados

Nome do banco:

```sql
sistema_automotivo
```

Tabelas principais:

```text
marcas
modelos
veiculos
```

Relacionamentos:

```text
marcas 1:N modelos
modelos 1:N veiculos
```

Arquivo SQL completo:

```text
database/BANCO_DE_DADOS.sql
```

## 2. Script resumido do banco

```sql
CREATE DATABASE IF NOT EXISTS sistema_automotivo;
USE sistema_automotivo;

CREATE TABLE marcas (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE modelos (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    marca_id BIGINT NOT NULL,
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (nome, marca_id),
    FOREIGN KEY (marca_id) REFERENCES marcas(id)
) ENGINE=InnoDB;

CREATE TABLE veiculos (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    modelo_id BIGINT NOT NULL,
    ano_fabricacao INT NOT NULL,
    cor VARCHAR(40) NOT NULL,
    preco DECIMAL(12,2) NOT NULL,
    quilometragem INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (modelo_id) REFERENCES modelos(id)
) ENGINE=InnoDB;
```

## 3. Endpoints

Base URL:

```text
http://localhost:8080/api
```

### Marcas

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/marcas` | Lista todas as marcas |
| GET | `/api/marcas/{id}` | Busca marca por ID |
| POST | `/api/marcas` | Cadastra marca |
| PUT | `/api/marcas/{id}` | Atualiza marca |
| DELETE | `/api/marcas/{id}` | Remove marca |

Body POST/PUT:

```json
{
  "nome": "Toyota"
}
```

### Modelos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/modelos` | Lista todos os modelos |
| GET | `/api/modelos?marcaId=1` | Lista modelos por marca |
| GET | `/api/modelos/{id}` | Busca modelo por ID |
| POST | `/api/modelos` | Cadastra modelo |
| PUT | `/api/modelos/{id}` | Atualiza modelo |
| DELETE | `/api/modelos/{id}` | Remove modelo |

Body POST/PUT:

```json
{
  "nome": "Corolla",
  "marcaId": 1
}
```

### Veículos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/veiculos` | Lista veículos |
| GET | `/api/veiculos/{id}` | Busca veículo por ID |
| POST | `/api/veiculos` | Cadastra veículo |
| PUT | `/api/veiculos/{id}` | Atualiza veículo completo |
| PATCH | `/api/veiculos/{id}` | Atualiza preço, km e/ou status |
| PATCH | `/api/veiculos/{id}/status?status=VENDIDO` | Atualiza somente status |
| DELETE | `/api/veiculos/{id}` | Remove veículo |

Body POST/PUT:

```json
{
  "modeloId": 1,
  "anoFabricacao": 2022,
  "cor": "Prata",
  "preco": 125000.00,
  "quilometragem": 25000,
  "status": "DISPONIVEL"
}
```

Body PATCH:

```json
{
  "preco": 119900.00,
  "quilometragem": 27000,
  "status": "RESERVADO"
}
```

## 4. Filtros de veículos

Parâmetros aceitos:

| Parâmetro | Exemplo |
|---|---|
| `marcaId` | `marcaId=1` |
| `modeloId` | `modeloId=1` |
| `precoMin` | `precoMin=50000` |
| `precoMax` | `precoMax=150000` |
| `ano` | `ano=2022` |
| `status` | `status=DISPONIVEL` |
| `cor` | `cor=Prata` |

Exemplo completo:

```http
GET /api/veiculos?marcaId=1&modeloId=1&precoMin=50000&precoMax=150000&ano=2022&status=DISPONIVEL&cor=Prata
```
