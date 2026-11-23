# Empréstimo de Equipamentos

API REST desenvolvida em Java com Spring Boot para gerenciamento de equipamentos e controle de empréstimos.

O sistema permite cadastrar equipamentos e colaboradores, registrar empréstimos, controlar devoluções e aplicar regras de negócio relacionadas à disponibilidade dos equipamentos.

## Tecnologias

- Java 25
- Spring Boot 4.1.2
- Spring Data JPA
- Spring Web MVC
- Jakarta Validation
- H2 Database
- Maven
- Springdoc OpenAPI (Swagger)
- JUnit e Spring Boot Test

## Como executar o projeto localmente

### 1. Pré-requisitos

- Java 25 instalado.
- Maven ou Maven Wrapper.
- Git.

### 2. Clone o repositório

```bash
git clone URL_DO_REPOSITORIO
```

Entre na pasta do projeto:

```bash
cd emprestimo
```

### 3. Configure o banco de dados

O projeto utiliza o banco H2 com armazenamento em arquivo.

As configurações estão no arquivo:

`src/main/resources/application.properties`

### 4. Execute a aplicação

Com Maven Wrapper:

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

Após iniciar, a API estará disponível em:

`http://localhost:8080`

## Endpoints da API

### Equipamentos

| Método | Rota | Descrição |
|---|---|---|
| GET | `/equipamentos` | Lista todos os equipamentos |
| GET | `/equipamentos/{id}` | Busca um equipamento pelo ID |
| POST | `/equipamentos` | Cadastra um equipamento |
| PUT | `/equipamentos/{id}` | Atualiza um equipamento |
| DELETE | `/equipamentos/{id}` | Remove um equipamento |

#### Exemplo de cadastro

**POST `/equipamentos`**

Corpo da requisição (JSON):

```json
{
  "patrimonio": "PAT001",
  "nome": "Notebook Dell",
  "tipo": "NOTEBOOK"
}
```

### Colaboradores

| Método | Rota | Descrição |
|---|---|---|
| GET | `/colaboradores` | Lista todos os colaboradores |
| GET | `/colaboradores/{id}` | Busca um colaborador pelo ID |
| POST | `/colaboradores` | Cadastra um colaborador |
| GET | `/colaboradores/{id}/emprestimos` | Consulta o histórico de empréstimos de um colaborador |

### Empréstimos

| Método | Rota | Descrição |
|---|---|---|
| GET | `/emprestimos` | Lista todos os empréstimos |
| GET | `/emprestimos/{id}` | Busca um empréstimo pelo ID |
| POST | `/emprestimos?equipamentoId=1&colaboradorId=2` | Realiza um empréstimo |
| PUT | `/emprestimos/{id}/devolucao` | Registra a devolução de um equipamento |

## Regras de negócio

### Equipamentos

- Não é permitido cadastrar equipamentos com patrimônio duplicado.
- Todo equipamento novo nasce disponível, mesmo que o JSON enviado informe `"disponivel": false`.
- Somente equipamentos disponíveis podem ser emprestados.
- Equipamentos que já tiveram empréstimos não podem ser removidos.
- Ao realizar um empréstimo, o equipamento passa a ficar indisponível.
- Ao devolver um equipamento, sua disponibilidade é restaurada.

### Colaboradores

- Não podem existir dois colaboradores com o mesmo e-mail.
- Um colaborador que possui um empréstimo atrasado não pode realizar outro empréstimo.

### Empréstimos

- O prazo previsto para devolução é de 14 dias após a data do empréstimo.
- A data de devolução é registrada no momento da operação.
- Um empréstimo já devolvido não pode ser devolvido novamente.
- Um empréstimo é considerado atrasado quando ainda não foi devolvido e sua data prevista de devolução é anterior à data da consulta.

## Tratamento de erros

A API utiliza códigos HTTP para indicar o resultado das operações.

| Código HTTP | Situação |
|---|---|
| 400 Bad Request | Dados inválidos ou parâmetros obrigatórios ausentes |
| 404 Not Found | Recurso não encontrado |
| 409 Conflict | Conflito com uma regra de negócio |
| 415 Unsupported Media Type | Tipo de conteúdo incompatível com a requisição(O texto precisa está no formato JSON) |

### Respostas de sucesso

| Código HTTP | Situação |
|---|---|
| 200 OK | Consulta ou atualização realizada com sucesso |
| 201 Created | Recurso criado com sucesso |
| 204 No Content | Operação concluída sem conteúdo de retorno |

## Validação

O projeto utiliza Jakarta Validation para validar os dados recebidos pela API.

A anotação `@Valid` permite que as validações declaradas nas entidades ou DTOs sejam executadas durante o processamento das requisições.

## Relacionamento entre entidades – @ManyToOne

A entidade `Emprestimo` possui relacionamentos com `Equipamento` e `Colaborador`.

Cada empréstimo está associado a um equipamento e a um colaborador.

O relacionamento é implementado utilizando JPA:

```java
@ManyToOne
@JoinColumn(nullable = false)
private Equipamento equipamento;

@ManyToOne
@JoinColumn(nullable = false)
private Colaborador colaborador;
```

Assim, um equipamento ou colaborador pode estar associado a diferentes registros de empréstimo ao longo do tempo.

## Swagger – Documentação da API

O projeto utiliza Springdoc OpenAPI para disponibilizar uma documentação interativa dos endpoints.

Acesse localmente:

`http://localhost:8080/swagger-ui/index.html`

Através do Swagger é possível:

- Visualizar os endpoints disponíveis.
- Consultar parâmetros e modelos de dados.
- Executar requisições diretamente pela interface.
- Verificar as respostas da API.

## Actuator

O Spring Boot Actuator está configurado atualmente como dependência do projeto.

## Deploy no Render

A aplicação está publicada na plataforma Render.

**URL pública:**

https://emprestimo-9fqf.onrender.com

### Exemplos de acesso em produção

Listar equipamentos:

```http
GET https://emprestimo-9fqf.onrender.com/equipamentos
```

Swagger em produção:

`https://emprestimo-9fqf.onrender.com/swagger-ui/index.html`

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

Projeto desenvolvido como prática de desenvolvimento de API REST utilizando Java e Spring Boot, pelo programa **ELAS TECH**, em parceria com o **PagBank**.

**Vídeo no YouTube:** [https://youtu.be/Nwd5z8XA-a8]