package controller;

import dao.DAOProduto;
import dao.ProdutoSQL;
import model.Produto;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ProdutoController {
    private final DAOProduto dao = new ProdutoSQL();

    public List<Produto> listarTipos() throws SQLException {
        return dao.listarTipos();   //Lista todos os tipos de produtos, usado para escolher o tipo de produto na compra
                                    // 1 - Pastel , 2 - Bebida

}
}
