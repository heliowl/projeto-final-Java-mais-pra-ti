import menu.Menu;
import exception.SistemaException;
import service.AlunoService;
import service.PessoaService;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, ParseException {

        Scanner sc = new Scanner(System.in);
        PessoaService pessoaService = new PessoaService(sc);
        AlunoService alunoService = new AlunoService(sc);

        boolean continuar = true;

        do{
            try {
                System.out.println();
                Menu.menu1();
                int opcao = sc.nextInt();
                if(opcao < 0 || opcao > 2){
                    throw new SistemaException("Opção inválida!");
                }
                if (opcao == 1) {
                    Menu.menuPessoa();
                    int opcao2 = sc.nextInt();
                    if(opcao2 < 0 || opcao2 > 5){
                        throw new SistemaException("Opção inválida!");
                    }
                    switch (opcao2) {
                        case 1:
                            pessoaService.cadastrar();
                            break;

                        case 2:
                            System.out.println("Digite o número da pessoa para alterar o cadastro");
                            pessoaService.mostrarPessoasCadastradas();
                            int opcao3 = sc.nextInt();
                            pessoaService.modificarDadosPorId(opcao3);
                            break;
                        case 3:
                            System.out.println("Escolha o número da pessoa que deseja acessar a ficha");
                            pessoaService.mostrarPessoasCadastradas();
                            opcao3 = sc.nextInt();
                            pessoaService.buscarPorId(opcao3);
                            break;
                        case 4:
                            System.out.println(">>> PESSOAS CADASTRADAS");
                            pessoaService.mostrarPessoasCadastradas();
                            break;
                        case 5:
                            pessoaService.deletar();
                            break;
                        case 0:
                            break;
                    }

                } else if (opcao == 2) {
                    Menu.menuAluno();
                    int opcao2 = sc.nextInt();
                    if(opcao2 < 0 || opcao2 > 7){
                        throw new SistemaException("Opção Inválida!");
                    }

                    switch (opcao2){
                        case 1:
                            alunoService.cadastrar();
                            break;
                        case 2:
                            System.out.println("Digite o número do aluno para alterar o cadastro");
                            alunoService.mostrarAlunosCadastrados();
                            int opcao3 = sc.nextInt();
                            alunoService.modificarDadosPorId(opcao3);
                            break;
                        case 3:
                            System.out.println("Escolha o número do aluno que dejeja acessar a ficha");
                            alunoService.mostrarAlunosCadastrados();
                            opcao3 = sc.nextInt();
                            alunoService.buscarPorId(opcao3);
                            break;
                        case 4:
                            System.out.println(">>> ALUNOS CADASTRADOS");
                            alunoService.mostrarAlunosCadastrados();
                            break;
                        case 5:
                            alunoService.buscarAprovados();
                            break;
                        case 6:
                            alunoService.buscarReprovados();
                            break;
                        case 7:
                            alunoService.deletar();
                            break;
                        case 0:
                            break;


                    }

                } else if (opcao == 0) {
                    continuar = false;
                    break;
                }

            }catch (SistemaException e){
                System.out.println(e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Opção Inválida!");
                sc.nextLine();
            }catch (InputMismatchException e) {
                System.out.println("Digite uma opção válida!");
                sc.nextLine();
            }catch (DateTimeParseException e){
                System.out.println("Digite uma data com formato válido! dd/mm/aaaa");
            }finally {
                Thread.sleep(1000l);
            }
        }while(continuar);

    }
}