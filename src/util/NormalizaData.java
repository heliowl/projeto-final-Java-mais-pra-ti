package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class NormalizaData {

    //Formato da data do cadastro e da atualização
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    //Imprime data do cadastro e da atualização no formato acima
    public static String ldformata(LocalDateTime data){
        return data.format(formatter);
    }

    //Formato da data de nascimento
    static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //Imprime data de nascimento no formato acima
    public static String sdformata(LocalDate data){

        return data.format(formato);
    }

    //Testa se a data de nascimento é válida
    public static boolean validaData(String data) {

        try {
            DateTimeFormatter formatter = formato;
            LocalDate nascimento = LocalDate.parse(data, formatter);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Digite uma data válida! dd/mm/aaaa");

            return false;
        }
    }

    //Tratamento de erro na data de nascimento
    public static String corrigeData(String nascimento){
        Scanner sc = new Scanner(System.in);

        boolean verifica = validaData(nascimento);
        if(verifica){
            verifica = veruficaDataFutura(nascimento);
        }

        while(!verifica){
            System.out.println("Data de nascimento: DD/MM/AAAA");
            nascimento = sc.nextLine();
            verifica = NormalizaData.validaData(nascimento);
            if(verifica){
                verifica = veruficaDataFutura(nascimento);
            }
        }
        return nascimento;
    }

    //Verifica se a data de nascimento não é uma data futura
    private static boolean veruficaDataFutura(String nascimento) {
        LocalDate hoje = LocalDate.now();
        LocalDate dataNasc = LocalDate.parse(nascimento, formato);
        boolean verifica = dataNasc.isBefore(hoje) || dataNasc.equals(hoje);
        if(!verifica){
            System.out.println("Data de nascimento não pode ser uma data futura!");
        }
        return verifica;
    }




    //retorna idade

    public static int idade(final LocalDate aniversario) {
        final LocalDate dataAtual = LocalDate.now();
        final Period periodo = Period.between(aniversario, dataAtual);
        return periodo.getYears();
    }


}

