package com.example.classes;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.example.dao.ProdutoDAO;
import com.example.models.Produto;
import com.example.models.Usuario;

public class MenuProduto {

 // Vamos obter o usuário logado do SessionManager
    private static Usuario usuarioLogado = SessionManager.getUsuarioLogado();

    static void listarProdutos() {
        List<Produto> produtos = ProdutoDAO.listarProdutos();

        System.out.println("ID | Nome | Quantidade | Valor | Status");
        for (Produto produto : produtos) {
            System.out.println(produto.getCodigo() + " | " + produto.getNome() + " | " + produto.getQtdEstoque() + " | " + produto.getValorProduto() + " | " + produto.getStatus());
        }

        // Verifique se o usuário é um administrador antes de permitir a edição ou inclusão
        if ("adm".equals(usuarioLogado.getGrupo())) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Escolha uma opção: 0 para voltar, ID do produto para editar, i para incluir");
            String opcao = scanner.nextLine();

            if (opcao.equals("0")) {
                return;
            } else if (opcao.equalsIgnoreCase("i")) {
                incluirProduto();
            } else {
                try {
                    int id = Integer.parseInt(opcao);
                    editarProduto(id);
                } catch (NumberFormatException e) {
                    System.out.println("Opção inválida.");
                }
            }
        } else if ("estoquista".equals(usuarioLogado.getGrupo())) {
            // Estoquista pode ver e editar apenas a quantidade em estoque
            Scanner scanner = new Scanner(System.in);
            System.out.println("Escolha uma opção: 0 para voltar, ID do produto para editar quantidade");
            String opcao = scanner.nextLine();

            if (opcao.equals("0")) {
                return;
            } else {
                try {
                    int id = Integer.parseInt(opcao);
                    editarQuantidadeEstoque(id);
                } catch (NumberFormatException e) {
                    System.out.println("Opção inválida.");
                }
            }
        } else {
            System.out.println("Você não tem permissão para editar ou incluir produtos.");
        }
    }

    private static void incluirProduto() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe o nome do produto: ");
        String nome = scanner.nextLine();

        System.out.println("Informe a avaliação (1.0 a 5.0): ");
        double avaliacao = scanner.nextDouble();
        scanner.nextLine();  // Consumir a quebra de linha após o double

        System.out.println("Informe a descrição detalhada do produto: ");
        String descricaoDetalhada = scanner.nextLine();

        System.out.println("Informe a quantidade em estoque: ");
        int qtdEstoque = scanner.nextInt();

        System.out.println("Informe o valor do produto: ");
        double valorProduto = scanner.nextDouble();
        scanner.nextLine();  // Consumir a quebra de linha após o double

        System.out.println("Informe o status do produto (ativo/desativado): ");
        String status = scanner.nextLine();

        ProdutoDAO produtoDAO = new ProdutoDAO();
        try {
            // Chama o método de cadastro no ProdutoDAO
            String resultado = produtoDAO.cadastrarProduto(nome, avaliacao, descricaoDetalhada, qtdEstoque, valorProduto, status);
            System.out.println(resultado);  // Exibe a mensagem de sucesso ou falha
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }

        listarProdutos(); // Volta para a lista de produtos após cadastrar
    }

    private static void editarProduto(int id) {
        ProdutoDAO produtoDAO = new ProdutoDAO();

        try {
            Produto produto = produtoDAO.buscarProdutoPorId(id); // Busca o produto pelo ID
            if (produto != null) {
                // Aqui você pode exibir os detalhes do produto e permitir ao usuário alterá-los
                Scanner sc = new Scanner(System.in);

                System.out.println("Editar produto: " + produto.getNome());

                // Permite editar todos os campos para administradores
                System.out.println("Novo nome: ");
                produto.setNome(sc.nextLine());

                System.out.println("Nova avaliação (1.0 a 5.0): ");
                produto.setAvaliacao(sc.nextDouble());
                sc.nextLine(); // Consumir a quebra de linha após o double

                System.out.println("Nova descrição detalhada: ");
                produto.setDescricaoDetalhada(sc.nextLine());

                System.out.println("Nova quantidade em estoque: ");
                produto.setQtdEstoque(sc.nextInt());

                System.out.println("Novo valor do produto: ");
                produto.setValorProduto(sc.nextDouble());
                sc.nextLine(); // Consumir a quebra de linha após o double

                System.out.println("Novo status (ativo/desativado): ");
                produto.setStatus(sc.nextLine());

                // Agora, atualiza o produto no banco de dados
                String resultado = produtoDAO.atualizarProduto(produto);
                System.out.println(resultado);
            } else {
                System.out.println("Produto não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ou atualizar produto: " + e.getMessage());
        }

        listarProdutos(); // Volta para a lista de produtos
    }

    // Método para o estoquista editar apenas a quantidade em estoque
    private static void editarQuantidadeEstoque(int id) {
        ProdutoDAO produtoDAO = new ProdutoDAO();

        try {
            Produto produto = produtoDAO.buscarProdutoPorId(id); // Busca o produto pelo ID
            if (produto != null) {
                // Estoquista pode editar apenas a quantidade em estoque
                Scanner sc = new Scanner(System.in);

                System.out.println("Editar quantidade em estoque do produto: " + produto.getNome());

                System.out.println("Nova quantidade em estoque: ");
                produto.setQtdEstoque(sc.nextInt());

                // Agora, atualiza apenas a quantidade no banco de dados
                String resultado = produtoDAO.atualizarProduto(produto);
                System.out.println(resultado);
            } else {
                System.out.println("Produto não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ou atualizar produto: " + e.getMessage());
        }

        listarProdutos(); // Volta para a lista de produtos
    }

    
    

    private static void incluirImagem(int produtoId) throws SQLException {
        // Código do método incluirImagem permanece inalterado
    }

    private static void salvarImagens(int produtoId) {
        // Código do método salvarImagens permanece inalterado
    }
}
