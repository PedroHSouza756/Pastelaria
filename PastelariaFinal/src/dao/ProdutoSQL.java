package dao;

import model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoSQL implements DAOProduto {
    // Todos são @Override porque "reescrevem" as funções do DAOProduto
    // Metodo semelhante ao ListarTodos do "PastelSQL"
    // É utilizado para o usuário escolher entre os tipos de produtos que pode se escolher
    @Override
    public List<Produto> listarTipos() throws SQLException {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Produto(
                        rs.getInt("ID_produto"),
                        rs.getString("tipo")
                ));
            }
        }
        //System.out.println(lista); // O AWS "libera" mais espaços do que o que foi criado por isso printa mais coisas
        return lista;
    }

}