package model;

import java.text.ParseException;

public class Aluno extends Pessoa{

    private double notaFinal;
    private Status status;

    public Aluno(String nome, String nascimento, long telefone,double notaFinal) throws ParseException {
        super(nome, nascimento, telefone);
        this.notaFinal = notaFinal;
        if(notaFinal >= 7.0) {
            this.status = Status.APROVADO;
        }else{
            this.status = Status.REPROVADO;
        }
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static enum Status{
        APROVADO,
        REPROVADO
    }
}
