package br.com.alysonrodrigo.debitosassertiva.domain;

public class Usuario {

    private String empresa = "NERIADV-EP";
    private String login = "NERIADV-EP";
    private String senha = "Neri2000";

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
