package util;

import java.text.Normalizer;
import java.util.Scanner;

public class NormalizaFone {

    //Faz a máscara de impressão do telefone na tela
    public static String imprimirFone(String fone){
        fone = Normalizer.normalize(fone, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

        if(fone.length() == 10){
            return "(" + fone.substring(0, 2) + ")" + fone.substring(2, 6) + "-" + fone.substring(6, 10);
        }

        return "(" + fone.substring(0, 2) + ")" + fone.substring(2, 7) + "-" + fone.substring(7, 11);


    }

    //Verifica se o número de dígitos está correto
    public static long validaFone(long fone){
        Scanner sc = new Scanner(System.in);
        String telefone = Long.toString(fone);
        while(telefone.length() < 10 || telefone.length() > 11){
            System.out.println("Digite um telefone válido!");
            System.out.println("Telefone: (Somente os dígitos com o DDD) ");
            fone = sc.nextLong();
            telefone = Long.toString(fone);
        }

        return fone;
    }
}
