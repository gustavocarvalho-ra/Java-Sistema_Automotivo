# Sistema Automotivo - CRUD de Estoque de Veículos

Projeto acadêmico desenvolvido em Java com Spring Boot para gerenciamento de estoque de veículos de uma concessionária. O sistema permite cadastrar, consultar, filtrar, atualizar e remover marcas, modelos e veículos.

## Tecnologias utilizadas

- Java 17
- Spring Boot 3.5.0
- Spring Web
- Spring Data JPA
- Jakarta Bean Validation
- MySQL
- Maven

## Funcionalidades

- Cadastro de marcas.
- Cadastro de modelos vinculados a marcas.
- Cadastro de veículos vinculados a modelos.
- Consulta de veículos por ID.
- Listagem geral de veículos.
- Filtros por marca, modelo, preço mínimo, preço máximo, ano, cor e status.
- Atualização completa de veículo.
- Atualização parcial de preço, quilometragem e status.
- Remoção de registros.
- Tratamento de erros com mensagens padronizadas.

## Estrutura do projeto

```text
sistema-automotivo-crud/
├── database/
│   └── BANCO_DE_DADOS.sql
├── docs/
│   └── ENDPOINTS.md
├── postman/
│   └── Sistema_Automotivo.postman_collection.json
├── src/
│   └── main/
│       ├── java/br/com/estudo/sistemaautomotivo/
│       │   ├── config/
│       │   ├── controller/
│       │   ├── dto/
│       │   ├── exception/
│       │   ├── model/
│       │   ├── repository/
│       │   ├── service/
│       │   ├── specification/
│       │   └── SistemaAutomotivoApplication.java
│       └── resources/
│           └── application.properties
├── ENDPOINTS.md
├── pom.xml
└── README.md
```

## Como executar

### 1. Pré-requisitos

Instale:

- JDK 17 ou superior.
- Maven.
- MySQL Server.
- Postman, Insomnia ou outro cliente REST, opcional.

### 2. Criar banco de dados

Abra o MySQL Workbench ou terminal MySQL e execute o arquivo:

```text
database/BANCO_DE_DADOS.sql
```

Esse script cria o banco `sistema_automotivo`, as tabelas `marcas`, `modelos` e `veiculos`, além de inserir dados de teste.

### 3. Configurar usuário e senha do MySQL

Edite o arquivo:

```text
src/main/resources/application.properties
```

Configuração padrão:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_automotivo?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
```

Altere `username` e `password` caso seu MySQL use outras credenciais.

### 4. Rodar o projeto

Na pasta raiz do projeto, execute:

```bash
mvn spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080/api
```

## Principais endpoints

### Marcas

```http
GET    /api/marcas
GET    /api/marcas/{id}
POST   /api/marcas
PUT    /api/marcas/{id}
DELETE /api/marcas/{id}
```

### Modelos

```http
GET    /api/modelos
GET    /api/modelos?marcaId=1
GET    /api/modelos/{id}
POST   /api/modelos
PUT    /api/modelos/{id}
DELETE /api/modelos/{id}
```

### Veículos

```http
GET    /api/veiculos
GET    /api/veiculos/{id}
POST   /api/veiculos
PUT    /api/veiculos/{id}
PATCH  /api/veiculos/{id}
PATCH  /api/veiculos/{id}/status?status=VENDIDO
DELETE /api/veiculos/{id}
```

### Filtros de veículos

```http
GET /api/veiculos?marcaId=1&modeloId=1&precoMin=50000&precoMax=150000&ano=2022&status=DISPONIVEL&cor=Prata
```

Mais exemplos estão no arquivo:

```text
docs/ENDPOINTS.md
```

## Exemplos de JSON

### Cadastro de marca

```json
{
  "nome": "Toyota"
}
```

### Cadastro de modelo

```json
{
  "nome": "Corolla",
  "marcaId": 1
}
```

### Cadastro de veículo

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

## Status aceitos

```text
DISPONIVEL
RESERVADO
VENDIDO
DESCONTINUADO
```

## Arquitetura

O projeto usa uma organização em camadas:

- `controller`: recebe requisições HTTP e retorna respostas REST.
- `service`: concentra regras de negócio.
- `repository`: realiza acesso ao banco usando Spring Data JPA.
- `model`: representa as entidades persistidas no banco.
- `dto`: define objetos de entrada e saída da API.
- `exception`: padroniza os erros retornados pela API.
- `specification`: implementa filtros dinâmicos para consulta de veículos.

## Modelo de dados

Relacionamentos principais:

```text
Marca 1:N Modelo
Modelo 1:N Veiculo
```

Uma marca pode ter vários modelos. Um modelo pode ter vários veículos. Cada veículo pertence a um único modelo.

## Observações para entrega acadêmica

Para entregar no GitHub:

```bash
git init
git add .
git commit -m "Sistema automotivo CRUD"
git branch -M main
git remote add origin URL_DO_REPOSITORIO
git push -u origin main
```
