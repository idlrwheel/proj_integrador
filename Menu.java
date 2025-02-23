package com.example.classes;

import java.util.List;
import java.util.Scanner;
import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;

public class Menu {
    public static void exibirMenu(Usuario usuario){
        Scanner sc = new Scanner(System.in);
        System.out.println("1) Listar produtos");
        
        if ("adm".equals(usuario.getGrupo())) {
            System.out.println("2) Listar usuários");
        }
        
        System.out.println("Entre com a opção (1 ou 2): ");
        int opc = sc.nextInt();

        switch(opc){
            case 1:
                System.out.println("Listar Produtos: ");
                break;
            case 2:
                if ("adm".equals(usuario.getGrupo())) {
                    listarUsuarios(); // Chama o método para listar usuários
                }
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    // Método para listar todos os usuários
    public static void listarUsuarios() {
        List<Usuario> usuarios = UsuarioDAO.listarUsuarios(); // Chama o DAO para listar usuários
        Scanner sc = new Scanner(System.in);

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
        } else {
            // Exibir a lista de usuários
            System.out.println("ID | Nome | Email | Status | Grupo");
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario u = usuarios.get(i);
                System.out.println((i + 1) + " | " + u.getNome() + " | " + u.getEmail() + " | " + u.getStatus() + " | " + u.getGrupo());
            }
        }

        System.out.println("Selecione o ID do usuário para ações (ou 0 para voltar ao menu): ");
        int idEscolhido = sc.nextInt();

        if (idEscolhido == 0) {
            System.out.println("Voltando ao menu principal...");
        } else {
            // Realizar a ação com o usuário escolhido
            Usuario usuarioEscolhido = usuarios.get(idEscolhido - 1); // Pega o usuário baseado no ID
            System.out.println("Usuário selecionado: " + usuarioEscolhido.getNome());

            // Mostrar opções de ações para o usuário escolhido
            System.out.println("O que você deseja fazer?");
            System.out.println("1) Alterar usuário");
            System.out.println("2) Habilitar/Desabilitar status");
            System.out.println("3) Voltar");

            int acao = sc.nextInt();
            switch (acao) {
                case 1:
                    alterarUsuario(usuarioEscolhido);
                    break;
                case 2:
                    habilitarDesabilitar(usuarioEscolhido);
                    break;
                case 3:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    // Método para alterar um usuário
    public static void alterarUsuario(Usuario usuario) {
        System.out.println("Alterando usuário: " + usuario.getNome());
        // Adicione a lógica para alterar os dados do usuário
    }

    // Método para habilitar/desabilitar o status de um usuário
    public static void habilitarDesabilitar(Usuario usuario) {
        System.out.println("Status atual do usuário: " + usuario.getStatus());
        // Lógica para habilitar/desabilitar
    }
}
