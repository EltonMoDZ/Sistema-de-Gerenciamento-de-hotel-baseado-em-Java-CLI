package br.com.wydem.faci.entities;

import java.time.LocalDate;

public class Registro {
    private String uuid;
    private String titulo;
    private String descricao;
    private LocalDate data;
    private Double valor;
    private boolean entradaESaida; //Entrada = true, Saida = false

    public Registro(String uuid, String titulo, String descricao, LocalDate data, Double valor, boolean entradaESaida) {
        this.uuid = uuid;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
        this.entradaESaida = entradaESaida;
    }

    public String getUuid() {
        return uuid;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public Double getValor() {
        return valor;
    }

    public boolean isEntradaESaida() {
        return entradaESaida;
    }

    

}
