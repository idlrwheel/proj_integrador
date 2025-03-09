package com.example.models;

public class Produto {
    private int codigo;
    private String nome;
    private String descricao;
    private double valorProduto;
    private int qtdEstoque;
    private static int i = 0;

    public Produto(String nome, String descricao, double valorProduto, int qtdEstoque) {
        this.nome = nome;
        this.descricao = descricao;
        this.valorProduto = valorProduto;
        this.qtdEstoque = qtdEstoque;
        codigo = i++;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    
}
