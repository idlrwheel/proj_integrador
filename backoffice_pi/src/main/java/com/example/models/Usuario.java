package com.example.models;

public class Usuario {
    private String email;
    private String grupo;
    private String status;

    public Usuario(String email, String grupo, String status) {
        this.email = email;
        this.grupo = grupo;
        this.status = status;
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

}
