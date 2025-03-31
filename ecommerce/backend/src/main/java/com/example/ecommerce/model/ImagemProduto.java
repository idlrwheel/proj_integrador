package com.example.ecommerce.model;

import jakarta.persistence.*;

@Entity
public class ImagemProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomeArquivo;
    private String diretorioOrigem;
    private boolean principal;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getDiretorioOrigem() {
        return diretorioOrigem;
    }

    public void setDiretorioOrigem(String diretorioOrigem) {
        this.diretorioOrigem = diretorioOrigem;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }


}