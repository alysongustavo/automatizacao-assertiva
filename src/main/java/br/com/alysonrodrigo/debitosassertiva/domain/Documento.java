package br.com.alysonrodrigo.debitosassertiva.domain;

public class Documento {

    private String cpf;

    public Documento() {
    }

    public Documento(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
