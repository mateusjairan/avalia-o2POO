# Análise do Projeto e Documentação dos Arquivos

Este documento apresenta uma análise detalhada do projeto com base nos critérios de avaliação e uma explicação de cada arquivo de código presente no sistema.

## Verificação dos Critérios de Avaliação

O projeto foi analisado e verificou-se que **todos os critérios foram atendidos**:

1.  **Login (2,0 pontos):**
    *   **Login Funcional:** Implementado através da classe `ServicoAutenticacao` e da interface `PainelLogin`. O sistema verifica as credenciais no banco de dados e permite o acesso apenas a usuários cadastrados.

2.  **Cadastro 1 - Usuários (2,0 pontos):**
    *   **Listar:** Implementado no `PainelUsuarios`, exibindo usuários e seus perfis em uma `JTable`.
    *   **Incluir:** Funcionalidade de adicionar usuários com verificação de nome único e seleção de perfil via `JComboBox`.
    *   **Editar:** Permite alterar nome, senha (opcional) e perfil.
    *   **Excluir:** Permite remover usuários do sistema.
    *   **Relação:** Possui relação com a entidade **Perfil** (`perfil_id` na tabela `usuarios`).

3.  **Cadastro 2 - Perfis (2,0 pontos):**
    *   **Listar:** Implementado no `PainelPerfis`.
    *   **Incluir:** Permite criar novos perfis.
    *   **Editar:** Permite renomear perfis.
    *   **Excluir:** Permite remover perfis.

4.  **Cadastro 3 - Clientes e Produtos (2,0 pontos):**
    *   O sistema implementa **dois** cadastros extras completos, superando o requisito mínimo de três cadastros totais.
    *   **Clientes:** CRUD completo (Listar, Incluir, Editar, Excluir) em `PainelClientes`.
    *   **Produtos:** CRUD completo (Listar, Incluir, Editar, Excluir) em `PainelProdutos`.

5.  **Qualidade do Software (1,0 ponto):**
    *   **Organização:** Código separado em pacotes (`com.example` para lógica/DAO e `com.example.gui` para interface gráfica).
    *   **Boas Práticas:** Uso de `PreparedStatement` para segurança contra SQL Injection, `try-with-resources` para fechamento de conexões, e tratamento de exceções com `DataAccessException`.
    *   **Nomes:** Classes, métodos e variáveis nomeados de forma clara e em português, facilitando o entendimento.

---

## Explicação dos Arquivos de Código

Abaixo segue a explicação detalhada de cada arquivo do projeto:

### Pacote `com.example` (Lógica e Acesso a Dados)

*   **`Cliente.java`**: Classe modelo (Entity) que representa um cliente. Contém atributos como `id`, `nome`, `email` e `telefone`, além de construtores, getters, setters e método `toString`.
*   **`ClienteDao.java`**: Classe de acesso a dados (DAO) para Clientes. Responsável por executar operações SQL (INSERT, SELECT, UPDATE, DELETE) na tabela `clientes` do banco de dados.
*   **`DataAccessException.java`**: Exceção personalizada (`RuntimeException`) utilizada para encapsular erros de banco de dados (`SQLException`), simplificando o tratamento de erros na camada de interface.
*   **`Database.java`**: Classe utilitária responsável por gerenciar a conexão com o banco de dados MySQL. Define a URL, usuário e senha, e fornece o método `getConnection()`.
*   **`Perfil.java`**: Classe modelo que representa um perfil de acesso (ex: Administrador, Usuário). Contém `id` e `nome`.
*   **`PerfilDao.java`**: DAO para a entidade Perfil. Gerencia as operações de banco de dados na tabela `perfis`.
*   **`Produto.java`**: Classe modelo que representa um produto. Contém `id`, `nome`, `descricao` e `preco` (usando `BigDecimal` para precisão monetária).
*   **`ProdutoDao.java`**: DAO para a entidade Produto. Gerencia as operações SQL na tabela `produtos`.
*   **`ServicoAutenticacao.java`**: Classe de serviço que contém a lógica de login. Verifica se o nome de usuário e senha correspondem a um registro no banco de dados e mantém o estado do usuário logado.
*   **`Usuario.java`**: Classe modelo que representa um usuário do sistema. Possui relacionamento com `Perfil` (objeto `Perfil` como atributo).
*   **`UsuarioDao.java`**: DAO para a entidade Usuário. Realiza operações SQL na tabela `usuarios`, incluindo o gerenciamento da chave estrangeira para a tabela `perfis`.

### Pacote `com.example.gui` (Interface Gráfica Swing)

*   **`AplicacaoGUI.java`**: Ponto de entrada da aplicação (`main`). Inicializa a conexão com o banco de dados (criando tabelas se necessário via script) e exibe a janela principal (`TelaPrincipal`).
*   **`PainelClientes.java`**: Painel (`JPanel`) que contém a interface para gerenciamento de clientes. Possui uma tabela para listagem e botões para adicionar, editar e excluir clientes.
*   **`PainelLogin.java`**: Painel responsável pela tela de login. Contém campos para usuário e senha, e botões para "Entrar" e "Cadastrar-se".
*   **`PainelPerfis.java`**: Painel para gerenciamento de perfis. Permite visualizar e manipular os perfis de acesso do sistema.
*   **`PainelProdutos.java`**: Painel para gerenciamento de produtos. Similar aos outros painéis de cadastro, mas lida com dados específicos de produtos como preço e descrição.
*   **`PainelUsuarios.java`**: Painel para gerenciamento de usuários. Inclui lógica para preencher um `JComboBox` com perfis disponíveis ao criar ou editar um usuário.
*   **`TelaPrincipal.java`**: Janela principal (`JFrame`) da aplicação. Gerencia a navegação entre as telas (Login e Painel Principal com abas) utilizando `CardLayout` e `JTabbedPane`.

### Recursos (`src/main/resources`)

*   **`schema.sql`**: Script SQL executado na inicialização para criar as tabelas do banco de dados (`perfis`, `usuarios`, `clientes`, `produtos`) e inserir dados iniciais (usuário 'admin').
