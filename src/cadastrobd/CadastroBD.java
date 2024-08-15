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
    // Instanciando ConectorBD e SequenceManager (simulando a inicialização)
    private static Scanner scanner = new Scanner(System.in);
    private static ConectorBD conectorBD = new ConectorBD();
    private static SequenceManager sequenceManager = new SequenceManager();

    // Instanciando DAOs
    private static PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO(conectorBD, sequenceManager);
    private static PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO(conectorBD, sequenceManager);
    
    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("==================================");
            System.out.println("1 - Incluir Pessoa");
            System.out.println("2 - Alterar Pessoa");
            System.out.println("3 - Excluir Pessoa");
            System.out.println("4 - Buscar pelo Id");
            System.out.println("5 - Exibir Todos");
            System.out.println("0 - Finalizar Programa");
            System.out.println("==================================");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    incluirPessoa();
                    break;
                case 2:
                    alterarPessoa();
                    break;
                case 3:
                    excluirPessoa();
                    break;
                case 4:
                    buscarPeloId();
                    break;
                case 5:
                    exibirTodos();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao!= 0);
    }
    
    private static void incluirPessoa() {
        String tipo;        
        int idPessoa;
        String nome; 
        String cpf; 
        String cnpj; 
        String logradouro;
        String cidade;
        String estado;
        String telefone;
        String email;
         
        System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
        
        tipo = scanner.next().toUpperCase();
        
        if (!tipo.equals("J") &&!tipo.equals("F")) {
            System.out.println("Opção inválida!");
            return;
        }
        
        System.out.println("Insira os dados...");
        System.out.println("Nome:");
        nome = scanner.next();
        scanner.nextLine();
        
        System.out.println("Logradouro:");
        logradouro = scanner.nextLine();
        
        System.out.println("Cidade:");
        cidade = scanner.nextLine();
        
        boolean isValidInputEstado = false;
        do {
            System.out.println("Estado:");
            estado = scanner.next();
            scanner.nextLine();
            if (estado.length() > 2) {
                System.out.println("Entrada inválida! Digite uma abreviação de estado com no máximo 2 caracteres.");
            } else {
                isValidInputEstado = true;
            }
        } while (!isValidInputEstado);
        
        System.out.println("Telefone:");
        telefone = scanner.next();
        
        System.out.println("Email:");
        email = scanner.next();
        
        boolean isValidInput = false;
        
        if (tipo.equals("J")) {
            do {
                System.out.println("CNPJ:");
                cnpj = scanner.next();

                if (cnpj.length() > 14) {
                    System.out.println("Entrada inválida! Digite um máximo de 14 caracteres.");
                } else {
                    isValidInput = true;
                }
            } while (!isValidInput);
            
            PessoaJuridica pessoaJuridica = new PessoaJuridica(0, nome, logradouro, cidade, estado, telefone, email, cnpj);
            pessoaJuridicaDAO.incluir(pessoaJuridica);
        }
        
        if (tipo.equals("F")) {
            do {
                System.out.println("CPF:");
                cpf = scanner.next();

                if (cpf.length() > 11) {
                    System.out.println("Entrada inválida! Digite um máximo de 11 caracteres.");
                } else {
                    isValidInput = true;
                }
            } while (!isValidInput);
            
            
            PessoaFisica pessoaFisica = new PessoaFisica(0, nome, logradouro, cidade, estado, telefone, email, cpf);
            pessoaFisicaDAO.incluir(pessoaFisica);
        }
    }
    
    private static void alterarPessoa() {
        String tipo;        
        int id;
        String nome; 
        String cpf; 
        String cnpj; 
        PessoaJuridica pessoaJuridica = null;
        PessoaFisica pessoaFisica = null;
        String logradouro;
        String cidade;
        String estado;
        String telefone;
        String email;
         
        System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
        tipo = scanner.next().toUpperCase();
        
        if (!tipo.equals("J") &&!tipo.equals("F")) {
            System.out.println("Opção inválida!");
            return;
        }
        
        System.out.println("Digite o id da Pessoa:");
        id = scanner.nextInt();
        
        if (tipo.equals("J")) {
            pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
            if (pessoaJuridica == null) {
                System.out.println("Pessoa não encontrada");
                return;
            }
        }
        
        if (tipo.equals("F")) {
            pessoaFisica = pessoaFisicaDAO.getPessoa(id);
            if (pessoaFisica == null) {
                System.out.println("Pessoa não encontrada");
                return;
            }
        }
        
        System.out.println("Insira os dados...");
        
        if (tipo.equals("J")) {
            System.out.printf("Nome (%s):", pessoaJuridica.getNome());
            System.out.println("");
        }
        
        if (tipo.equals("F")) {
            System.out.printf("Nome (%s):", pessoaFisica.getNome());
            System.out.println("");
        }
        
        nome = scanner.next();
        scanner.nextLine();
        
        if (tipo.equals("J")) {
            System.out.printf("Logradouro (%s):", pessoaJuridica.getLogradouro());
            System.out.println("");
        }
        
        if (tipo.equals("F")) {
            System.out.printf("Logradouro (%s):", pessoaFisica.getLogradouro());
            System.out.println("");
        }

        logradouro = scanner.nextLine();
        
        if (tipo.equals("J")) {
            System.out.printf("Cidade (%s):", pessoaJuridica.getCidade());
            System.out.println("");
        }
        
        if (tipo.equals("F")) {
            System.out.printf("Cidade (%s):", pessoaFisica.getCidade());
            System.out.println("");
        }
        
        cidade = scanner.nextLine();
        
        if (tipo.equals("J")) {
            System.out.printf("Estado (%s):", pessoaJuridica.getEstado());
            System.out.println("");
        }
        
        if (tipo.equals("F")) {
            System.out.printf("Estado (%s):", pessoaFisica.getEstado());
            System.out.println("");
        }
        
        boolean isValidInputEstado = false;
        do {
            estado = scanner.next();
            scanner.nextLine();
            if (estado.length() > 2) {
                System.out.println("Entrada inválida! Digite uma abreviação de estado com no máximo 2 caracteres.");
            } else {
                isValidInputEstado = true;
            }
        } while (!isValidInputEstado);
        
        if (tipo.equals("J")) {
            System.out.printf("Telefone (%s):", pessoaJuridica.getTelefone());
        }
        
        if (tipo.equals("F")) {
            System.out.printf("Telefone (%s):", pessoaFisica.getTelefone());
        }
        
        System.out.println("");
        telefone = scanner.next();
        
        if (tipo.equals("J")) {
            System.out.printf("Email (%s):", pessoaJuridica.getEmail());
        }
        
        if (tipo.equals("F")) {
            System.out.printf("Email (%s):", pessoaFisica.getEmail());
        }
        
        System.out.println("");
        email = scanner.next();
        
        boolean isValidInput = false;
        
        if (tipo.equals("J")) {
            do {
                System.out.printf("CNPJ (%s):", pessoaJuridica.getCnpj());
                System.out.println("");
                cnpj = scanner.next();

                if (cnpj.length() > 14) {
                    System.out.println("Entrada inválida! Digite um máximo de 14 caracteres.");
                } else {
                    isValidInput = true;
                }
            } while (!isValidInput);
            
            PessoaJuridica pessoaJuridicaData = new PessoaJuridica(id, nome, logradouro, cidade, estado, telefone, email, cnpj);
            pessoaJuridicaDAO.alterar(pessoaJuridicaData);
        }
        
        if (tipo.equals("F")) {
            do {
                System.out.printf("CPF (%s):", pessoaFisica.getCpf());
                System.out.println("");
                cpf = scanner.next();

                if (cpf.length() > 11) {
                    System.out.println("Entrada inválida! Digite um máximo de 11 caracteres.");
                } else {
                    isValidInput = true;
                }
            } while (!isValidInput);
            
            PessoaFisica pessoaFisicaData = new PessoaFisica(id, nome, logradouro, cidade, estado, telefone, email, cpf);
            pessoaFisicaDAO.alterar(pessoaFisicaData);
        }
    }
    
    private static void buscarPeloId() {
        String tipo;  
        int id;  
        
        System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
        tipo = scanner.next().toUpperCase();
        
        if (!tipo.equals("J") &&!tipo.equals("F")) {
            System.out.println("Opção inválida!");
            return;
        }
                
        System.out.println("Digite o id da Pessoa:");
        id = scanner.nextInt();
        
        if (tipo.equals("J")) {
            PessoaJuridica pessoa = pessoaJuridicaDAO.getPessoa(id);
            if (pessoa == null) {
                System.out.println("Pessoa não encontrada");
            } else {
                System.out.println("Pessoa Juridica:");
                pessoa.exibir();
            }
        }
        
        if (tipo.equals("F")) {
            PessoaFisica pessoa = pessoaFisicaDAO.getPessoa(id);
            if (pessoa == null) {
                System.out.println("Pessoa não encontrada");
            } else {
                System.out.println("Pessoa Fisica:");
                pessoa.exibir();
            }
        }
    } 
    
    private static void excluirPessoa() {
        String tipo;  
        int id;  
        
        System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
        tipo = scanner.next().toUpperCase();
        
        if (!tipo.equals("J") &&!tipo.equals("F")) {
            System.out.println("Opção inválida!");
            return;
        }
                
        System.out.println("Digite o id da Pessoa:");
        id = scanner.nextInt();
        
        if (tipo.equals("J")) {
            PessoaJuridica pessoa = pessoaJuridicaDAO.getPessoa(id);
            if (pessoa == null) {
                System.out.println("Pessoa não encontrada");
            } else {
                pessoaJuridicaDAO.excluir(id);
            }
        }
        
        if (tipo.equals("F")) {
            PessoaFisica pessoa = pessoaFisicaDAO.getPessoa(id);
            if (pessoa == null) {
                System.out.println("Pessoa não encontrada");
            } else {
                pessoaFisicaDAO.excluir(id);
            }
        }
    }
    
    private static void exibirTodos() {
        System.out.println("Dados de Pessoas Fisicas:");
        List<PessoaFisica> todasPessoasFisicas = pessoaFisicaDAO.getPessoas();
        if (!todasPessoasFisicas.isEmpty()) {
            for (PessoaFisica pf : todasPessoasFisicas) {
                pf.exibir();
                System.out.println("");
            }
        } else {
            System.out.println("Nenhuma Pessoa Fisica");
        }
        
        System.out.println("-----------------------------");
        
        System.out.println("Dados de Pessoas Juridicas:");
        
        List<PessoaJuridica> todasPessoasJuridicas = pessoaJuridicaDAO.getPessoas();
        if (!todasPessoasJuridicas.isEmpty()) {
            for (PessoaJuridica pf : todasPessoasJuridicas) {
                pf.exibir();
                System.out.println("");
            }
        } else {
            System.out.println("Nenhuma Pessoa Juridica");
        }
    }
}