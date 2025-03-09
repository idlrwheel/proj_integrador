package com.example.models;

public class Produto {
    private int codigo;
    private String nome;
    private double avaliacao;
    private String descricaoDetalhada;
    private int qtdEstoque;
    private double valorProduto;
    private String status;

    public Produto(int codigo, String nome, double avaliacao, String descricaoDetalhada, int qtdEstoque, double valorProduto, String status) {
        this.codigo = codigo;
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.descricaoDetalhada = descricaoDetalhada;
        this.qtdEstoque = qtdEstoque;
        this.valorProduto = valorProduto;
        this.status = status;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricaoDetalhada;
    }

    public void setDescricao(String descricao) {
        this.descricaoDetalhada = descricao;
    }

    public double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }

    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }
    
}

    
}
