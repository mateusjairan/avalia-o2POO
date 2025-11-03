package com.example.gui;

import com.example.ServicoAutenticacao;

import javax.swing.*;
import java.awt.*;

public class PainelLogin extends JPanel {

    private final ServicoAutenticacao servicoAutenticacao;
    private final JTextField campoUsuario;
    private final JPasswordField campoSenha;

    public PainelLogin(Runnable loginCallback) {
        this.servicoAutenticacao = new ServicoAutenticacao();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel labelUsuario = new JLabel("Usuário:");
        campoUsuario = new JTextField(20);

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField(20);

        JButton botaoLogin = new JButton("Entrar");
        botaoLogin.addActionListener(e -> {
            String usuario = campoUsuario.getText();
            String senha = new String(campoSenha.getPassword());

            if (servicoAutenticacao.login(usuario, senha)) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                loginCallback.run(); // Chama o método para trocar de tela
            } else {
                JOptionPane.showMessageDialog(this, "Nome de usuário ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelUsuario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(campoUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(labelSenha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(campoSenha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(botaoLogin, gbc);
    }
}
