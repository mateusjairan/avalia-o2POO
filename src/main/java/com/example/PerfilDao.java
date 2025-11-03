package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerfilDao {

    public void adicionarPerfil(Perfil perfil) {
        if (obterPerfilPorNome(perfil.getNome()) != null) {
            throw new DataAccessException("Erro: Já existe um perfil com este nome.", null);
        }

        String sql = "INSERT INTO perfis (nome) VALUES (?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, perfil.getNome());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    perfil.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao adicionar perfil.", e);
        }
    }

    public List<Perfil> obterTodosOsPerfis() {
        List<Perfil> perfis = new ArrayList<>();
        String sql = "SELECT * FROM perfis";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Perfil perfil = new Perfil();
                perfil.setId(rs.getInt("id"));
                perfil.setNome(rs.getString("nome"));
                perfis.add(perfil);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao obter perfis.", e);
        }
        return perfis;
    }

    public Perfil obterPerfilPorId(int id) {
        Perfil perfil = null;
        String sql = "SELECT * FROM perfis WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    perfil = new Perfil();
                    perfil.setId(rs.getInt("id"));
                    perfil.setNome(rs.getString("nome"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao obter perfil por ID.", e);
        }
        return perfil;
    }

    public Perfil obterPerfilPorNome(String nome) {
        Perfil perfil = null;
        String sql = "SELECT * FROM perfis WHERE nome = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    perfil = new Perfil();
                    perfil.setId(rs.getInt("id"));
                    perfil.setNome(rs.getString("nome"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao obter perfil por nome.", e);
        }
        return perfil;
    }

    public boolean atualizarPerfil(Perfil perfil) {
        if (obterPerfilPorNome(perfil.getNome()) != null && obterPerfilPorNome(perfil.getNome()).getId() != perfil.getId()) {
            throw new DataAccessException("Erro: Outro perfil com este nome já existe.", null);
        }

        String sql = "UPDATE perfis SET nome = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, perfil.getNome());
            pstmt.setInt(2, perfil.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao atualizar perfil.", e);
        }
    }

    public boolean deletarPerfil(int id) {
        String sql = "DELETE FROM perfis WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao deletar perfil.", e);
        }
    }
}
