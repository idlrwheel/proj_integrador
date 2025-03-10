package com.example.models;

public class ImagemProduto {
    private int id;
    private String nomeArquivo;
    private boolean principal;

    public ImagemProduto(int id, String nomeArquivo, boolean principal) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.principal = principal;
    }

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

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }
}