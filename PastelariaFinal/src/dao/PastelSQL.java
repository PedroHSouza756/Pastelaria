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
    public void adicionar(Pastel pastel) throws SQLException { // Nao precisa de adicionar id_Pastel pois é autoincrement
        String sql = "INSERT INTO pasteis (ID_produto, sabor, preco) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, 1);
            stmt.setString(2, pastel.getSabor());
            stmt.setDouble(3, pastel.getPreco());

            stmt.executeUpdate();

        }
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
