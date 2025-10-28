package dao;

import model.Refrigerante;

import java.sql.SQLException;
import java.util.List;

public interface DAORefrigerante {
    // todos os metodos de RefrigeranteSQL de uma forma mais simples de compreender (Interface)
    public List<Refrigerante> listarRefrigerantes() throws SQLException;

}
