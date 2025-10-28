package controller;

import dao.Conexao; //Conexao com DB

import dao.DAOPastel;
import dao.PastelSQL;

import model.Pastel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PastelController {

    public List<Pastel> listarPasteis() throws SQLException { // Nao precisa de receber nada para iniciar, pois o return é uma tabela no BD
        try (Connection conn = Conexao.getConnection()) {
            DAOPastel dao = new PastelSQL(conn); // Faz a instancia do PastelSQL
            return dao.listarTodos();            // Chama o metodo listarTodos() do PastelSQL
        }
    }

    public void adicionarPastel(String sabor, double preco) throws SQLException { // Precisa receber o sabor e preço, id é autoincrement
        try (Connection conn = Conexao.getConnection()) {
            DAOPastel dao = new PastelSQL(conn); // Faz a instancia do PastelSQL
            dao.adicionar(new Pastel(sabor, preco)); // Chama o metodo adicionar() do PastelSQL
        }
    }

    public void removerPastel(String sabor) throws SQLException { // A remoção do pastel é apenas pelo sabor
        try (Connection conn = Conexao.getConnection()) {
            DAOPastel dao = new PastelSQL(conn);// Faz a instancia do PastelSQL
            dao.removerPorSabor(sabor);// Chama o metodo removerPorSabor() do PastelSQL
        }
    }
    public List<Pastel> buscarPasteisPorNome(String nome) throws SQLException { // Busca do pastel pelo nome, usado na tela ListarPasteis
        try (Connection conn = Conexao.getConnection()) {
           DAOPastel dao = new PastelSQL(conn);// Faz a instancia do PastelSQL
            return dao.buscarPasteisPorNome(nome);
        }
    }
}
