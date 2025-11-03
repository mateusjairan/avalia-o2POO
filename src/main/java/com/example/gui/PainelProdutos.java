package com.example.gui;

import com.example.Produto;
import com.example.ProdutoDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class PainelProdutos extends JPanel {

    private final ProdutoDao produtoDao;
    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    public PainelProdutos() {
        this.produtoDao = new ProdutoDao();
        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição", "Preço"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);

        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton botaoAdicionar = new JButton("Adicionar");
        JButton botaoEditar = new JButton("Editar");
        JButton botaoDeletar = new JButton("Deletar");

        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoDeletar);

        add(painelBotoes, BorderLayout.SOUTH);

        botaoAdicionar.addActionListener(e -> adicionarProduto());
        botaoEditar.addActionListener(e -> editarProduto());
        botaoDeletar.addActionListener(e -> deletarProduto());

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Produto> produtos = produtoDao.obterTodosOsProdutos();
        for (Produto produto : produtos) {
            modeloTabela.addRow(new Object[]{produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco()});
        }
    }

    private void adicionarProduto() {
        JTextField campoNome = new JTextField();
        JTextField campoDescricao = new JTextField();
        JTextField campoPreco = new JTextField();

        JPanel painelDialogo = new JPanel(new GridLayout(0, 2, 5, 5));
        painelDialogo.add(new JLabel("Nome:"));
        painelDialogo.add(campoNome);
        painelDialogo.add(new JLabel("Descrição:"));
        painelDialogo.add(campoDescricao);
        painelDialogo.add(new JLabel("Preço:"));
        painelDialogo.add(campoPreco);

        int resultado = JOptionPane.showConfirmDialog(this, painelDialogo, "Adicionar Produto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            String nome = campoNome.getText();
            String descricao = campoDescricao.getText();
            String precoStr = campoPreco.getText();

            if (nome.trim().isEmpty() || precoStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome and Preço cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                BigDecimal preco = new BigDecimal(precoStr);
                Produto produto = new Produto(0, nome, descricao, preco);
                produtoDao.adicionarProduto(produto);
                atualizarTabela();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Preço inválido. Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarProduto() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        Produto produtoAtual = produtoDao.obterProdutoPorId(id);

        JTextField campoNome = new JTextField(produtoAtual.getNome());
        JTextField campoDescricao = new JTextField(produtoAtual.getDescricao());
        JTextField campoPreco = new JTextField(produtoAtual.getPreco().toString());

        JPanel painelDialogo = new JPanel(new GridLayout(0, 2, 5, 5));
        painelDialogo.add(new JLabel("Nome:"));
        painelDialogo.add(campoNome);
        painelDialogo.add(new JLabel("Descrição:"));
        painelDialogo.add(campoDescricao);
        painelDialogo.add(new JLabel("Preço:"));
        painelDialogo.add(campoPreco);

        int resultado = JOptionPane.showConfirmDialog(this, painelDialogo, "Editar Produto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            String nome = campoNome.getText();
            String descricao = campoDescricao.getText();
            String precoStr = campoPreco.getText();

            if (nome.trim().isEmpty() || precoStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e preço não podem estar vazios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                BigDecimal preco = new BigDecimal(precoStr);
                Produto produto = new Produto(id, nome, descricao, preco);
                if (produtoDao.atualizarProduto(produto)) {
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível atualizar o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Preço inválido. Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deletarProduto() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este produto?", "Confirmar Deleção", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            if (produtoDao.deletarProduto(id)) {
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível deletar o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
