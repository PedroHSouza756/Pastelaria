package dao;
import model.Refrigerante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RefrigeranteSQL implements DAORefrigerante {
    // Todos são @Override porque "reescrevem" as funções do DAORefrigerante
    // Semelhante aos anteriores
    @Override
    public List<Refrigerante> listarRefrigerantes() throws SQLException {
        List<Refrigerante> lista = new ArrayList<>();
        String sql = "SELECT * FROM refrigerantes";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Refrigerante(
                        rs.getInt("ID_refrigerante"),
                        rs.getInt("ID_produto"),
                        rs.getString("sabor"),
                        rs.getInt("tamanho_ml"),
                        rs.getDouble("preco")
                ));
            }
        }
        return lista;
    }
}
