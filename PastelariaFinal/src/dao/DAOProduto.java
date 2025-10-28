package dao;
import java.sql.SQLException;
import java.util.List;
import model.Produto;
public interface DAOProduto {
    // todos os metodos de ProdutoSQL de uma forma mais simples de compreender (Interface)
    public List<Produto> listarTipos() throws SQLException;
}
