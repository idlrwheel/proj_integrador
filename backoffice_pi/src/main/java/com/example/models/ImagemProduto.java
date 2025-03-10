package com.example.models;

public class ImagemProduto {
    private int id;
    private int produtoId;
    private String nomeArquivo;
    private String diretorioOrigem;
    private boolean principal;

    public ImagemProduto(int id, int produtoId, String nomeArquivo, String diretorioOrigem, boolean principal) {
        this.id = id;
        this.produtoId = produtoId;
        this.nomeArquivo = nomeArquivo;
        this.diretorioOrigem = diretorioOrigem;
        this.principal = principal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
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
}
