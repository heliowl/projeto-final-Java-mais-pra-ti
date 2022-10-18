package util;

public class Contador {
    private static Integer SEQUENCIAL = 1;
    public static Integer proximoID() {

        return SEQUENCIAL ++;

    }
}
