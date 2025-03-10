package com.example.classes;

import com.example.models.Usuario;

public class SessionManager {
    private static Usuario usuarioLogado;

    // Método para obter o usuário logado
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    // Método para setar o usuário logado
    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }
}
