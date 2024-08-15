/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadastrobd;

/**
 *
 * @author Marvin
 */
import java.util.*;
import cadastro.model.util.ConectorBD;
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;
import cadastro.model.util.SequenceManager;

public class CadastroBD {

    public static void main(String[] args) {
        // Instanciando ConectorBD e SequenceManager (simulando a inicialização)
        ConectorBD conectorBD = new ConectorBD();
        SequenceManager sequenceManager = new SequenceManager();

        // Instanciando DAOs
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO(conectorBD, sequenceManager);
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO(conectorBD, sequenceManager);

        // Operações com Pessoa Física
        int idPessoa = 0;
        String nome = "João";
        String logradouro = "Rua Exemplo";
        String cidade = "São Paulo";
        String estado = "SP";
        String telefone = "(11) 99999-9999";
        String email = "joao@example.com";
        String cpf = "1111666617";

        PessoaFisica pessoaFisica = new PessoaFisica(idPessoa, nome, logradouro, cidade, estado, telefone, email, cpf);
        idPessoa = pessoaFisicaDAO.incluir(pessoaFisica);
        System.out.println("Pessoa Física incluída com sucesso.");
        
        pessoaFisica.setId(idPessoa);
        pessoaFisica.setNome("Alterando nome");
        pessoaFisica.setCpf("E111666617");
        
        pessoaFisicaDAO.alterar(pessoaFisica); // Alterando os dados da pessoa física
        System.out.println("Dados da pessoa física alterados com sucesso.");

        List<PessoaFisica> todasPessoasFisicas = pessoaFisicaDAO.getPessoas();
        for (PessoaFisica pf : todasPessoasFisicas) {
            pf.exibir();
        }
        
        System.out.println(pessoaFisica.getId());
        pessoaFisicaDAO.excluir(pessoaFisica.getId()); // Excluindo a pessoa física
        System.out.println("Pessoa Física excluída com sucesso.");
        
        idPessoa = 21;
        nome = "Empresa XYZ";
        logradouro = "Rua Exemplo";
        cidade = "São Paulo";
        estado = "SP";
        telefone = "(11) 99999-9999";
        email = "XYZ@example.com";
        String cnpj = "000000000010";

        // Operações com Pessoa Jurídica
        PessoaJuridica pessoaJuridica = new PessoaJuridica(idPessoa, nome, logradouro, cidade, estado, telefone, email, cnpj);
        
        idPessoa = pessoaJuridicaDAO.incluir(pessoaJuridica);
        System.out.println("Pessoa Jurídica incluída com sucesso.");
        
        pessoaJuridica.setId(idPessoa);
        pessoaJuridica.setNome("Alterando Empresa");
        pessoaJuridica.setCnpj("E000000000010");

        pessoaJuridicaDAO.alterar(pessoaJuridica); // Alterando os dados da pessoa jurídica
        System.out.println("Dados da pessoa jurídica alterados com sucesso.");

        List<PessoaJuridica> todasPessoasJuridicas = pessoaJuridicaDAO.getPessoas();
        for (PessoaJuridica pj : todasPessoasJuridicas) {
            pj.exibir();
        }

        pessoaJuridicaDAO.excluir(pessoaJuridica.getId()); // Excluindo a pessoa jurídica
        System.out.println(pessoaFisica.getId());
        System.out.println("Pessoa Jurídica excluída com sucesso.");
    }
}