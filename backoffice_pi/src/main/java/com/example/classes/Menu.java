
package com.example.classes;

import com.example.dao.PedidoDAO;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

import com.example.dao.UsuarioDAO;
import com.example.models.Pedido;
import com.example.models.Usuario;

public class Menu {
    public static void exibirMenu() {
        Usuario usuario = SessionManager.getUsuarioLogado();
        Scanner sc = new Scanner(System.in);

        int opc = -1;
        while (opc != 0) {
            System.out.println("==== MENU PRINCIPAL ====");
            System.out.println("1) Listar produtos");

            if (usuario.getGrupo().equals("adm")) {
                System.out.println("2) Listar usuários");
            }
            
             if (usuario.getGrupo().equals("estoquista")) {
                System.out.println("2) Listar pedidos");
                System.out.println("3) Alterar status do pedido");
            }


            System.out.println("0) Sair");
            System.out.print("Entre com a opção: ");
            opc = sc.nextInt();
            sc.nextLine();

             if (opc == 1) {
                MenuProduto.listarProdutos();
            } else if (opc == 2 && usuario.getGrupo().equals("adm")) {
                listarUsuarios(usuario);
            } else if (opc == 2 && usuario.getGrupo().equals("estoquista")) {
                listarPedidos();
            } else if (opc == 3 && usuario.getGrupo().equals("estoquista")) {
                alterarStatusPedido();
            } else if (opc == 0) {
                System.out.println("Saindo do sistema...");
            }
        }
    }


    public static void listarUsuarios(Usuario usuario) {
        List<Usuario> usuarios = UsuarioDAO.listarUsuarios();
        Scanner sc = new Scanner(System.in);

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
            return;
        }

        System.out.println("ID | Nome | Email | Status | Grupo");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            System.out.println((i + 1) + " | " + u.getNome() + " | " + u.getEmail() + " | " + u.getStatus() + " | " + u.getGrupo());
        }

        System.out.print("Selecione o ID do usuário para editar/ativar/inativar, 0 para voltar e i para incluir: ");
        String opcao = sc.nextLine();

        if (opcao.equals("0")) {
            return;
        }

        if (opcao.equalsIgnoreCase("i")) {
            incluirUsuario(usuario);
            return;
        }

        int idEscolhido = Integer.parseInt(opcao);
        Usuario usuarioEscolhido = usuarios.get(idEscolhido - 1);

        System.out.println("Usuário selecionado:");
        System.out.println(usuarioEscolhido);

        System.out.println("""
            Opções:
            1) Alterar usuário
            2) Alterar senha
            3) Habilitar/Desabilitar status
            4) Voltar
            """);

        int acao = sc.nextInt();
        sc.nextLine();

        switch (acao) {
            case 1 -> alterarUsuario(usuarioEscolhido);
            case 2 -> alterarSenha(usuarioEscolhido);
            case 3 -> habilitarDesabilitar(usuarioEscolhido);
            case 4 -> { }
        }
    }
    
    public static void listarPedidos() {
        PedidoDAO pedidoDAO = new PedidoDAO();
        List<Pedido> pedidos = pedidoDAO.listarPedidos();

        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        System.out.println("ID | Cliente | Número do Pedido | Total | Status");
        for (Pedido pedido : pedidos) {
            System.out.println(pedido.getId() + " | " + pedido.getClienteNome() + " | "   
                    + pedido.getNumeroPedido() + " | " + pedido.getTotal() + " | " + pedido.getStatus());
        }
    }

    public static void alterarStatusPedido() {
        PedidoDAO pedidoDAO = new PedidoDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite o ID do pedido para alterar o status ou 0 para voltar: ");
        int idPedido = sc.nextInt();
        sc.nextLine(); 

        if (idPedido == 0) return;

        System.out.println("Escolha o novo status:");
        System.out.println("""
            1) Aguardando pagamento
            2) Pagamento rejeitado
            3) Pagamento com sucesso
            4) Aguardando retirada
            5) Em trânsito
            6) Entregue
        """);

        int opcao = sc.nextInt();
        sc.nextLine();

        String novoStatus = switch (opcao) {
            case 1 -> "aguardando pagamento";
            case 2 -> "pagamento rejeitado";
            case 3 -> "pagamento com sucesso";
            case 4 -> "aguardando retirada";
            case 5 -> "em transito";
            case 6 -> "entregue";
            default -> {
                System.out.println("Opção inválida.");
                yield null;
            }
        };

        if (novoStatus != null) {
            boolean atualizado = pedidoDAO.atualizarStatus(idPedido, novoStatus);
            if (atualizado) {
                System.out.println("Status atualizado com sucesso!");
            } else {
                System.out.println("Erro ao atualizar status.");
            }
        }
    }


    public static void alterarUsuario(Usuario usuario) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite o novo nome (pressione Enter para manter o atual): ");
        String nome = sc.nextLine();

        System.out.print("Digite o novo CPF (pressione Enter para manter o atual): ");
        String cpf = sc.nextLine();

        System.out.print("Digite o novo grupo (adm ou estoquista) (pressione Enter para manter o atual): ");
        String grupo = sc.nextLine();

        // Validação do CPF
        if (!cpf.isEmpty() && !validaCPF(cpf)) {
            System.out.println("CPF inválido. Operação cancelada.");
            return;
        }

        // Validação do Grupo
        if (!grupo.isEmpty() && !(grupo.equalsIgnoreCase("adm") || grupo.equalsIgnoreCase("estoquista"))) {
            System.out.println("Grupo inválido. Operação cancelada.");
            return;
        }

        if (!nome.isEmpty()) {
            usuario.setNome(nome);
        }

        if (!cpf.isEmpty()) {
            usuario.setCpf(cpf);
        }

        if (!grupo.isEmpty()) {
            usuario.setGrupo(grupo);
        }

        System.out.print("Salvar alterações? (Y/N): ");
        String salvar = sc.nextLine();
        if (salvar.equalsIgnoreCase("Y")) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.alterarUsuario(usuario);
            System.out.println("Usuário atualizado com sucesso.");
        } else {
            System.out.println("Alterações canceladas.");
        }
    }

    public static boolean validaCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("[^0-9]+")) {
            return false;
        }

        try {
            int soma = 0;
            int peso = 10;
            for (int i = 0; i < 9; i++) {
                int num = Character.getNumericValue(cpf.charAt(i));
                soma += num * peso;
                peso--;
            }

            int resto = soma % 11;
            int digito1 = (resto < 2) ? 0 : 11 - resto;

            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                int num = Character.getNumericValue(cpf.charAt(i));
                soma += num * peso;
                peso--;
            }

            resto = soma % 11;
            int digito2 = (resto < 2) ? 0 : 11 - resto;

            return digito1 == Character.getNumericValue(cpf.charAt(9)) && digito2 == Character.getNumericValue(cpf.charAt(10));
        } catch (InputMismatchException e) {
            return false;
        }
    }

    public static void alterarSenha(Usuario usuario) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite a nova senha: ");
        String novaSenha = sc.nextLine();

        System.out.print("Digite novamente a nova senha: ");
        String novaSenha2 = sc.nextLine();

        if (!novaSenha.equals(novaSenha2)) {
            System.out.println("As senhas não coincidem. Operação cancelada.");
            return;
        }

        try {
            String senhaCriptografada = criptografarSenha(novaSenha);
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.alterarSenha(usuario.getEmail(), senhaCriptografada);
            System.out.println("Senha atualizada com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao alterar senha: " + e.getMessage());
        }
    }

    public static void habilitarDesabilitar(Usuario usuario) {
        Scanner sc = new Scanner(System.in);
        String statusAtual = usuario.getStatus();

        String novoStatus = statusAtual.equalsIgnoreCase("ativado") ? "desativado" : "ativado";

        System.out.println("Status atual: " + statusAtual);
        System.out.print("Alterar status para " + novoStatus + "? (Y/N): ");
        String opcao = sc.nextLine();

        if (opcao.equalsIgnoreCase("Y")) {
            usuario.setStatus(novoStatus);
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.alterarUsuario(usuario);
            System.out.println("Status alterado com sucesso.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private static void incluirUsuario(Usuario usuarioLogado) {
        Scanner sc = new Scanner(System.in);
        UsuarioDAO usuario = new UsuarioDAO();

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("E-mail: ");
        String email = sc.nextLine();

        System.out.print("Grupo (Adm/Estoquista): ");
        String tipoUser = sc.nextLine();

        System.out.print("Digite senha: ");
        String s1 = sc.nextLine();

        System.out.print("Repetir senha: ");
        String s2 = sc.nextLine();

        System.out.print("Salvar (Y/N): ");
        String salvar = sc.nextLine();

        if (salvar.equalsIgnoreCase("Y")) {
            try {
                String resultado = usuario.cadastrarUsuario(nome, cpf, email, tipoUser, s1, s2);
                System.out.println(resultado);
            } catch (SQLException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Cadastro cancelado.");
        }
    }

    public static String criptografarSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(senha.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
