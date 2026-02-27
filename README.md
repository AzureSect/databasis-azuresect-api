# Databasis — API de Gestão de Inventário (Back-end)

API REST do ecossistema Databasis, responsável por toda a lógica de negócio e persistência de dados do sistema de gestão de estoque e composição de produtos.

---

## Sobre o Projeto

Este repositório contém o módulo back-end da aplicação, desenvolvido com **Quarkus** e hospedado no **Render** (plano gratuito).

> ⚠️ **Atenção:** No primeiro acesso pode haver um tempo de espera de até ~1 minuto, pois o servidor entra em modo sleep. Isso é esperado.

A API fornece endpoints para:

- Controle de matérias-primas e seus estoques
- Cadastro e composição técnica de produtos
- Cálculo de capacidade produtiva com base no estoque disponível
- Priorização de produção por maior valor comercial

---

## Arquitetura Full-Stack

Para funcionamento completo do sistema, os dois módulos devem estar ativos:

| Módulo | Repositório |
|--------|-------------|
| Front-end (React) | [databasis-azuresect-web](https://github.com/AzureSect/databasis-azuresect-web) |
| Back-end (API Quarkus) | Este repositório |

---

## Tecnologias Utilizadas

- **Java** com **Quarkus**
- **PostgreSQL** (hospedado no Render)
- **Hibernate ORM com Panache**
- **RESTEasy** (JAX-RS)
- **Cypress** — testes end-to-end (pasta `e2e/`)

---

## Funcionalidades

### Gestão de Matérias-Primas
- CRUD completo
- Controle de quantidade em estoque

### Gestão de Produtos
- CRUD completo
- Associação de múltiplas matérias-primas com quantidades necessárias

### Sugestão de Produção
- Cálculo automático da produção máxima possível com base no estoque
- Priorização por maior valor comercial do produto
- Retorno das quantidades produzíveis e valor total gerado

---

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── ...         # Resources, Services, Entities, Repositories
│   └── resources/
│       └── application.properties
e2e/                    # Testes end-to-end com Cypress
```

---

## Configuração de Ambiente

As variáveis de ambiente (URL do banco, credenciais) estão configuradas diretamente na plataforma **Render**, não sendo necessário configurá-las localmente para o ambiente de produção.

Para rodar localmente, configure o `application.properties` com suas credenciais do PostgreSQL:

```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=SEU_USUARIO
quarkus.datasource.password=SUA_SENHA
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/databasis
quarkus.hibernate-orm.database.generation=update
```

---

## Como Executar o Projeto

### Pré-requisitos

- Java 17+
- Maven

### Executar em modo desenvolvimento

```bash
./mvnw compile quarkus:dev
```

API disponível em: `http://localhost:8080`

Dev UI disponível em: `http://localhost:8080/q/dev/`

### Gerar build de produção

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

### Gerar executable nativo (opcional)

```bash
./mvnw package -Dnative
```

---

## Testes

Os testes end-to-end estão na pasta `e2e/` e utilizam **Cypress**.

### Pré-requisitos

```bash
npm install
```

### Rodar os testes

```bash
npx cypress open
```

---

## Endpoints Principais

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/materials` | Lista todas as matérias-primas |
| POST | `/api/materials` | Cadastra uma matéria-prima |
| PUT | `/api/materials/{id}` | Atualiza uma matéria-prima |
| DELETE | `/api/materials/{id}` | Remove uma matéria-prima |
| GET | `/api/products` | Lista todos os produtos |
| POST | `/api/products` | Cadastra um produto |
| PUT | `/api/products/{id}` | Atualiza um produto |
| DELETE | `/api/products/{id}` | Remove um produto |
| GET | `/api/production/suggest` | Retorna sugestão de produção com base no estoque |
