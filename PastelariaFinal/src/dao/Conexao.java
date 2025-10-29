package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://18.230.65.88:4582/pastelaria"; // BD no AWS , precisa do ip e porta
    private static final String USER = "pastelaria";
    private static final String PASS = "remote4P$";

    public static Connection getConnection() throws SQLException { // Metodo para "inicializar" o contato com o BD
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
