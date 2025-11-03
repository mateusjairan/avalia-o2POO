package com.example.gui;

import com.example.Cliente;
import com.example.ClienteDao;
import com.example.DataAccessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelClientes extends JPanel {

    private final ClienteDao clienteDao;
    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    public PainelClientes() {
        this.clienteDao = new ClienteDao();
        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Email", "Telefone"}, 0) {
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

        botaoAdicionar.addActionListener(e -> adicionarCliente());
        botaoEditar.addActionListener(e -> editarCliente());
        botaoDeletar.addActionListener(e -> deletarCliente());

        atualizarTabela();
    }

    private void atualizarTabela() {
        try {
            modeloTabela.setRowCount(0);
            List<Cliente> clientes = clienteDao.obterTodosOsClientes();
            for (Cliente cliente : clientes) {
                modeloTabela.addRow(new Object[]{cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getTelefone()});
            }
        } catch (DataAccessException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Acesso aos Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarCliente() {
        JTextField campoNome = new JTextField();
        JTextField campoEmail = new JTextField();
        JTextField campoTelefone = new JTextField();

        JPanel painelDialogo = new JPanel(new GridLayout(0, 2, 5, 5));
        painelDialogo.add(new JLabel("Nome:"));
        painelDialogo.add(campoNome);
        painelDialogo.add(new JLabel("Email:"));
        painelDialogo.add(campoEmail);
        painelDialogo.add(new JLabel("Telefone:"));
        painelDialogo.add(campoTelefone);

        int resultado = JOptionPane.showConfirmDialog(this, painelDialogo, "Adicionar Cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            String nome = campoNome.getText();
            String email = campoEmail.getText();
            String telefone = campoTelefone.getText();

            if (nome.trim().isEmpty() || email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e email não podem estar vazios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Cliente cliente = new Cliente(0, nome, email, telefone);
                clienteDao.adicionarCliente(cliente);
                atualizarTabela();
            } catch (DataAccessException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Adicionar Cliente", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarCliente() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        Cliente clienteAtual = clienteDao.obterClientePorId(id);

        JTextField campoNome = new JTextField(clienteAtual.getNome());
        JTextField campoEmail = new JTextField(clienteAtual.getEmail());
        JTextField campoTelefone = new JTextField(clienteAtual.getTelefone());

        JPanel painelDialogo = new JPanel(new GridLayout(0, 2, 5, 5));
        painelDialogo.add(new JLabel("Nome:"));
        painelDialogo.add(campoNome);
        painelDialogo.add(new JLabel("Email:"));
        painelDialogo.add(campoEmail);
        painelDialogo.add(new JLabel("Telefone:"));
        painelDialogo.add(campoTelefone);

        int resultado = JOptionPane.showConfirmDialog(this, painelDialogo, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            String nome = campoNome.getText();
            String email = campoEmail.getText();
            String telefone = campoTelefone.getText();

            if (nome.trim().isEmpty() || email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e email não podem estar vazios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Cliente cliente = new Cliente(id, nome, email, telefone);
                if (clienteDao.atualizarCliente(cliente)) {
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível atualizar o cliente (cliente não encontrado).", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (DataAccessException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Atualizar Cliente", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deletarCliente() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este cliente?", "Confirmar Deleção", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                if (clienteDao.deletarCliente(id)) {
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível deletar o cliente (cliente não encontrado).", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (DataAccessException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Deletar Cliente", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
