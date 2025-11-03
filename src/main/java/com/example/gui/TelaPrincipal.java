package com.example.gui;

import javax.swing.*;

public class TelaPrincipal extends JPanel {

    public TelaPrincipal() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTabbedPane abas = new JTabbedPane();

        abas.addTab("Perfis", new PainelPerfis());
        abas.addTab("Usuários", new PainelUsuarios());
        abas.addTab("Clientes", new PainelClientes());
        abas.addTab("Produtos", new PainelProdutos());

        add(abas);
    }
}
