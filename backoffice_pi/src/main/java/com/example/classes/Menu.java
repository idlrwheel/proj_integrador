package com.example.classes;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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
                    listarUsuarios(usuario); // Chama o método para listar usuários
                }
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    // Método para listar todos os usuários
    public static void listarUsuarios(Usuario usuario) {
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

        System.out.println("Selecione o ID do usuário para editar/ativar/inativar, 0 para voltar e i para incluir =>: ");
        String opcao = sc.nextLine();

        if ("0".equals(opcao)) {
            System.out.println("Voltando ao menu principal...");
            exibirMenu(usuario);

        } else if ("i".equalsIgnoreCase(opcao)){
            incluirUsuario(usuario);

        } else {
            int idEscolhido = Integer.parseInt(opcao);
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
                    exibirMenu(usuarioEscolhido);
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

    private static void incluirUsuario(Usuario usuarioLogado) {
        Scanner sc = new Scanner(System.in);
        UsuarioDAO usuario = new UsuarioDAO();
        System.out.println("Incluir usuario");
        System.out.println("Nome => ");
        String nome = sc.nextLine();
        System.out.println("CPF => ");
        String cpf = sc.nextLine();
        System.out.println("E-mail => ");
        String email = sc.nextLine();
        System.out.println("Senha => ");
        System.out.println("Grupo (Adm/Estoquista) => ");
        String tipoUser = sc.nextLine();
        System.out.println("Digite senha => ");
        String s1 = sc.nextLine();
        System.out.println("Repetir senha => ");
        String s2 = sc.nextLine();
        System.out.println("Salvar (Y/N) => ");
        String salvar = sc.nextLine();

        if("Y".equalsIgnoreCase(salvar)){
        try {
            String resultado = usuario.cadastrarUsuario(nome, cpf, email, tipoUser, s1, s2);
            System.out.println(resultado);
            listarUsuarios(usuarioLogado);
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        } else if("N".equalsIgnoreCase(salvar)){
            listarUsuarios(usuarioLogado);
        } else{
            System.out.println("Opção invalida!");
        }
    }
}
