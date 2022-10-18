package service;

import exception.SistemaException;
import model.Aluno;
import repository.Repository;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import static model.Aluno.Status.APROVADO;
import static model.Aluno.Status.REPROVADO;
import static util.NormalizaData.*;
import static util.NormalizaFone.imprimirFone;
import static util.NormalizaFone.validaFone;
import static util.ValidaNota.validaNota;

public class AlunoService {

    private Repository<Integer, Aluno> repository = new Repository<>();
    private Scanner sc;

    public AlunoService(Scanner sc) throws ParseException {
        this.sc = sc;
        Aluno aluno = new Aluno("Gumercindo Gumarães", "20/04/2002", 51987654321l,6.7);
        Aluno aluno2 = new Aluno("Cleuza Maria", "03/06/1984", 51912345678l,9.4);
        Aluno aluno3 = new Aluno("Gilberto Silveira", "27/11/1998", 5132323232l,7.2);
        Aluno aluno4 = new Aluno("Camila Dorneles", "14/09/1995", 5130304040l,5.8);
        repository.salvar(aluno.getId(), aluno);
        repository.salvar(aluno2.getId(), aluno2);
        repository.salvar(aluno3.getId(), aluno3);
        repository.salvar(aluno4.getId(), aluno4);
    }

    public void cadastrar() throws ParseException {

        sc.nextLine();
        System.out.println("Preencha os campos para realizar o cadastro!");
        System.out.println("Nome: ");
        String nome = sc.nextLine();
        System.out.println("Data de nascimento: DD/MM/AAAA");
        String nascimento = sc.nextLine();
        nascimento = corrigeData(nascimento);
        System.out.println("Telefone: ");
        long telefone = sc.nextLong();
        telefone = validaFone(telefone);
        System.out.println("Média Final (ex. 8,5): ");
        double nota = sc.nextDouble();
        nota = validaNota(nota);
        Aluno aluno = new Aluno(nome, nascimento, telefone, nota);

        repository.salvar(aluno.getId(), aluno);
        System.out.println("Cadastro realizado com sucesso!");
        this.buscarPorId(aluno.getId());
    }

    public void mostrarAlunosCadastrados(){
        List<Aluno> alunos = repository.buscarTodos();
        alunos.stream()
                .forEach(a -> System.out.println(a.getId() + " - " +a.getNome() +
                        "  Idade: "+ a.getIdade()+" anos"
                        +" - "+"Nota Final: "+a.getNotaFinal() +" - "+a.getStatus()+" - Atualizado: "+ ldformata(a.getUltimaAtualizacao())));
    }

    public void modificarDadosPorId(int id) {
        Aluno aluno = repository.buscaPorId(id);
        System.out.println("Escolha a opção que quer modificar: ");
        System.out.println("1 - Nome: "+aluno.getNome()+"\n"+"2 - Nascimento: "+ sdformata(aluno.getDataNascimento())
                +"\n"+ "3 - Telefone: "+ imprimirFone(aluno.getTelefone())+"\n"+ "4 - Nota Final: "+aluno.getNotaFinal());
        int opcao = sc.nextInt();
        sc.nextLine();
        boolean cadastro;
        switch (opcao){
            case 1:
                System.out.println("Digite o novo nome: ");
                String nome = sc.nextLine();
                aluno.setNome(nome);
                cadastro = true;
                ConfirmaAtualizacao(cadastro, id);
                break;
            case 2:
                System.out.println("Digite a nova data de nascimento: ");
                String nascimento = sc.nextLine();
                nascimento = corrigeData(nascimento);
                LocalDate dataNascimento = LocalDate.parse(nascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                aluno.setDataNascimento(dataNascimento);
                aluno.setIdade(idade(dataNascimento));
                cadastro = true;
                ConfirmaAtualizacao(cadastro, id);
                break;
            case 3:
                System.out.println("Digite o novo telefone: (Somente os dígitos com DDD) ");
                long fone = sc.nextLong();
                fone = validaFone(fone);
                String telefone = Long.toString(fone);
                aluno.setTelefone(telefone);
                cadastro = true;
                ConfirmaAtualizacao(cadastro, id);
                break;

            case 4:
                System.out.println("Digite o nova nota final (ex. 8,5): ");
                double nota = sc.nextDouble();
                nota = validaNota(nota);
                aluno.setNotaFinal(nota);
                if(nota >= 7){
                    aluno.setStatus(APROVADO);
                }else{
                    aluno.setStatus(REPROVADO);
                }
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
        Aluno aluno = repository.buscaPorId(id);
        if(atualizado){
            aluno.setUltimaAtualizacao(LocalDateTime.now());
            repository.salvar(id, aluno);
            System.out.println("Cadastro Atualizado!");
            this.buscarPorId(id);
        }
    }

    public void buscarPorId(int id){
        Aluno aluno = repository.buscaPorId(id);
        System.out.println("===============================================");
        System.out.println("ID: "+aluno.getId()+ "\n"+"Nome: "+aluno.getNome()+", "+aluno.getIdade()+ " anos"+
                " - Nascimento: "+sdformata(aluno.getDataNascimento())+
                "\n"+"Nota Final: "+aluno.getNotaFinal()+ " - "+aluno.getStatus()+
                "\n"+"Telefone: "+imprimirFone(aluno.getTelefone())+"\n"+
                "Data cadastro: "+ldformata(aluno.getDataCadastro())+"\n" +
                "Atualizado: "+ldformata(aluno.getUltimaAtualizacao()));
        System.out.println("===============================================");

    }

    public void deletar() throws SistemaException{
        System.out.println("Escolha o número do aluno que deseja deletar do sistema");
        this.mostrarAlunosCadastrados();
        int opcao = sc.nextInt();
        Aluno aluno = repository.buscaPorId(opcao);
        if(aluno == null){
            throw new SistemaException("Aluno não encontrado!");
        }else {
            repository.excluir(opcao);
            System.out.println("Aluno deletado com sucesso!");
        }
    }

    public void buscarAprovados(){
        List<Aluno> alunos = repository.buscarTodos();
        List<Aluno> aprovados = alunos.stream().filter(a -> a.getStatus().equals(APROVADO)).collect(Collectors.toList());

        if(aprovados.size() <= 0){
            System.out.println("Não há alunos aprovados!");
        }else{
            System.out.println(">>> APROVADOS");
            aprovados.forEach(a -> System.out.println(a.getId() + " - " +a.getNome() +
                    " - "+"Nota Final: "+a.getNotaFinal()+ " - "+a.getStatus()));
        }
    }

    public void buscarReprovados(){
        List<Aluno> alunos = repository.buscarTodos();
        List<Aluno> reprovados = alunos.stream().filter(a -> a.getStatus().equals(REPROVADO)).collect(Collectors.toList());

        if(reprovados.size() <= 0){
            System.out.println("Não há alunos reprovados!");
        }else{
            System.out.println(">>> REPROVADOS");
            reprovados.forEach(a -> System.out.println(a.getId() + " - " +a.getNome() +
                    " - "+"Nota Final: "+a.getNotaFinal()+ " - "+a.getStatus()));
        }
    }


}
