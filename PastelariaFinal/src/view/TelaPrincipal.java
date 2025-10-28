package view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {

        setTitle("Pastelaria 🥟");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnPedido = new JButton("🛒 Fazer Pedido");
        JButton btnListar = new JButton("📋 Ver Todos Pasteis");
        JButton btnGerenciar = new JButton("➕ Adicionar / Remover Pasteis");

        // Adiciona as funções para cada botão abrir a tela certa

        btnListar.addActionListener(e -> new TelaListarPasteis());
        btnGerenciar.addActionListener(e -> new TelaAdicionarRemoverPastel());
        btnPedido.addActionListener(e -> new TelaPedido());

        add(btnPedido);
        add(btnListar);
        add(btnGerenciar);

        setVisible(true);
    }
}
