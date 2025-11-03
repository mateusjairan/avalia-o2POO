package com.example.gui;

import javax.swing.*;

public class PainelPrincipal extends JPanel {

    public PainelPrincipal() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTabbedPane abas = new JTabbedPane();

        abas.addTab("Perfis", new PainelPerfis());
        abas.addTab("Usuários", new PainelUsuarios());
        abas.addTab("Clientes", new PainelClientes());
        abas.addTab("Produtos", new JPanel()); // Será implementado a seguir

        add(abas);
    }
}
