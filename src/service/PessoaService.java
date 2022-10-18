package service;

import exception.SistemaException;
import model.Aluno;
import model.Pessoa;
import repository.Repository;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import static util.NormalizaData.*;
import static util.NormalizaFone.imprimirFone;
import static util.NormalizaFone.validaFone;

public class PessoaService {

    private Repository<Integer, Pessoa> repository = new Repository<>();
    private Scanner sc;

    public PessoaService(Scanner sc) throws ParseException {
        this.sc = sc;
        Pessoa pessoa = new Pessoa("João da Silva", "10/09/1985", 51987654321l);
        Pessoa pessoa2 = new Pessoa("Paula Rodrigues", "21/01/1992", 51987654321l);
        repository.salvar(pessoa.getId(), pessoa);
        repository.salvar(pessoa2.getId(), pessoa2);
    }

    public void cadastrar() throws ParseException {

        sc.nextLine();
        System.out.println("Preencha os campos para realizar o cadastro!");
        System.out.println("Nome: ");
        String nome = sc.nextLine();
        System.out.println("Data de nascimento: DD/MM/AAAA");
        String nascimento = sc.nextLine();
        nascimento = corrigeData(nascimento);
        System.out.println("Telefone: (Somente os dígitos com o DDD) ");
        long telefone = sc.nextLong();
        telefone = validaFone(telefone);
        Pessoa pessoa = new Pessoa(nome, nascimento, telefone);

        repository.salvar(pessoa.getId(), pessoa);
        System.out.println("Cadastro realizado com sucesso!");
        this.buscarPorId(pessoa.getId());
    }

    public void mostrarPessoasCadastradas(){
        List<Pessoa> pessoas = repository.buscarTodos();
        pessoas.stream()
                .forEach(p -> System.out.println(p.getId() + " - " +p.getNome() +
                        "  Idade: "+ p.getIdade()+" anos"+" - Atualizado: "+ ldformata(p.getUltimaAtualizacao())));
    }

    public void modificarDadosPorId(int id) {
        Pessoa pessoa = repository.buscaPorId(id);
        System.out.println("Escolha a opção que quer modificar: ");
        System.out.println("1 - Nome: "+pessoa.getNome()+"\n"+"2 - Nascimento: "+ sdformata(pessoa.getDataNascimento())
                +"\n"+"3 - Telefone: "+imprimirFone(pessoa.getTelefone()));
        int opcao = sc.nextInt();
        sc.nextLine();
        boolean cadastro;
        switch (opcao){
            case 1:
                System.out.println("Digite o novo nome: ");
                String nome = sc.nextLine();
                pessoa.setNome(nome);
                cadastro = true;
                ConfirmaAtualizacao(cadastro, id);
                break;
            case 2:
                System.out.println("Digite a nova data de nascimento: ");
                String nascimento = sc.nextLine();
                nascimento = corrigeData(nascimento);
                LocalDate dataNascimento = LocalDate.parse(nascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                pessoa.setDataNascimento(dataNascimento);
                pessoa.setIdade(idade(dataNascimento));
                cadastro = true;
                ConfirmaAtualizacao(cadastro, id);
                break;
            case 3:
                System.out.println("Digite o novo telefone: (Somente os dígitos com DDD) ");
                long fone = sc.nextLong();
                fone = validaFone(fone);
                String telefone = Long.toString(fone);
                pessoa.setTelefone(telefone);
                cadastro = true;
                ConfirmaAtualizacao(cadastro, id);
                break;
            default:
                System.out.println("Opção inválida");
                cadastro = false;
                break;
        }

    }

    public void ConfirmaAtualizacao(boolean atualizado, int id){
        Pessoa pessoa = repository.buscaPorId(id);
        if(atualizado){
            pessoa.setUltimaAtualizacao(LocalDateTime.now());
            repository.salvar(id, pessoa);
            System.out.println("Cadastro Atualizado!");
            this.buscarPorId(id);
        }
    }

    public void buscarPorId(int id) {
        Pessoa pessoa = repository.buscaPorId(id);
        System.out.println("===============================================");
        System.out.println("ID: "+pessoa.getId()+"\n"+ "Nome: "+pessoa.getNome()+ ", "+pessoa.getIdade()+ " anos"+
                " - Nascimento: "+sdformata(pessoa.getDataNascimento())+"\n"+
                "Telefone: " + imprimirFone(pessoa.getTelefone()) +"\n"
                +"Data cadastro: "+ldformata(pessoa.getDataCadastro())+"\n" +
                "Atualizado: "+ldformata(pessoa.getUltimaAtualizacao()));
        System.out.println("===============================================");

    }

    public void deletar() throws SistemaException{
        System.out.println("Escolha o número da pessoa que deseja deletar do sistema");
        this.mostrarPessoasCadastradas();
        int opcao = sc.nextInt();
        Pessoa pessoa = repository.buscaPorId(opcao);
        if(pessoa == null){
            throw new SistemaException("Pessoa não encontrada!");
        }else {
            repository.excluir(opcao);
            System.out.println("Pessoa deletada com sucesso!");
        }
    }
}
