package com.example.models;

import java.util.ArrayList;
import java.util.List;

public class Produto {
    private int codigo;
    private String nome;
    private double avaliacao;
    private String descricaoDetalhada;
    private int qtdEstoque;
    private double valorProduto;
    private String status;
    private String imagemPrincipal;
    private List<String> imagens;

    public Produto(int codigo, String nome, double avaliacao, String descricaoDetalhada, int qtdEstoque, double valorProduto, String status) {
        this.codigo = codigo;
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.descricaoDetalhada = descricaoDetalhada;
        this.qtdEstoque = qtdEstoque;
        this.valorProduto = valorProduto;
        this.status = status;
        this.imagens = new ArrayList<>();
    }

    // Getters e Setters
    public int getCodigo() {
        return codigo;
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

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Métodos para imagens
    public String getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public void adicionarImagem(String imagem) {
        this.imagens.add(imagem);
    }
}

    

