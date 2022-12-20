package com.trabalho.financecontrol.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Operacao implements Serializable {

    private long id;
    private Tipo tipo;
    private Date data;
    private String valor;
    private Categoria categoria;

    public void setId(long id) {
        this.id = id;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public long getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Date getData() {
        return data;
    }

    public String getValor() {
        return valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
