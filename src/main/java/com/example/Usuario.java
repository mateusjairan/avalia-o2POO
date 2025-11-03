package com.example;

public class Usuario {

    private int id;
    private String nomeUsuario;
    private String senha;
    private Perfil perfil;

    public Usuario() {}

    public Usuario(int id, String nomeUsuario, String senha, Perfil perfil) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", perfil=" + (perfil != null ? perfil.getNome() : "null") +
                '}';
    }
}
