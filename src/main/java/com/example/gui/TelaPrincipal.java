package com.example.gui;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JPanel {

    public TelaPrincipal() {
        setLayout(new BorderLayout());

        // Painel superior com o botão de logout
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botaoLogout = new JButton("Logout");
        botaoLogout.addActionListener(e -> {
            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja sair?", "Confirmar Logout", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                // Chama a lógica de logout na classe principal da GUI
                AplicacaoGUI.mostrarTelaLogin();
            }
        });
        painelSuperior.add(botaoLogout);
        add(painelSuperior, BorderLayout.NORTH);

        // Painel de abas com os CRUDs
        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Perfis", new PainelPerfis());
        abas.addTab("Usuários", new PainelUsuarios());
        abas.addTab("Clientes", new PainelClientes());
        abas.addTab("Produtos", new PainelProdutos());

        add(abas, BorderLayout.CENTER);
    }
}
