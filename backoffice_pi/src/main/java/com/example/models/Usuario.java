package com.example.models;


public class Usuario {
    private int id;
    private static int valor = 0;
    private String email;
    private String grupo;
    private String status;
    private String nome;
    private String cpf;
    private String senha;

    public Usuario() {}

    public Usuario(String email, String grupo, String status, String nome, String cpf) {
        this.email = email;
        this.grupo = grupo;
        this.status = status;
        this.nome = nome;
        this.cpf = cpf;
        id = valor++;
    }

    public Usuario(String email, String grupo, String status, String nome, String cpf, String senha) {
        this.email = email;
        this.grupo = grupo;
        this.status = status;
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        id = valor++;
    }

    public void setId(int id) { this.id = id; }
    public static void setValor(int valor) { Usuario.valor = valor; }
    public void setEmail(String email) { this.email = email; }
    public void setGrupo(String grupo) { this.grupo = grupo; }
    public void setStatus(String status) { this.status = status; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setSenha(String senha) { this.senha = senha; }

    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getGrupo() { return grupo; }
    public String getStatus() { return status; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getSenha() { return senha; }
}
