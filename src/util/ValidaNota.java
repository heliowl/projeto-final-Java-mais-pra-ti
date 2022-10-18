package util;

import java.util.Scanner;

public class ValidaNota {
    static Scanner sc = new Scanner(System.in);
    public static double validaNota(double nota){
        while (nota < 0 || nota > 10) {
            System.out.println("Digite uma nota entre 0 e 10");
            System.out.println("Média Final: ");
            nota = sc.nextDouble();
        }
        return nota;
    }
}
