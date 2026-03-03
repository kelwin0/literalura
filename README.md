# 📚 LiterAlura — Catálogo de Livros em Java

Projeto de catálogo de livros com menu interativo via console, utilizando Java 17+,
Spring Boot e consumindo a API Gutendex para buscar livros do Project Gutenberg.

A aplicação usa:
- Java HttpClient (nativo do Java 17)
- Jackson (para conversão de JSON)
- Spring Data JPA + PostgreSQL (persistência de dados)
- Gutendex API (gratuita, sem necessidade de API KEY)

---

## 🚀 Como funciona

1. O usuário escolhe uma das **6 opções** no menu:

```
1. Buscar livro por título
2. Listar livros cadastrados
3. Listar autores cadastrados
4. Listar autores vivos em um determinado ano
5. Listar livros por idioma
6. Contagem de livros por idioma
0. Sair
```

2. Na opção 1, o usuário digita o título do livro.
3. O sistema consulta a API Gutendex em tempo real.
4. O primeiro resultado é salvo no banco de dados PostgreSQL.
5. As demais opções consultam e exibem os dados já salvos.

---

## 🛠 Tecnologias utilizadas

- Java 17+
- Spring Boot 3.2.3
- Spring Data JPA
- Jackson 2.16.1
- PostgreSQL 16+
- Gutendex API (gratuita, sem API KEY)

---

## 📦 Dependências Maven

Principais dependências no `pom.xml`:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <scope>runtime</scope>
</dependency>

<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>2.16.1</version>
</dependency>
```

---

## ⚙️ Configuração do banco de dados

Crie o banco no PostgreSQL:

```sql
CREATE DATABASE literalura;
```

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA_AQUI
```

---

## ▶️ Como executar

Execute pelo Maven:

```
mvn spring-boot:run
```

Ou gere o JAR e execute:

```
mvn clean package
java -jar target/literalura-0.0.1-SNAPSHOT.jar
```

---

## 📂 Estrutura do projeto

```
src/
 └── main/
      ├── java/com/literalura/
      │    ├── LiteraluraApplication.java     ← menu e entrada do usuário
      │    ├── dto/
      │    │    ├── RespostaApiDTO.java        ← mapeia a resposta geral da API
      │    │    ├── LivroApiDTO.java           ← mapeia os dados do livro
      │    │    └── AutorApiDTO.java           ← mapeia os dados do autor
      │    ├── model/
      │    │    ├── Livro.java                 ← entidade JPA (tabela livros)
      │    │    └── Autor.java                 ← entidade JPA (tabela autores)
      │    ├── repository/
      │    │    ├── LivroRepository.java       ← consultas de livros no banco
      │    │    └── AutorRepository.java       ← consultas de autores no banco
      │    └── service/
      │         ├── GutendexService.java       ← consumo da API + parse JSON
      │         └── LivroService.java          ← lógica de negócio
      └── resources/
           └── application.properties         ← configurações do banco
```

---

## 📌 Objetivo do projeto

Projeto feito para prática de:

- Consumo de API REST com Java HttpClient
- Manipulação de JSON com Jackson
- Persistência de dados com Spring Data JPA
- Consultas derivadas (derived queries) com PostgreSQL
- Interação via console (menu textual)
- Organização em camadas (dto, model, repository, service)

---

## ✍ Autor

Kelwin Zambarda
