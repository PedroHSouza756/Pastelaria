package dao;
import java.sql.SQLException;
import java.util.List;
import model.Pastel;
public interface DAOPastel {
    // todos os metodos de PastelSQL de uma forma mais simples de compreender (Interface)

    public abstract List<Pastel> listarTodos() throws SQLException;
    public void adicionar(Pastel pastel) throws SQLException;
    public void removerPorSabor(String sabor) throws SQLException;
    public List<Pastel> buscarPasteisPorNome(String nome) throws SQLException;
}
