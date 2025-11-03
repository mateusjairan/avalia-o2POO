package com.example.gui;

import com.example.DataAccessException;
import com.example.Perfil;
import com.example.PerfilDao;
import com.example.Usuario;
import com.example.UsuarioDao;

import javax.swing.*;

public class AplicacaoGUI {

    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Sistema de Gerenciamento");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            mostrarTelaLogin();

            frame.setVisible(true);
        });
    }

    public static void mostrarTelaLogin() {
        PainelLogin painelLogin = new PainelLogin(AplicacaoGUI::mostrarTelaPrincipal, AplicacaoGUI::mostrarTelaCadastro);
        frame.setContentPane(painelLogin);
        frame.revalidate();
        frame.repaint();
    }

    public static void mostrarTelaPrincipal() {
        TelaPrincipal telaPrincipal = new TelaPrincipal();
        frame.setContentPane(telaPrincipal);
        frame.revalidate();
        frame.repaint();
    }

    public static void mostrarTelaCadastro() {
        JTextField campoNomeUsuario = new JTextField();
        JPasswordField campoSenha = new JPasswordField();

        JPanel painelDialogo = new JPanel();
        painelDialogo.setLayout(new BoxLayout(painelDialogo, BoxLayout.Y_AXIS));
        painelDialogo.add(new JLabel("Nome de Usuário:"));
        painelDialogo.add(campoNomeUsuario);
        painelDialogo.add(new JLabel("Senha:"));
        painelDialogo.add(campoSenha);

        int resultado = JOptionPane.showConfirmDialog(frame, painelDialogo, "Cadastro de Novo Usuário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String nomeUsuario = campoNomeUsuario.getText();
            String senha = new String(campoSenha.getPassword());

            if (nomeUsuario.trim().isEmpty() || senha.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nome de usuário e senha não podem estar vazios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                PerfilDao perfilDao = new PerfilDao();
                // Assumimos que o perfil 'Usuario' tem ID 2. Em um sistema real, isso seria mais robusto.
                Perfil perfilUsuario = perfilDao.obterPerfilPorNome("Usuario");
                if (perfilUsuario == null) {
                    JOptionPane.showMessageDialog(frame, "Perfil 'Usuario' não encontrado. Cadastre-o primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                UsuarioDao usuarioDao = new UsuarioDao();
                Usuario novoUsuario = new Usuario(0, nomeUsuario, senha, perfilUsuario);
                usuarioDao.adicionarUsuario(novoUsuario);

                JOptionPane.showMessageDialog(frame, "Usuário cadastrado com sucesso! Você já pode fazer o login.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (DataAccessException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao cadastrar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
