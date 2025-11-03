package com.example.gui;

import com.example.ServicoAutenticacao;

import javax.swing.*;
import java.awt.*;

public class PainelLogin extends JPanel {

    private final ServicoAutenticacao servicoAutenticacao;
    private final JTextField campoUsuario;
    private final JPasswordField campoSenha;

    // O callback para o cadastro será usado na próxima etapa.
    public PainelLogin(Runnable loginCallback, Runnable registerCallback) {
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

        JButton botaoCadastrar = new JButton("Cadastrar-se");
        botaoCadastrar.addActionListener(e -> registerCallback.run());

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelUsuario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(campoUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(labelSenha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(campoSenha, gbc);

        // Painel para os botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoLogin);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(painelBotoes, gbc);
    }
}
