package com.example.classes;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.example.dao.ProdutoDAO;
import com.example.models.Produto;

public class MenuProduto {

    static void listarProdutos() {
        List<Produto> produtos = ProdutoDAO.listarProdutos();

        System.out.println("ID | Nome | Quantidade | Valor | Status");
        for (Produto produto : produtos) {
            System.out.println(produto.getCodigo() + " | " + produto.getNome() + " | " + produto.getQtdEstoque() + " | " + produto.getValorProduto() + " | " + produto.getStatus());
        }

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
                editarProduto(id); // Chama o método de edição passando o id
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
            }
        }
    }

    private static void incluirProduto() {
        // Código do método incluirProduto permanece inalterado
    }

    private static void editarProduto(int id) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        try {
            Produto produto = produtoDAO.buscarProdutoPorId(id); // Busca o produto pelo ID
            if (produto != null) {
                // Aqui você pode exibir os detalhes do produto e permitir ao usuário alterá-los
                Scanner sc = new Scanner(System.in);
    
                System.out.println("Editar produto: " + produto.getNome());
    
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
    
    

    private static void incluirImagem(int produtoId) throws SQLException {
        // Código do método incluirImagem permanece inalterado
    }

    private static void salvarImagens(int produtoId) {
        // Código do método salvarImagens permanece inalterado
    }
}
