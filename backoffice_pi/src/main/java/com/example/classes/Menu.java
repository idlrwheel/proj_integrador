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
            System.out.println("Usuário selecionado: " + usuarioEscolhido.getId() + ", " + usuarioEscolhido.getNome() +
            ", " + usuarioEscolhido.getCpf() + ", " + usuarioEscolhido.getEmail() + ", " + usuarioEscolhido.getStatus() + ", " + usuarioEscolhido.getGrupo());

            // Mostrar opções de ações para o usuário escolhido
            System.out.println("Opções :");
            System.out.println("""
                               1) Alterar usuario
                               2) Alterar senha 
                               3) Habilitar/Desabilitar status 
                               4) Voltar
                               """);
            

            int acao = sc.nextInt();
            switch (acao) {
                case 1:
                    alterarUsuario(usuarioEscolhido);
                    break;
                case 2:
                     alterarSenha(usuarioEscolhido);
                case 3:
                    habilitarDesabilitar(usuarioEscolhido);
                    break;
                case 4:
                    System.out.println("Voltando...");
                    listarUsuarios(usuarioEscolhido);
                default:
                    System.out.println("Opção inválida");
            }
    }
    }
    // Método para alterar um usuário
public static void alterarUsuario(Usuario usuario) {
    Scanner sc = new Scanner(System.in);
    
    System.out.println("Alterando usuário: " + usuario.getNome());
    
    System.out.println("Novo nome (deixe em branco para manter o atual): ");
    String novoNome = sc.nextLine();
    if (!novoNome.trim().isEmpty()) {
        usuario.setNome(novoNome);
    }
    
    System.out.println("Novo CPF (deixe em branco para manter o atual): ");
    String novoCpf = sc.nextLine();
    if (!novoCpf.trim().isEmpty()) {
        usuario.setCpf(novoCpf);
    }
    

    //Comentado para não aparecer opção de alterar e-mail. 
   /* System.out.println("Novo E-mail (deixe em branco para manter o atual): ");
    String novoEmail = sc.nextLine();
    if (!novoEmail.trim().isEmpty()) {
        usuario.setEmail(novoEmail); 
    }*/
    
    System.out.println("Novo Grupo (Adm/Estoquista) (deixe em branco para manter o atual): ");
    String novoGrupo = sc.nextLine();
    if (!novoGrupo.trim().isEmpty()) {
        usuario.setGrupo(novoGrupo);
    }
    
    System.out.println("Novo Status (Ativado/Desativado) (deixe em branco para manter o atual): ");
    String novoStatus = sc.nextLine();
    if (!novoStatus.trim().isEmpty()) {
        usuario.setStatus(novoStatus);
    }

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    usuarioDAO.alterarUsuario(usuario);

    System.out.println("Usuário atualizado com sucesso!");
    listarUsuarios(usuario); // Volta para a lista de usuários
}

public static void alterarSenha(Usuario usuario) {
    Scanner sc = new Scanner(System.in);

    System.out.println("Alterando senha do usuário: " + usuario.getNome());

    System.out.println("Digite a nova senha: ");
    String novaSenha = sc.nextLine();

    System.out.println("Digite a nova senha novamente: ");
    String confirmarSenha = sc.nextLine();

    if (!novaSenha.equals(confirmarSenha)) {
        System.out.println("As senhas não coincidem. Tente novamente.");
        alterarSenha(usuario);
        return;
    }

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    try {
        String senhaCripto = usuarioDAO.encriptarSenha(novaSenha);
        usuario.setSenha(senhaCripto);  // Adicione o método setSenha na classe Usuario

        usuarioDAO.alterarSenha(usuario.getEmail(), senhaCripto);

        System.out.println("Senha atualizada com sucesso!");
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        System.out.println("Erro ao encriptar a senha.");
    }
}

 // Método para habilitar/desabilitar o status de um usuário
public static void habilitarDesabilitar(Usuario usuario) {
    Scanner sc = new Scanner(System.in);

    System.out.println("Status atual do usuário: " + usuario.getStatus());
    System.out.println("Deseja alterar o status? (S/N): ");
    String resposta = sc.nextLine();

    if ("S".equalsIgnoreCase(resposta)) {
        // Alterna o status
        String novoStatus = usuario.getStatus().equalsIgnoreCase("Ativado") ? "Desativado" : "Ativado";
        usuario.setStatus(novoStatus);

        // Atualiza no banco de dados
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.alterarUsuario(usuario);

        System.out.println("Status atualizado para: " + novoStatus);
    } else {
        System.out.println("Operação cancelada.");
    }

    listarUsuarios(usuario); // Retorna para a lista de usuários
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
