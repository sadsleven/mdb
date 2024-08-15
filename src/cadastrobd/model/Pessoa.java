/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model;

/**
 *
 * @author abrah
 */
public class Pessoa {
    protected int idPessoa;
    protected String nome;
    protected String logradouro;
    protected String cidade;
    protected String estado;
    protected String telefone;
    protected String email;

    // Construtor padrão
    public Pessoa() {}

    // Construtor completo
    public Pessoa(int idPessoa, String nome, String logradouro, String cidade, String estado, String telefone, String email) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone = telefone;
        this.email = email;
    }
    
    public void setId(int id) {
        this.idPessoa = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Método para exibir dados
    public void exibir() {
        System.out.println("ID: " + idPessoa);
        System.out.println("Nome: " + nome);
        System.out.println("Logradouro: " + logradouro);
        System.out.println("Cidade: " + cidade);
        System.out.println("Estado: " + estado);
        System.out.println("Telefone: " + telefone);
        System.out.println("Email: " + email);
    }
    
    public int getId() {
        return idPessoa;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getLogradouro() {
        return logradouro;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public String getEmail() {
        return email;
    }
    
}