package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    public void adicionarCliente(Cliente cliente) {
        if (obterClientePorEmail(cliente.getEmail()) != null) {
            throw new DataAccessException("Erro: Já existe um cliente com este email.", null);
        }

        String sql = "INSERT INTO clientes (nome, email, telefone) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao adicionar cliente.", e);
        }
    }

    public List<Cliente> obterTodosOsClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao obter clientes.", e);
        }
        return clientes;
    }

    public Cliente obterClientePorId(int id) {
        Cliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setTelefone(rs.getString("telefone"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao obter cliente por ID.", e);
        }
        return cliente;
    }

    public Cliente obterClientePorEmail(String email) {
        Cliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE email = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setTelefone(rs.getString("telefone"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao obter cliente por email.", e);
        }
        return cliente;
    }

    public boolean atualizarCliente(Cliente cliente) {
        Cliente clienteExistente = obterClientePorEmail(cliente.getEmail());
        if (clienteExistente != null && clienteExistente.getId() != cliente.getId()) {
            throw new DataAccessException("Erro: Outro cliente com este email já existe.", null);
        }

        String sql = "UPDATE clientes SET nome = ?, email = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.setInt(4, cliente.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao atualizar cliente.", e);
        }
    }

    public boolean deletarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao deletar cliente.", e);
        }
    }
}
