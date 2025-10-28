package view;

import controller.PastelController;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TelaAdicionarRemoverPastel extends JFrame {
    // Cria os controller
    private final PastelController controller;
    // Inicializa o Sabor e Preço
    private JTextField txtSabor, txtPreco;

    public TelaAdicionarRemoverPastel() {
        // Cria o controller para pedir informações do DAO
        controller = new PastelController();

        // Visual

        setTitle("Gerenciar Pasteis");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));

        painel.add(new JLabel("Sabor:"));
        txtSabor = new JTextField();
        painel.add(txtSabor);

        painel.add(new JLabel("Preço:"));
        txtPreco = new JTextField();
        painel.add(txtPreco);

        JButton btnAdd = new JButton("Adicionar");
        painel.add(btnAdd);
        JButton btnRemove = new JButton("Remover");
        painel.add(btnRemove);

        // Adiciona ações aos botões
        btnAdd.addActionListener(e -> adicionarPastel());
        btnRemove.addActionListener(e -> removerPastel());

        // Adiciona o painel com os botões e todos os labels
        add(painel);
        setVisible(true);
    }

    private void limpar(){
        txtSabor.setText("");
        txtPreco.setText("");
    };
    private void adicionarPastel() {
        try {
            String sabor = txtSabor.getText().trim(); // Pega o sabor do pastel
            double preco = Double.parseDouble(txtPreco.getText().trim()); // Pega o texto do preço e remove os espaços em branco -- trim() --

            if (sabor.isEmpty()) { // se nao escrever o nome do sabor , o programa da return direto , ou seja nao adicionar nada
                JOptionPane.showMessageDialog(this, "O campo 'Sabor' não pode estar vazio.");
                return;
            }
            if (preco <= 0) { // se o preço for menor ou 0 , nao adiciona
                JOptionPane.showMessageDialog(this, "O pastel não pode ser de graça ou não podemos pagar pra você comer");
                return;
            }
            if (preco >= 99) { // se o preço for maior que 99 , nao adiciona
                JOptionPane.showMessageDialog(this, "Muito caro para um pastel");
                return;
            }

            // puxa o controller para adicionar no banco de dados
            controller.adicionarPastel(sabor, preco);
            JOptionPane.showMessageDialog(this, "Pastel adicionado com sucesso!");

            limpar();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar: " + e.getMessage());
            limpar();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido.");
            limpar();
        }

    }

    private void removerPastel() {
        try {
            String sabor = txtSabor.getText();
            controller.removerPastel(sabor);

            JOptionPane.showMessageDialog(this, "Pastel removido com sucesso!");

            limpar();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao remover: " + e.getMessage());
            limpar();
        }
    }
}
