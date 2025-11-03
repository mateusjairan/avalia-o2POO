# Sistema de CRUD em Java com Interface Gráfica e MySQL

## Descrição

Um sistema de desktop em Java com uma interface gráfica (GUI) feita com Swing para gerenciar perfis, usuários, clientes e produtos. A aplicação utiliza um banco de dados **MySQL** local e conta com uma tela de login funcional (com opção de auto-cadastro) e janelas de CRUD intuitivas para cada funcionalidade.

Este projeto foi desenvolvido para fins acadêmicos, com foco em código limpo, boas práticas de desenvolvimento e clareza para facilitar a apresentação e explicação do código.

## Requisitos

Para compilar e executar este projeto, você precisará ter instalado:

-   **Java 20** ou superior
-   **Apache Maven**
-   **MySQL Server**

## Configuração do Banco de Dados

Antes de executar a aplicação, você precisa configurar o banco de dados MySQL:

1.  **Crie um novo banco de dados** com o nome `simple_crud_db`. Você pode usar o comando SQL:
    ```sql
    CREATE DATABASE simple_crud_db;
    ```

2.  **Execute o script `schema.sql`** para criar as tabelas e inserir os dados iniciais. O arquivo está localizado em `src/main/resources/schema.sql`. Você pode executar o conteúdo deste arquivo em um cliente MySQL (como DBeaver, MySQL Workbench, ou o terminal).
    - Isso criará as tabelas `perfis`, `usuarios`, `clientes`, e `produtos`, e adicionará um usuário padrão `admin` (senha: `admin`) para o primeiro login.

3.  **Verifique as credenciais de conexão:** O projeto está configurado para se conectar com o usuário `root` e senha `root`. Se a sua configuração do MySQL for diferente, ajuste as credenciais na classe `src/main/java/com/example/Database.java`.

## Como Compilar e Executar

1.  **Abra o terminal** na pasta raiz do projeto.

2.  **Compile o projeto** usando o Maven. O comando a seguir irá baixar as dependências e compilar o código-fonte:
    ```bash
    mvn clean install
    ```

3.  **Execute a aplicação** com o seguinte comando Maven. Isso iniciará a interface gráfica:
    ```bash
    mvn exec:java
    ```
