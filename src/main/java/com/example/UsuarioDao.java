package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    public void adicionarUsuario(Usuario usuario) {
        if (obterUsuarioPorNomeUsuario(usuario.getNomeUsuario()) != null) {
            System.err.println("Erro: Já existe um usuário com este nome de usuário.");
            return;
        }

        String sql = "INSERT INTO usuarios (nome_usuario, senha, perfil_id) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, usuario.getNomeUsuario());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setInt(3, usuario.getPerfil().getId());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar usuário: " + e.getMessage());
        }
    }

    public List<Usuario> obterTodosOsUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.id, u.nome_usuario, u.senha, p.id as perfil_id, p.nome as perfil_nome " +
                     "FROM usuarios u JOIN perfis p ON u.perfil_id = p.id";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Perfil perfil = new Perfil(rs.getInt("perfil_id"), rs.getString("perfil_nome"));
                Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("nome_usuario"), rs.getString("senha"), perfil);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter usuários: " + e.getMessage());
        }
        return usuarios;
    }

    public Usuario obterUsuarioPorId(int id) {
        Usuario usuario = null;
        String sql = "SELECT u.id, u.nome_usuario, u.senha, p.id as perfil_id, p.nome as perfil_nome " +
                     "FROM usuarios u JOIN perfis p ON u.perfil_id = p.id WHERE u.id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Perfil perfil = new Perfil(rs.getInt("perfil_id"), rs.getString("perfil_nome"));
                    usuario = new Usuario(rs.getInt("id"), rs.getString("nome_usuario"), rs.getString("senha"), perfil);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter usuário por ID: " + e.getMessage());
        }
        return usuario;
    }

    public Usuario obterUsuarioPorNomeUsuario(String nomeUsuario) {
        Usuario usuario = null;
        String sql = "SELECT u.id, u.nome_usuario, u.senha, p.id as perfil_id, p.nome as perfil_nome " +
                "FROM usuarios u JOIN perfis p ON u.perfil_id = p.id WHERE u.nome_usuario = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomeUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Perfil perfil = new Perfil(rs.getInt("perfil_id"), rs.getString("perfil_nome"));
                    usuario = new Usuario(rs.getInt("id"), rs.getString("nome_usuario"), rs.getString("senha"), perfil);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter usuário por nome de usuário: " + e.getMessage());
        }
        return usuario;
    }

    public boolean atualizarUsuario(Usuario usuario) {
        Usuario usuarioExistente = obterUsuarioPorNomeUsuario(usuario.getNomeUsuario());
        if (usuarioExistente != null && usuarioExistente.getId() != usuario.getId()) {
            System.err.println("Erro: Outro usuário com este nome de usuário já existe.");
            return false;
        }

        String sql = "UPDATE usuarios SET nome_usuario = ?, senha = ?, perfil_id = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNomeUsuario());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setInt(3, usuario.getPerfil().getId());
            pstmt.setInt(4, usuario.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
            return false;
        }
    }
}
