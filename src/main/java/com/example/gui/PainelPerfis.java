package com.example.gui;

import com.example.DataAccessException;
import com.example.Perfil;
import com.example.PerfilDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelPerfis extends JPanel {

    private final PerfilDao perfilDao;
    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    public PainelPerfis() {
        this.perfilDao = new PerfilDao();
        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome"}, 0);
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

        botaoAdicionar.addActionListener(e -> adicionarPerfil());
        botaoEditar.addActionListener(e -> editarPerfil());
        botaoDeletar.addActionListener(e -> deletarPerfil());

        atualizarTabela();
    }

    private void atualizarTabela() {
        try {
            modeloTabela.setRowCount(0); // Limpa a tabela
            List<Perfil> perfis = perfilDao.obterTodosOsPerfis();
            for (Perfil perfil : perfis) {
                modeloTabela.addRow(new Object[]{perfil.getId(), perfil.getNome()});
            }
        } catch (DataAccessException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Acesso aos Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarPerfil() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do perfil:", "Adicionar Perfil", JOptionPane.PLAIN_MESSAGE);
        if (nome != null && !nome.trim().isEmpty()) {
            try {
                Perfil perfil = new Perfil(0, nome);
                perfilDao.adicionarPerfil(perfil);
                atualizarTabela();
            } catch (DataAccessException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Adicionar Perfil", JOptionPane.ERROR_MESSAGE);
            }
        } else if (nome != null) {
            JOptionPane.showMessageDialog(this, "O nome do perfil não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarPerfil() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um perfil para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nomeAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 1);

        String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome do perfil:", nomeAtual);
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            try {
                Perfil perfil = new Perfil(id, novoNome);
                if (perfilDao.atualizarPerfil(perfil)) {
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível atualizar o perfil (perfil não encontrado).", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (DataAccessException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Atualizar Perfil", JOptionPane.ERROR_MESSAGE);
            }
        } else if (novoNome != null) {
            JOptionPane.showMessageDialog(this, "O nome do perfil não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarPerfil() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um perfil para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este perfil?", "Confirmar Deleção", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                if (perfilDao.deletarPerfil(id)) {
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível deletar o perfil (perfil não encontrado).", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (DataAccessException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Deletar Perfil", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
