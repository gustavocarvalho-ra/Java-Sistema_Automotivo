# Endpoints REST - Sistema Automotivo

Base URL local:

```text
http://localhost:8080/api
```

## Status possíveis de veículo

```text
DISPONIVEL
RESERVADO
VENDIDO
DESCONTINUADO
```

---

## 1. Marcas

### Listar marcas

```http
GET /api/marcas
```

### Buscar marca por ID

```http
GET /api/marcas/{id}
```

### Cadastrar marca

```http
POST /api/marcas
Content-Type: application/json
```

Body:

```json
{
  "nome": "Toyota"
}
```

### Atualizar marca

```http
PUT /api/marcas/{id}
Content-Type: application/json
```

Body:

```json
{
  "nome": "Honda"
}
```

### Remover marca

```http
DELETE /api/marcas/{id}
```

Observação: se a marca possuir modelos cadastrados, o banco bloqueia a exclusão por integridade referencial.

---

## 2. Modelos

### Listar modelos

```http
GET /api/modelos
```

### Listar modelos por marca

```http
GET /api/modelos?marcaId=1
```

### Buscar modelo por ID

```http
GET /api/modelos/{id}
```

### Cadastrar modelo

```http
POST /api/modelos
Content-Type: application/json
```

Body:

```json
{
  "nome": "Corolla",
  "marcaId": 1
}
```

### Atualizar modelo

```http
PUT /api/modelos/{id}
Content-Type: application/json
```

Body:

```json
{
  "nome": "Civic",
  "marcaId": 2
}
```

### Remover modelo

```http
DELETE /api/modelos/{id}
```

Observação: se o modelo possuir veículos cadastrados, o banco bloqueia a exclusão por integridade referencial.

---

## 3. Veículos

### Listar todos os veículos

```http
GET /api/veiculos
```

### Buscar veículo por ID

```http
GET /api/veiculos/{id}
```

### Cadastrar veículo

```http
POST /api/veiculos
Content-Type: application/json
```

Body:

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

### Atualizar veículo completo

```http
PUT /api/veiculos/{id}
Content-Type: application/json
```

Body:

```json
{
  "modeloId": 1,
  "anoFabricacao": 2023,
  "cor": "Branco",
  "preco": 132000.00,
  "quilometragem": 15000,
  "status": "DISPONIVEL"
}
```

### Atualizar veículo parcialmente

```http
PATCH /api/veiculos/{id}
Content-Type: application/json
```

Body com um ou mais campos:

```json
{
  "preco": 119900.00,
  "quilometragem": 27000,
  "status": "RESERVADO"
}
```

### Atualizar apenas status

```http
PATCH /api/veiculos/{id}/status?status=VENDIDO
```

### Remover veículo

```http
DELETE /api/veiculos/{id}
```

---

## 4. Filtros de consulta de veículos

Todos os filtros podem ser combinados:

```http
GET /api/veiculos?marcaId=1&modeloId=1&precoMin=50000&precoMax=150000&ano=2022&status=DISPONIVEL&cor=Prata
```

Exemplos:

```http
GET /api/veiculos?status=DISPONIVEL
GET /api/veiculos?marcaId=1
GET /api/veiculos?precoMin=70000&precoMax=130000
GET /api/veiculos?ano=2024
GET /api/veiculos?cor=branco
```

---

## 5. Exemplos com cURL

### Criar marca

```bash
curl -X POST http://localhost:8080/api/marcas \
  -H "Content-Type: application/json" \
  -d '{"nome":"Nissan"}'
```

### Criar modelo

```bash
curl -X POST http://localhost:8080/api/modelos \
  -H "Content-Type: application/json" \
  -d '{"nome":"Kicks","marcaId":1}'
```

### Criar veículo

```bash
curl -X POST http://localhost:8080/api/veiculos \
  -H "Content-Type: application/json" \
  -d '{"modeloId":1,"anoFabricacao":2024,"cor":"Branco","preco":98000.00,"quilometragem":5000,"status":"DISPONIVEL"}'
```

### Filtrar veículos disponíveis

```bash
curl "http://localhost:8080/api/veiculos?status=DISPONIVEL"
```

### Vender veículo

```bash
curl -X PATCH "http://localhost:8080/api/veiculos/1/status?status=VENDIDO"
```
