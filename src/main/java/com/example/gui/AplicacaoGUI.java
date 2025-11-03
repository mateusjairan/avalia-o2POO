package com.example.gui;

import com.example.Database;

import javax.swing.*;

public class AplicacaoGUI {

    private static JFrame frame;

    public static void main(String[] args) {
        Database.initialize();

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
        PainelLogin painelLogin = new PainelLogin(AplicacaoGUI::mostrarTelaPrincipal);
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
}
