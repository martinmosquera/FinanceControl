package com.trabalho.financecontrol.model;

public enum Categoria {
    DEBITO("Debito"), CREDITO("Credito");
    private String nome;

    Categoria(String n) {
        nome = n;
    }

    public String getNome() {
        return nome;
    }

}

