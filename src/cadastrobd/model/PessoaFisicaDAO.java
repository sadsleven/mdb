/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

/**
 *
 * @author abrah
 */
import java.util.*;
import java.util.List;
import cadastro.model.util.ConectorBD;
import cadastro.model.util.SequenceManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;

public class PessoaFisicaDAO {
    private ConectorBD conector;
    private SequenceManager sequenceManager;

    public PessoaFisicaDAO(ConectorBD conector, SequenceManager sequenceManager) {
        this.conector = conector;
        this.sequenceManager = sequenceManager;
    }

    // Método para obter uma pessoa física pelo ID
    public PessoaFisica getPessoa(int id) {
        String sql = "SELECT * FROM Pessoa JOIN PessoaFisica ON Pessoa.idPessoa = PessoaFisica.idPessoa WHERE Pessoa.idPessoa = ?";
        try (
                Connection conn = ConectorBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
            ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String logradouro = rs.getString("logradouro");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String cpf = rs.getString("cpf");
                return new PessoaFisica(id, nome, logradouro, cidade, estado, telefone, email, cpf);
            }
        } catch (SQLException e) { // Catch SQLException specifically first
            e.printStackTrace();
        } catch (Exception e) { // Then catch the more general Exception
            e.printStackTrace();
        }
        return null;
    }
    
    // Método para obter todas as pessoas físicas
    public List<PessoaFisica> getPessoas() {
        List<PessoaFisica> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM Pessoa JOIN PessoaFisica ON Pessoa.idPessoa = PessoaFisica.idPessoa";
        try (
                Connection conn = ConectorBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
            ) {
            while (rs.next()) {
                int id = rs.getInt("idPessoa");
                String nome = rs.getString("nome");
                String logradouro = rs.getString("logradouro");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String cpf = rs.getString("cpf");
                pessoas.add(new PessoaFisica(id, nome, logradouro, cidade, estado, telefone, email, cpf));
            }
        } catch (SQLException e) { // Catch SQLException specifically first
            e.printStackTrace();
        } catch (Exception e) { // Then catch the more general Exception
            e.printStackTrace();
        }
        return pessoas;
    }
    
    // Método para incluir uma nova pessoa física
    public int incluir(PessoaFisica pessoaFisica) {
        String sqlInsertPessoa = "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlInsertPessoaFisica = "INSERT INTO PessoaFisica (idPessoa, cpf) VALUES (?, ?)";
        String sqlMaxIdPessoa = "SELECT MAX(idPessoa) AS MaxId FROM Pessoa";
        
        try {
            // Insert Pessoa
            try (Connection conn = ConectorBD.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sqlInsertPessoa)) {

                pstmt.setString(1, pessoaFisica.getNome());
                pstmt.setString(2, pessoaFisica.getLogradouro());
                pstmt.setString(3, pessoaFisica.getCidade());
                pstmt.setString(4, pessoaFisica.getEstado());
                pstmt.setString(5, pessoaFisica.getTelefone());
                pstmt.setString(6, pessoaFisica.getEmail());
                pstmt.executeUpdate();

        
                PreparedStatement pstmtMaxId = conn.prepareStatement(sqlMaxIdPessoa);
                ResultSet rsMaxId = pstmtMaxId.executeQuery();
                if (rsMaxId.next()) {
                    int lastInsertedId = rsMaxId.getInt("MaxId");
                    // Now use this ID to insert PessoaFisica
                    try (PreparedStatement pstmt2 = conn.prepareStatement(sqlInsertPessoaFisica)) {
                        pstmt2.setInt(1, lastInsertedId);
                        pstmt2.setString(2, pessoaFisica.getCpf());
                        pstmt2.executeUpdate();
                        
                        System.out.println("Pessoa Física incluída com sucesso.");
                        return lastInsertedId;
                    }
                } else {
                    System.out.println("No se encontró el último ID insertado.");
                }
               
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, such as logging the error or informing the user
        }
        return 0;
    }

    // Método para alterar os dados de uma pessoa física
    public void alterar(PessoaFisica pessoaFisica) {
        String sql = "UPDATE Pessoa SET nome=?, logradouro=?, cidade=?, estado=?, telefone=?, email=? WHERE idPessoa=?";
        String sql2 = "UPDATE PessoaFisica SET cpf=? WHERE idPessoa=?";
        
        try {
            // Insert Pessoa
            try (Connection conn = ConectorBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

                pstmt.setString(1, pessoaFisica.getNome());
                pstmt.setString(2, pessoaFisica.getLogradouro());
                pstmt.setString(3, pessoaFisica.getCidade());
                pstmt.setString(4, pessoaFisica.getEstado());
                pstmt.setString(5, pessoaFisica.getTelefone());
                pstmt.setString(6, pessoaFisica.getEmail());
                pstmt.setInt(7, pessoaFisica.getId());
                pstmt.executeUpdate();
                
                pstmt2.setString(1, pessoaFisica.getCpf());
                pstmt2.setInt(2, pessoaFisica.getId());
                pstmt2.executeUpdate();
                
                System.out.println("Dados da pessoa fisica alterados com sucesso.");
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, such as logging the error or informing the user
        }
    }
    
    // Método para excluir uma pessoa física
    public void excluir(int id) {
        String sql = "DELETE FROM PessoaFisica WHERE idPessoa=?";
        String sql2 = "DELETE FROM Pessoa WHERE idPessoa=?";
        
        try (
            Connection conn = ConectorBD.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            pstmt2.setInt(1, id);
            pstmt2.executeUpdate();
            
            System.out.println("Pessoa Física excluída com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}