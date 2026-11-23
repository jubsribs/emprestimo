# Empréstimo de Equipamentos

API REST desenvolvida em Java com Spring Boot para gerenciamento de equipamentos e controle de empréstimos.

## Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- Jakarta Validation
- Banco de dados configurado no projeto
- Maven 

## Como executar o projeto localmente

### 1. Pré-requisitos

- Java instalado (utilize a versão configurada no projeto).
- Maven, caso não utilize o Maven Wrapper.
- Banco de dados configurado conforme as propriedades da aplicação.

### 2. Clone o repositório

```bash
git clone URL_DO_REPOSITORIO
```

Entre na pasta do projeto:

```bash
cd emprestimo
```

### 3. Configure o banco de dados

Verifique o arquivo:

`src/main/resources/application.properties`

Configure as informações de conexão com o banco de dados, conforme o ambiente utilizado.

### 4. Execute a aplicação

Se o projeto possuir Maven Wrapper:

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

Ou, utilizando Maven instalado:

```bash
mvn spring-boot:run
```

Após iniciar, a API estará disponível, por padrão, em:

```text
http://localhost:8080
```

## Endpoints da API

A API possui operações de cadastro, consulta, atualização e remoção de equipamentos.

**URL base local:**

```text
http://localhost:8080
```

### Equipamentos

| Método | Rota | Descrição |
|---|---|---|
| GET | `/equipamentos` | Lista todos os equipamentos |
| GET | `/equipamentos/{id}` | Busca um equipamento pelo ID |
| POST | `/equipamentos` | Cadastra um equipamento |
| PUT | `/equipamentos/{id}` | Atualiza um equipamento |
| DELETE | `/equipamentos/{id}` | Remove um equipamento |

### 1. Listar equipamentos

```http
GET /equipamentos
```

Também permite filtrar os resultados por tipo e disponibilidade:

```http
GET /equipamentos?tipo=NOTEBOOK
```

```http
GET /equipamentos?disponivel=true
```

```http
GET /equipamentos?tipo=NOTEBOOK&disponivel=true
```

### 2. Buscar equipamento por ID

```http
GET /equipamentos/1
```

Retorna os dados do equipamento correspondente ao ID informado.

### 3. Cadastrar equipamento

```http
POST /equipamentos
```

Exemplo de corpo da requisição:

```json
{
  "patrimonio": "PAT001",
  "nome": "Notebook Dell",
  "tipo": "NOTEBOOK"
}
```

O equipamento é cadastrado inicialmente como disponível.

**Resposta esperada:** `201 Created`.

### 4. Atualizar equipamento

```http
PUT /equipamentos/1
```

Exemplo de corpo da requisição:

```json
{
  "patrimonio": "PAT001",
  "nome": "Notebook Dell Inspiron",
  "tipo": "NOTEBOOK"
}
```

### 5. Remover equipamento

```http
DELETE /equipamentos/1
```

**Resposta esperada:** `204 No Content`.

## Regras de negócio

- Não é permitido cadastrar equipamentos com patrimônio duplicado.
- Um equipamento recém-cadastrado fica disponível.
- Não é permitido realizar operações com equipamentos inexistentes.
- Somente equipamentos disponíveis podem ser emprestados.
- Ao realizar um empréstimo, o equipamento passa a ficar indisponível.
- O prazo previsto para devolução é de 14 dias após a data do empréstimo.
- Ao devolver um equipamento, sua disponibilidade é restaurada.
- Um empréstimo já devolvido não pode ser devolvido novamente.
- Um empréstimo é considerado atrasado quando ainda não foi devolvido e sua data prevista de devolução é anterior à data da consulta.

## Tratamento de erros

| Código HTTP | Situação |
|---|---|
| 404 Not Found | Equipamento não encontrado |
| 409 Conflict | Patrimônio já cadastrado ou conflito de regra de negócio |
| 201 Created | Equipamento cadastrado com sucesso |
| 204 No Content | Equipamento removido com sucesso |

## Deploy no Render

**Aplicação publicada: **

```text
https://emprestimo-9fqf.onrender.com
```

Substitua o endereço acima pela URL pública fornecida pelo Render.

Para acessar os endpoints em produção, utilize a URL do serviço como endereço base.

Exemplo:

```http
GET https://emprestimo-9fqf.onrender.com/equipamentos
```

## Testes

Os testes automatizados podem ser executados com:

```bash
./mvnw test
```

Ou:

```bash
mvn test
```

## Autoria

Projeto desenvolvido como prática de desenvolvimento de API REST utilizando Java e Spring Boot pelo programa ELAS TECH em Parceria com o PagBank.