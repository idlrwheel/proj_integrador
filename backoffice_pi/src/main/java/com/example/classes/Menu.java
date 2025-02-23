package com.example.classes;

import java.util.Scanner;

import com.example.models.Usuario;

public class Menu {
    public static void exibirMenu(Usuario usuario){
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Listar produtos");
        if("adm".equals(usuario.getGrupo())){
            System.out.println("2. Listar usuários");
        }
        System.out.println("Escolha uma opção :");
        int opc = sc.nextInt();

            switch(opc){
            case 1:
            System.out.println("Listar Produtos: ");
            break;
            case 2 :
            if("adm".equals(usuario.getGrupo())){
                System.out.println("Listar Usuarios: ");
               // UsuarioDAO.listarUsuarios();
            }
            break;
            default:
            System.out.println("Opcao invalida");
      } sc.close();
    }
       
}
