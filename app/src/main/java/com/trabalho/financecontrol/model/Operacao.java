package com.trabalho.financecontrol.model;

import java.io.Serializable;
import java.util.Date;

public class Operacao implements Serializable {

    private long id;
    private Tipo tipo;
    private Date data;
    private String valor;

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
}
