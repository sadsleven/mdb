/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastro.model.util;

/**
 *
 * @author abrah
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectorBD {

    // Método para obter uma conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Loja3;trustServerCertificate=true";
        
        String usuario = "sa";
        String senha = "1234";

        return DriverManager.getConnection(url, usuario, senha);
    }

    // Método para obter um PreparedStatement
    public static PreparedStatement getPrepared(String sql) throws Exception {
        try (Connection connection = getConnection()) {
            return connection.prepareStatement(sql);
        }
    }

    // Método para obter um ResultSet
    public static ResultSet getSelect(String sql) throws Exception {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            return statement.executeQuery(sql);
        }
    }

    // Métodos sobrecarregados para fechar Statement, ResultSet e Connection
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}