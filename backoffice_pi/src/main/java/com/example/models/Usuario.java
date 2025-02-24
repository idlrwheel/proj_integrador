package com.example.models;

public class Usuario {
    private int id;
    private static int valor = 0;
    private String email;
    private String grupo;
    private String status;
    private String nome; // Nome foi adicionado ao modelo para poder ser usado
    private String cpf;

    public Usuario(){
    }

    public Usuario(String email, String grupo, String status, String nome, String cpf) {
        this.email = email;
        this.grupo = grupo;
        this.status = status;
        this.nome = nome; // Inicializando o nome
        this.cpf = cpf;
        id = valor++;
    }
    
    public int getId(){
        return id;
    }
    public String getEmail() {
        return email;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getStatus() {
        return status;
    }

    public String getNome() {
        return nome; // Método getter para o nome
    }
    public String getCPF(){
        return cpf;
    }
}
