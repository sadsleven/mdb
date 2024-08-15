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

public class PessoaJuridicaDAO {
    private ConectorBD conector;
    private SequenceManager sequenceManager;

    public PessoaJuridicaDAO(ConectorBD conector, SequenceManager sequenceManager) {
        this.conector = conector;
        this.sequenceManager = sequenceManager;
    }

    // Método para obter uma pessoa física pelo ID
    public PessoaJuridica getPessoa(int id) {
        String sql = "SELECT * FROM Pessoa JOIN PessoaJuridica ON Pessoa.idPessoa = PessoaJuridica.idPessoa WHERE Pessoa.id = ?";
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
                String cnpj = rs.getString("cnpj");
                return new PessoaJuridica(id, nome, logradouro, cidade, estado, telefone, email, cnpj);
            }
            while (rs.next()) {
                
            }
        } catch (SQLException e) { // Catch SQLException specifically first
            e.printStackTrace();
        } catch (Exception e) { // Then catch the more general Exception
            e.printStackTrace();
        }
        return null;
    }
    
    // Método para obter todas as pessoas físicas
    public List<PessoaJuridica> getPessoas() {
        List<PessoaJuridica> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM Pessoa JOIN PessoaJuridica ON Pessoa.idPessoa = PessoaJuridica.idPessoa";
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
                String cnpj = rs.getString("cnpj");
                pessoas.add(new PessoaJuridica(id, nome, logradouro, cidade, estado, telefone, email, cnpj));
            }
        } catch (SQLException e) { // Catch SQLException specifically first
            e.printStackTrace();
        } catch (Exception e) { // Then catch the more general Exception
            e.printStackTrace();
        }
        return pessoas;
    }
    
    // Método para incluir uma nova pessoa física
    public int incluir(PessoaJuridica pessoaJuridica) {
        String sqlInsertPessoa = "INSERT INTO Pessoa (nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlInsertPessoaJuridica = "INSERT INTO PessoaJuridica (idPessoa, cnpj) VALUES (?, ?)";
        String sqlMaxIdPessoa = "SELECT MAX(idPessoa) AS MaxId FROM Pessoa";
        
        try {
            // Insert Pessoa
            try (Connection conn = ConectorBD.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sqlInsertPessoa)) {

                pstmt.setString(1, pessoaJuridica.getNome());
                pstmt.setString(2, pessoaJuridica.getLogradouro());
                pstmt.setString(3, pessoaJuridica.getCidade());
                pstmt.setString(4, pessoaJuridica.getEstado());
                pstmt.setString(5, pessoaJuridica.getTelefone());
                pstmt.setString(6, pessoaJuridica.getEmail());
                pstmt.executeUpdate();

        
                PreparedStatement pstmtMaxId = conn.prepareStatement(sqlMaxIdPessoa);
                ResultSet rsMaxId = pstmtMaxId.executeQuery();
                if (rsMaxId.next()) {
                    int lastInsertedId = rsMaxId.getInt("MaxId");
                    // Now use this ID to insert PessoaJuridica
                    try (PreparedStatement pstmt2 = conn.prepareStatement(sqlInsertPessoaJuridica)) {
                        pstmt2.setInt(1, lastInsertedId);
                        pstmt2.setString(2, pessoaJuridica.getCnpj());
                        pstmt2.executeUpdate();
                        
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
    public void alterar(PessoaJuridica pessoaJuridica) {
        String sql = "UPDATE Pessoa SET nome=?, logradouro=?, cidade=?, estado=?, telefone=?, email=? WHERE idPessoa=?";
        String sql2 = "UPDATE PessoaJuridica SET cnpj=? WHERE idPessoa=?";
        
        try {
            // Insert Pessoa
            try (Connection conn = ConectorBD.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

                pstmt.setString(1, pessoaJuridica.getNome());
                pstmt.setString(2, pessoaJuridica.getLogradouro());
                pstmt.setString(3, pessoaJuridica.getCidade());
                pstmt.setString(4, pessoaJuridica.getEstado());
                pstmt.setString(5, pessoaJuridica.getTelefone());
                pstmt.setString(6, pessoaJuridica.getEmail());
                pstmt.setInt(7, pessoaJuridica.getId());
                pstmt.executeUpdate();
                
                pstmt2.setString(1, pessoaJuridica.getCnpj());
                pstmt2.setInt(2, pessoaJuridica.getId());
                pstmt2.executeUpdate();
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, such as logging the error or informing the user
        }
    }
    
    // Método para excluir uma pessoa física
    public void excluir(int id) {
        String sql = "DELETE FROM PessoaJuridica WHERE idPessoa=?";
        String sql2 = "DELETE FROM Pessoa WHERE idPessoa=?";
        
        try (
            Connection conn = ConectorBD.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            pstmt2.setInt(1, id);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}