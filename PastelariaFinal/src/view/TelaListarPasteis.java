package view;

import controller.PastelController;
import model.Pastel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class TelaListarPasteis extends JFrame {

    private final PastelController controller;
    private final DefaultTableModel model;
    private JTextField txtBuscar;

    public TelaListarPasteis() {

        controller = new PastelController();

        // Visual

        setTitle("Lista de Pasteis");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Painel de busca
        JPanel painelBusca = new JPanel();
        painelBusca.add(new JLabel("Buscar sabor:"));

        txtBuscar = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnListarTodos = new JButton("Listar todos");

        painelBusca.add(txtBuscar);
        painelBusca.add(btnBuscar);
        painelBusca.add(btnListarTodos);

        add(painelBusca, BorderLayout.NORTH); // Adiciona no Topo

        // Tabela
        model = new DefaultTableModel(new String[]{"ID", "Sabor", "Preço"}, 0) {

        @Override
        public boolean isCellEditable(int row, int column) {return false;  } }; // impede edição das células

        JTable tabela = new JTable(model);
        add(new JScrollPane(tabela), BorderLayout.CENTER); // Adiciona no centro da tela

        // Eventos
        btnBuscar.addActionListener(this::buscarPorNome);
        btnListarTodos.addActionListener(e -> listarPasteis());

        listarPasteis(); // carrega todos no início
        setVisible(true);
    }

    // Executa o listar pasteis
    private void listarPasteis() {
        try {
            List<Pastel> lista = controller.listarPasteis();
            atualizarTabela(lista);
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar pasteis: " + e.getMessage());
        }
    }
    // Executa o buscar por Nome
    private void buscarPorNome(ActionEvent e) {
        String nome = txtBuscar.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um sabor para buscar.");
            return;
        }

        try {
            List<Pastel> lista = controller.buscarPasteisPorNome(nome);
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum pastel encontrado com esse nome.");
            }
            atualizarTabela(lista);
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar pastel: " + ex.getMessage());
        }
    }

    // Popula a tabela com os itens do banco de Dados, e dependendo do tipo de (lista), mostrará os devidos resultados
    private void atualizarTabela(List<Pastel> lista) {
        model.setRowCount(0); // Remove as linhas
        for (Pastel p : lista) { // Para cada pastel da lista ele adiciona na tabela
            model.addRow(new Object[]{p.getIdPasteis(), p.getSabor(), p.getPreco()});
        }
    }
}
