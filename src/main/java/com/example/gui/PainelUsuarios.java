package com.example.gui;

import com.example.Perfil;
import com.example.PerfilDao;
import com.example.Usuario;
import com.example.UsuarioDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelUsuarios extends JPanel {

    private final UsuarioDao usuarioDao;
    private final PerfilDao perfilDao;
    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    public PainelUsuarios() {
        this.usuarioDao = new UsuarioDao();
        this.perfilDao = new PerfilDao();
        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome de Usuário", "Perfil"}, 0) {
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

        botaoAdicionar.addActionListener(e -> adicionarUsuario());
        botaoEditar.addActionListener(e -> editarUsuario());
        botaoDeletar.addActionListener(e -> deletarUsuario());

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Usuario> usuarios = usuarioDao.obterTodosOsUsuarios();
        for (Usuario usuario : usuarios) {
            modeloTabela.addRow(new Object[]{usuario.getId(), usuario.getNomeUsuario(), usuario.getPerfil().getNome()});
        }
    }

    private void adicionarUsuario() {
        JTextField campoNomeUsuario = new JTextField();
        JPasswordField campoSenha = new JPasswordField();
        JComboBox<Perfil> comboPerfis = new JComboBox<>();

        List<Perfil> perfis = perfilDao.obterTodosOsPerfis();
        for (Perfil perfil : perfis) {
            comboPerfis.addItem(perfil);
        }

        JPanel painelDialogo = new JPanel(new GridLayout(0, 2, 5, 5));
        painelDialogo.add(new JLabel("Nome de Usuário:"));
        painelDialogo.add(campoNomeUsuario);
        painelDialogo.add(new JLabel("Senha:"));
        painelDialogo.add(campoSenha);
        painelDialogo.add(new JLabel("Perfil:"));
        painelDialogo.add(comboPerfis);

        int resultado = JOptionPane.showConfirmDialog(this, painelDialogo, "Adicionar Usuário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            String nomeUsuario = campoNomeUsuario.getText();
            String senha = new String(campoSenha.getPassword());
            Perfil perfilSelecionado = (Perfil) comboPerfis.getSelectedItem();

            if (nomeUsuario.trim().isEmpty() || senha.trim().isEmpty() || perfilSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuario = new Usuario(0, nomeUsuario, senha, perfilSelecionado);
            usuarioDao.adicionarUsuario(usuario);
            atualizarTabela();
        }
    }

    private void editarUsuario() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        Usuario usuarioAtual = usuarioDao.obterUsuarioPorId(id);

        JTextField campoNomeUsuario = new JTextField(usuarioAtual.getNomeUsuario());
        JPasswordField campoSenha = new JPasswordField();
        JComboBox<Perfil> comboPerfis = new JComboBox<>();

        List<Perfil> perfis = perfilDao.obterTodosOsPerfis();
        for (Perfil perfil : perfis) {
            comboPerfis.addItem(perfil);
            if (perfil.getId() == usuarioAtual.getPerfil().getId()) {
                comboPerfis.setSelectedItem(perfil);
            }
        }

        JPanel painelDialogo = new JPanel(new GridLayout(0, 2, 5, 5));
        painelDialogo.add(new JLabel("Nome de Usuário:"));
        painelDialogo.add(campoNomeUsuario);
        painelDialogo.add(new JLabel("Nova Senha (deixe em branco para não alterar):"));
        painelDialogo.add(campoSenha);
        painelDialogo.add(new JLabel("Perfil:"));
        painelDialogo.add(comboPerfis);

        int resultado = JOptionPane.showConfirmDialog(this, painelDialogo, "Editar Usuário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            String nomeUsuario = campoNomeUsuario.getText();
            String senha = new String(campoSenha.getPassword());
            Perfil perfilSelecionado = (Perfil) comboPerfis.getSelectedItem();

            if (nomeUsuario.trim().isEmpty() || perfilSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Nome de usuário e perfil não podem estar vazios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (senha.trim().isEmpty()) {
                senha = usuarioAtual.getSenha(); // Mantém a senha atual se o campo for deixado em branco
            }

            Usuario usuario = new Usuario(id, nomeUsuario, senha, perfilSelecionado);
            if (usuarioDao.atualizarUsuario(usuario)) {
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível atualizar o usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deletarUsuario() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar este usuário?", "Confirmar Deleção", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            if (usuarioDao.deletarUsuario(id)) {
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível deletar o usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
