package com.example;
import java.util.Scanner;

import com.example.classes.Menu;
import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;

public class BackofficeMain {
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite seu email: ");
        String email = sc.nextLine();
        System.out.println("Digite sua senha: ");
        String senha = sc.nextLine();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.validarLogin(email, senha);

        
        if(usuario != null){
            System.out.println("Login bem-sucedido! Bem-vindo, " + usuario.getEmail());
            Menu.exibirMenu(usuario);
        } else{
            System.out.println("<< Nao foi possível identificar o usuario, tente novamente>>");
        }
}
}