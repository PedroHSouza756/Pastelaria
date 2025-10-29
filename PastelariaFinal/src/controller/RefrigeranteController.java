package controller;

import dao.DAORefrigerante;
import dao.RefrigeranteSQL;
import model.Refrigerante;
import java.sql.SQLException;
import java.util.List;

public class RefrigeranteController {
    private final DAORefrigerante dao = new RefrigeranteSQL();

    public List<Refrigerante> listarRefrigerantes() throws SQLException {
        return dao.listarRefrigerantes();  // Lista todos refrigerantes, usado apenas na hora da lista de compras
    }
}
