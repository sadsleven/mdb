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
import java.sql.SQLException;
import java.sql.ResultSet;

public class SequenceManager {

    // Método para obter o próximo valor de uma sequência
    public static long getValue(String sequenceName) throws SQLException {
        try (Connection connection = ConectorBD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT nextval('" + sequenceName + "')")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long result = resultSet.getLong(1); 
                    return result;
                } else {
                    throw new SQLException("No result found");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}