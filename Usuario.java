package com.example.models;

public class Usuario {
    private String email;
    private String grupo;
    private String status;
    private String nome; // Nome foi adicionado ao modelo para poder ser usado

    public Usuario(String email, String grupo, String status, String nome) {
        this.email = email;
        this.grupo = grupo;
        this.status = status;
        this.nome = nome; // Inicializando o nome
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
}
