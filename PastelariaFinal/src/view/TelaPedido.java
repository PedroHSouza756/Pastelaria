package view;

import controller.PastelController;
import controller.ProdutoController;
import controller.RefrigeranteController;
import model.Pastel;
import model.Refrigerante;
import model.Produto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelaPedido extends JFrame {

    private final PastelController pastelController;
    private final RefrigeranteController refriController;
    private final ProdutoController produtoController;
    private JComboBox<Produto> comboTipoProduto;
    private JComboBox<Object> comboProdutos;
    private DefaultTableModel tabelaModel;
    private JLabel lblTotal;
    private JButton btnFinalizar;
    private final List<Object> itensPedido = new ArrayList<>();

    public TelaPedido() {
        // Inicializa todos os Controllers
        pastelController = new PastelController();
        refriController = new RefrigeranteController();
        produtoController = new ProdutoController();

        setTitle("Fazer Pedido 🛒");
        setSize(650, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Visual

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Topo

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        // Cria as comboBox
        comboTipoProduto = new JComboBox<Produto>();
        comboProdutos = new JComboBox<>();
        JButton btnAdicionar = new JButton("Adicionar ao Pedido");

        painelTopo.add(new JLabel("Tipo:"));
        painelTopo.add(comboTipoProduto);

        painelTopo.add(new JLabel("Produto:"));
        painelTopo.add(comboProdutos);

        painelTopo.add(btnAdicionar);

        painel.add(painelTopo, BorderLayout.NORTH); // Topo da Tela

        // Tabela

        tabelaModel = new DefaultTableModel(new String[]{"Produto", "Preço"}, 0);
        JTable tabela = new JTable(tabelaModel);
        JScrollPane scroll = new JScrollPane(tabela); // Cria um ScrollPane com a tabela dentro dele
        scroll.setBorder(BorderFactory.createTitledBorder("Itens no Pedido"));

        painel.add(scroll, BorderLayout.CENTER); // Meio da Tela

        // Rodapé
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));

        lblTotal = new JLabel("Total: R$ 0.00"); // Inicializa o total como 0,00 porque nao iniciamos a compra

        btnFinalizar = new JButton("Finalizar Pedido");
        btnFinalizar.setBackground(new Color(0, 153, 51));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFocusPainted(false);

        painelRodape.add(lblTotal);
        painelRodape.add(btnFinalizar);

        painel.add(painelRodape, BorderLayout.SOUTH); // Adiciona no final da tela

        // --- Eventos ---
        comboTipoProduto.addActionListener(e -> carregarProdutos());
        btnAdicionar.addActionListener(e -> adicionarItem());
        btnFinalizar.addActionListener(e -> finalizarPedido());

        // --- Inicia todos os métodos ---
        carregarTiposProduto();
        carregarProdutos();

        setContentPane(painel);
        setVisible(true);
    }

    // Carrega as opções do usuário escolher entre Pastel e Bebida

    private void carregarTiposProduto() {
        comboTipoProduto.removeAllItems(); //Limpa a comboBox
        try {
            List<Produto> tipos = produtoController.listarTipos();
            // ✅ Filtra apenas IDs 1 e 2 como fizemos tabela em AWS ele libera mais opções que nao criamos, por isso limitamos no Id 1 - Pastel, Id 2 - Bebida
            for (Produto tipo : tipos) {
                if (tipo.getId() == 1 || tipo.getId() == 2) { // Para um banco maior, removeriamos essa parte
                    comboTipoProduto.addItem(tipo);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tipos: " + e.getMessage());
        }
    }

    private void carregarProdutos() {
        comboProdutos.removeAllItems();
        Produto produtoSelecionado = (Produto) comboTipoProduto.getSelectedItem(); // Pega a opção do usuário
        try {
            int idTipo = produtoSelecionado.getId(); // pega o ID do tipo selecionado
            if (idTipo == 1) { // 1 = Pastel
                for (Pastel p : pastelController.listarPasteis()) {
                    comboProdutos.addItem(p);
                }
            }
            else if (idTipo == 2) { // 2 = Bebida
                for (Refrigerante r : refriController.listarRefrigerantes()) {
                    comboProdutos.addItem(r);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void adicionarItem() {
        Object selecionado = comboProdutos.getSelectedItem(); // Um objeto sem classe, vai receber a opção selecionada na comboBox
        if (selecionado == null) return; // Vai ser impossivel nao selecionar nada porque o aplicativo carrega sempre uma primeira opção, mas fica o tratamento de erro

        // Cria duas variáveis para calcular o preço, e o item adicionado
        double preco = 0;
        String nome = "";

        if (selecionado instanceof Pastel pastel) { // verifica se o item adicionado é do model Pastel, se for ele pega o Preço e o Sabor
            preco = pastel.getPreco();
            nome = "Pastel de " + pastel.getSabor();
        }
        else if (selecionado instanceof Refrigerante refri) { // verifica se o item adicionado é do model Refrigerante, se for ele pega o Preço e o Sabor e o Tamanho em Ml
            preco = refri.getPreco();
            nome = refri.getSabor() + " " + refri.getTamanhoMl() + "ml";
        }

        itensPedido.add(selecionado); // Adiciona na Lista , essa lista mostra no final o preço , e uma mensagem agradecendo por ter comprado
        tabelaModel.addRow(new Object[]{nome, String.format("R$ %.2f", preco)}); // Adicionar na tabela de compras o nome do item, e o preço com 2 casas dps da virgula
        atualizarTotal(); // Atualiza o total, aumentando o valor gasto
    }



    private double atualizarTotal() {
        double total = 0; // a cada objeto adicionado é zerado o preço e calculado novamente somando cada item, nao sei se é o melhor jeito mas só pensei desse jeito, como a lista salva os valores não se perde a soma
        for (Object item : itensPedido) {   // pra cada objeto adicionado ele roda um if pra saber qual sua classe Model

            if (item instanceof Pastel pastel) {
                total += pastel.getPreco();
            } else if (item instanceof Refrigerante refri) {
                total += refri.getPreco();
            }
        }
        lblTotal.setText(String.format("Total: R$ %.2f", total));   // No canto inferior ele mostra o preço atual
        return total;
    }

    private void finalizarPedido() {
        if (itensPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum item no pedido!");
            return;
        }
        JOptionPane.showMessageDialog(this, String.format(
                "Pedido finalizado!\nTotal: R$ %.2f\nObrigado pela preferência 🥟", atualizarTotal())); // Coloquei o return no atualizarTotal como double, podendo reutilizar aqui
        itensPedido.clear();
        tabelaModel.setRowCount(0);
        lblTotal.setText("Total: R$ 0.00");
        // Limpa toda a tela do programa
    }
}
