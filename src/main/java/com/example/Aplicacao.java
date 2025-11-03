package com.example;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Aplicacao {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ServicoAutenticacao servicoAutenticacao = new ServicoAutenticacao();
    private static final PerfilDao perfilDao = new PerfilDao();
    private static final UsuarioDao usuarioDao = new UsuarioDao();
    private static final ClienteDao clienteDao = new ClienteDao();
    private static final ProdutoDao produtoDao = new ProdutoDao();

    public static void main(String[] args) {
        Database.initialize();
        mostrarMenuInicial();
    }

    private static void mostrarMenuInicial() {
        while (true) {
            System.out.println("\n--- Menu Inicial ---");
            System.out.println("1. Login");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        tratarLogin();
                        if (servicoAutenticacao.estaLogado()) {
                            mostrarMenuPrincipal();
                        }
                        break;
                    case 2:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.err.println("Opção inválida. Por favor, tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }

    private static void tratarLogin() {
        System.out.print("Digite o nome de usuário: ");
        String nomeUsuario = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        if (servicoAutenticacao.login(nomeUsuario, senha)) {
            System.out.println("Login bem-sucedido! Bem-vindo, " + servicoAutenticacao.getUsuarioLogado().getNomeUsuario() + ".");
        } else {
            System.err.println("Login falhou.");
        }
    }

    private static void mostrarMenuPrincipal() {
        while (servicoAutenticacao.estaLogado()) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Gerenciar Perfis");
            System.out.println("2. Gerenciar Usuários");
            System.out.println("3. Gerenciar Clientes");
            System.out.println("4. Gerenciar Produtos");
            System.out.println("5. Logout");
            System.out.print("Escolha uma opção: ");

            try {
                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        mostrarMenuPerfis();
                        break;
                    case 2:
                        mostrarMenuUsuarios();
                        break;
                    case 3:
                        mostrarMenuClientes();
                        break;
                    case 4:
                        mostrarMenuProdutos();
                        break;
                    case 5:
                        servicoAutenticacao.logout();
                        return;
                    default:
                        System.err.println("Opção inválida. Por favor, tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }

    private static void mostrarMenuPerfis() {
        while (true) {
            System.out.println("\n--- Gerenciar Perfis ---");
            System.out.println("1. Adicionar Perfil");
            System.out.println("2. Listar Todos os Perfis");
            System.out.println("3. Atualizar Perfil");
            System.out.println("4. Deletar Perfil");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        System.out.print("Digite o nome do perfil: ");
                        String nome = scanner.nextLine();
                        if (nome.trim().isEmpty()) {
                            System.err.println("O nome do perfil não pode estar vazio.");
                            break;
                        }
                        perfilDao.adicionarPerfil(new Perfil(0, nome));
                        System.out.println("Perfil adicionado com sucesso.");
                        break;
                    case 2:
                        List<Perfil> perfis = perfilDao.obterTodosOsPerfis();
                        perfis.forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Digite o ID do perfil para atualizar: ");
                        int idAtualizar = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Digite o novo nome do perfil: ");
                        String novoNome = scanner.nextLine();
                        if (novoNome.trim().isEmpty()) {
                            System.err.println("O nome do perfil não pode estar vazio.");
                            break;
                        }
                        if (perfilDao.atualizarPerfil(new Perfil(idAtualizar, novoNome))) {
                            System.out.println("Perfil atualizado com sucesso.");
                        } else {
                            System.err.println("Perfil não encontrado ou não pôde ser atualizado.");
                        }
                        break;
                    case 4:
                        System.out.print("Digite o ID do perfil para deletar: ");
                        int idDeletar = scanner.nextInt();
                        scanner.nextLine();
                        if (perfilDao.deletarPerfil(idDeletar)) {
                            System.out.println("Perfil deletado com sucesso.");
                        } else {
                            System.err.println("Perfil não encontrado ou não pôde ser deletado.");
                        }
                        break;
                    case 5:
                        return;
                    default:
                        System.err.println("Opção inválida. Por favor, tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }

    private static void mostrarMenuUsuarios() {
        while (true) {
            System.out.println("\n--- Gerenciar Usuários ---");
            System.out.println("1. Adicionar Usuário");
            System.out.println("2. Listar Todos os Usuários");
            System.out.println("3. Atualizar Usuário");
            System.out.println("4. Deletar Usuário");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        System.out.print("Digite o nome de usuário: ");
                        String nomeUsuario = scanner.nextLine();
                        System.out.print("Digite a senha: ");
                        String senha = scanner.nextLine();
                        System.out.print("Digite o ID do perfil: ");
                        int perfilId = scanner.nextInt();
                        scanner.nextLine();

                        if (nomeUsuario.trim().isEmpty() || senha.trim().isEmpty()) {
                            System.err.println("Nome de usuário e senha não podem estar vazios.");
                            break;
                        }

                        Perfil perfil = perfilDao.obterPerfilPorId(perfilId);
                        if (perfil == null) {
                            System.err.println("Perfil não encontrado.");
                            break;
                        }
                        usuarioDao.adicionarUsuario(new Usuario(0, nomeUsuario, senha, perfil));
                        System.out.println("Usuário adicionado com sucesso.");
                        break;
                    case 2:
                        List<Usuario> usuarios = usuarioDao.obterTodosOsUsuarios();
                        usuarios.forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Digite o ID do usuário para atualizar: ");
                        int idAtualizar = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Digite o novo nome de usuário: ");
                        String novoNomeUsuario = scanner.nextLine();
                        System.out.print("Digite a nova senha: ");
                        String novaSenha = scanner.nextLine();
                        System.out.print("Digite o novo ID do perfil: ");
                        int novoPerfilId = scanner.nextInt();
                        scanner.nextLine();

                        if (novoNomeUsuario.trim().isEmpty() || novaSenha.trim().isEmpty()) {
                            System.err.println("Nome de usuário e senha não podem estar vazios.");
                            break;
                        }

                        Perfil novoPerfil = perfilDao.obterPerfilPorId(novoPerfilId);
                        if (novoPerfil == null) {
                            System.err.println("Perfil não encontrado.");
                            break;
                        }
                        if (usuarioDao.atualizarUsuario(new Usuario(idAtualizar, novoNomeUsuario, novaSenha, novoPerfil))) {
                            System.out.println("Usuário atualizado com sucesso.");
                        } else {
                            System.err.println("Usuário não encontrado ou não pôde ser atualizado.");
                        }
                        break;
                    case 4:
                        System.out.print("Digite o ID do usuário para deletar: ");
                        int idDeletar = scanner.nextInt();
                        scanner.nextLine();
                        if (usuarioDao.deletarUsuario(idDeletar)) {
                            System.out.println("Usuário deletado com sucesso.");
                        } else {
                            System.err.println("Usuário não encontrado ou não pôde ser deletado.");
                        }
                        break;
                    case 5:
                        return;
                    default:
                        System.err.println("Opção inválida. Por favor, tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }

    private static void mostrarMenuClientes() {
        while (true) {
            System.out.println("\n--- Gerenciar Clientes ---");
            System.out.println("1. Adicionar Cliente");
            System.out.println("2. Listar Todos os Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Deletar Cliente");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        System.out.print("Digite o nome do cliente: ");
                        String nome = scanner.nextLine();
                        System.out.print("Digite o email do cliente: ");
                        String email = scanner.nextLine();
                        System.out.print("Digite o telefone do cliente: ");
                        String telefone = scanner.nextLine();

                        if (nome.trim().isEmpty() || email.trim().isEmpty()) {
                            System.err.println("Nome e email não podem estar vazios.");
                            break;
                        }

                        clienteDao.adicionarCliente(new Cliente(0, nome, email, telefone));
                        System.out.println("Cliente adicionado com sucesso.");
                        break;
                    case 2:
                        List<Cliente> clientes = clienteDao.obterTodosOsClientes();
                        clientes.forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Digite o ID do cliente para atualizar: ");
                        int idAtualizar = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Digite o novo nome: ");
                        String novoNome = scanner.nextLine();
                        System.out.print("Digite o novo email: ");
                        String novoEmail = scanner.nextLine();
                        System.out.print("Digite o novo telefone: ");
                        String novoTelefone = scanner.nextLine();

                        if (novoNome.trim().isEmpty() || novoEmail.trim().isEmpty()) {
                            System.err.println("Nome e email não podem estar vazios.");
                            break;
                        }

                        if (clienteDao.atualizarCliente(new Cliente(idAtualizar, novoNome, novoEmail, novoTelefone))) {
                            System.out.println("Cliente atualizado com sucesso.");
                        } else {
                            System.err.println("Cliente não encontrado ou não pôde ser atualizado.");
                        }
                        break;
                    case 4:
                        System.out.print("Digite o ID do cliente para deletar: ");
                        int idDeletar = scanner.nextInt();
                        scanner.nextLine();
                        if (clienteDao.deletarCliente(idDeletar)) {
                            System.out.println("Cliente deletado com sucesso.");
                        } else {
                            System.err.println("Cliente não encontrado ou não pôde ser deletado.");
                        }
                        break;
                    case 5:
                        return;
                    default:
                        System.err.println("Opção inválida. Por favor, tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }

    private static void mostrarMenuProdutos() {
        while (true) {
            System.out.println("\n--- Gerenciar Produtos ---");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Listar Todos os Produtos");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Deletar Produto");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        System.out.print("Digite o nome do produto: ");
                        String nome = scanner.nextLine();
                        System.out.print("Digite a descrição do produto: ");
                        String descricao = scanner.nextLine();
                        System.out.print("Digite o preço do produto: ");
                        BigDecimal preco = scanner.nextBigDecimal();
                        scanner.nextLine();

                        if (nome.trim().isEmpty()) {
                            System.err.println("O nome do produto não pode estar vazio.");
                            break;
                        }

                        produtoDao.adicionarProduto(new Produto(0, nome, descricao, preco));
                        System.out.println("Produto adicionado com sucesso.");
                        break;
                    case 2:
                        List<Produto> produtos = produtoDao.obterTodosOsProdutos();
                        produtos.forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Digite o ID do produto para atualizar: ");
                        int idAtualizar = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Digite o novo nome: ");
                        String novoNome = scanner.nextLine();
                        System.out.print("Digite a nova descrição: ");
                        String novaDescricao = scanner.nextLine();
                        System.out.print("Digite o novo preço: ");
                        BigDecimal novoPreco = scanner.nextBigDecimal();
                        scanner.nextLine();

                        if (novoNome.trim().isEmpty()) {
                            System.err.println("O nome do produto não pode estar vazio.");
                            break;
                        }

                        if (produtoDao.atualizarProduto(new Produto(idAtualizar, novoNome, novaDescricao, novoPreco))) {
                            System.out.println("Produto atualizado com sucesso.");
                        } else {
                            System.err.println("Produto não encontrado ou não pôde ser atualizado.");
                        }
                        break;
                    case 4:
                        System.out.print("Digite o ID do produto para deletar: ");
                        int idDeletar = scanner.nextInt();
                        scanner.nextLine();
                        if (produtoDao.deletarProduto(idDeletar)) {
                            System.out.println("Produto deletado com sucesso.");
                        } else {
                            System.err.println("Produto não encontrado ou não pôde ser deletado.");
                        }
                        break;
                    case 5:
                        return;
                    default:
                        System.err.println("Opção inválida. Por favor, tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }
}
