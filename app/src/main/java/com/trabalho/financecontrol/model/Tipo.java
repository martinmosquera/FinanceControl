package com.trabalho.financecontrol.model;

import java.io.Serializable;

public class Tipo implements Serializable {

    private long id;
    private String nome;
    private Categoria categoria;

    public Tipo(){

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
