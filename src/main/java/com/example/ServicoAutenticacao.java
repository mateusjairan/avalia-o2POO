package com.example;

public class ServicoAutenticacao {

    private final UsuarioDao usuarioDao;
    private Usuario usuarioLogado;

    public ServicoAutenticacao() {
        this.usuarioDao = new UsuarioDao();
        this.usuarioLogado = null;
    }

    public boolean login(String nomeUsuario, String senha) {
        if (nomeUsuario == null || nomeUsuario.trim().isEmpty() || senha == null || senha.isEmpty()) {
            System.err.println("Nome de usuário e senha não podem estar vazios.");
            return false;
        }

        Usuario usuario = usuarioDao.obterUsuarioPorNomeUsuario(nomeUsuario);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            usuarioLogado = usuario;
            return true;
        }

        System.err.println("Nome de usuário ou senha inválidos.");
        return false;
    }

    public void logout() {
        usuarioLogado = null;
        System.out.println("Logout realizado com sucesso.");
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public boolean estaLogado() {
        return usuarioLogado != null;
    }
}
