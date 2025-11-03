package com.example;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {

    public void adicionarProduto(Produto produto) {
        if (obterProdutoPorNome(produto.getNome()) != null) {
            System.err.println("Erro: Já existe um produto com este nome.");
            return;
        }

        String sql = "INSERT INTO produtos (nome, descricao, preco) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, produto.getNome());
            pstmt.setString(2, produto.getDescricao());
            pstmt.setBigDecimal(3, produto.getPreco());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    produto.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    public List<Produto> obterTodosOsProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getBigDecimal("preco"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter produtos: " + e.getMessage());
        }
        return produtos;
    }

    public Produto obterProdutoPorId(int id) {
        Produto produto = null;
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPreco(rs.getBigDecimal("preco"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter produto por ID: " + e.getMessage());
        }
        return produto;
    }

    public Produto obterProdutoPorNome(String nome) {
        Produto produto = null;
        String sql = "SELECT * FROM produtos WHERE nome = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPreco(rs.getBigDecimal("preco"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter produto por nome: " + e.getMessage());
        }
        return produto;
    }

    public boolean atualizarProduto(Produto produto) {
        Produto produtoExistente = obterProdutoPorNome(produto.getNome());
        if (produtoExistente != null && produtoExistente.getId() != produto.getId()) {
            System.err.println("Erro: Outro produto com este nome já existe.");
            return false;
        }

        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, produto.getNome());
            pstmt.setString(2, produto.getDescricao());
            pstmt.setBigDecimal(3, produto.getPreco());
            pstmt.setInt(4, produto.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarProduto(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar produto: " + e.getMessage());
            return false;
        }
    }
}
