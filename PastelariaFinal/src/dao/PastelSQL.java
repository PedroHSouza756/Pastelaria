package dao;
import model.Pastel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PastelSQL implements DAOPastel {
    private final Connection conn;
    public PastelSQL(Connection conn) {
        this.conn = conn; // Inicializa a conexão
    }
    // Todos são @Override porque "reescrevem" as funções do DAOPastel

    @Override
    public List<Pastel> listarTodos() throws SQLException {
        List<Pastel> lista = new ArrayList<>(); // Cria uma lista de Objetos "Pastel"
        String sql = "SELECT ID_pasteis, sabor, preco FROM pasteis ORDER BY sabor"; // Cria uma String para buscar no BD
        // Metodos para usar o SQL
        try (PreparedStatement stmt = conn.prepareStatement(sql); //Executa a String no BD
             ResultSet rs = stmt.executeQuery()) {  // Cada linha será um Objeto "Pastel
            while (rs.next()) {
                lista.add(new Pastel( // Adiciona cada pastel criado dentro da lista de "Pastel"
                        rs.getInt("ID_pasteis"),
                        rs.getString("sabor"),
                        rs.getDouble("preco")
                ));
            }
        }
        return lista; // Retorna a Lista
    }

    @Override
    public void adicionar(Pastel pastel) throws SQLException { // Adiciona um objeto Pastel ao BD
        String sqlProduto = "INSERT INTO produtos (tipo) VALUES ('pastel')"; // SQL para inserir um registro na tabela `produtos` com o campo `tipo` = 'pastel'
        PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS); // pede pro JDBC retorne as chaves geradas, porque o campo é AutoIncremente e só o BD sabe qual o prox p/ adicionar
        stmtProduto.executeUpdate(); // Executa o que esta acima

        ResultSet rs = stmtProduto.getGeneratedKeys(); // Recupera o ResultSet contendo as chaves geradas pelo INSERT anterior (O valor que o AutoIncrement gerou)
        int idProduto = 0; // Inicializa variável que armazenará o id gerado (valor padrão 0 caso não haja chave retornada)
        if (rs.next()) idProduto = rs.getInt(1); // Se houver chave gerada, pega o primeiro valor (coluna 1) do ResultSet — AutoIncrement

        String sqlPasteis = "INSERT INTO pasteis (ID_produto, sabor, preco) VALUES (?, ?, ?)"; // SQL para inserir dados específicos do pastel na tabela `pasteis`, referenciando o produto criado, pegamos o valor o AutoIncrement para adicionar aqui
        PreparedStatement stmtPasteis = conn.prepareStatement(sqlPasteis); // Prepara a statement para o INSERT em `pasteis`

        // Set de todos os "?"
        stmtPasteis.setInt(1, idProduto);
        stmtPasteis.setString(2, pastel.getSabor());
        stmtPasteis.setDouble(3, pastel.getPreco());

        stmtPasteis.executeUpdate(); // Executa o INSERT na tabela `pasteis`

        stmtProduto.close(); // Fecha o PreparedStatement do produto
        stmtPasteis.close(); // Fecha o PreparedStatement dos pasteis
    }

    @Override
    public void removerPorSabor(String sabor) throws SQLException {
        String sql = "DELETE FROM pasteis WHERE sabor = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, sabor); // O ? é substituido pelo sabor, e apagará todos os pasteis com esse sabor especifico
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public List<Pastel> buscarPasteisPorNome(String nome) throws SQLException {
        List<Pastel> lista = new ArrayList<>();
        String sql = "SELECT ID_pasteis, sabor, preco FROM pasteis WHERE sabor LIKE ? ORDER BY sabor"; // ordem alfabética
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%"); // Uso do Like para uma busca parcial, por exemplo ? = q, todos os pasteis que tem "q" aparecerão
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // cria uma lista para poder dar "print" para o usuário
                    lista.add(new Pastel(
                            rs.getInt("ID_pasteis"),
                            rs.getString("sabor"),
                            rs.getDouble("preco")
                    ));
                }
            }
        }
        return lista;
    }
}
