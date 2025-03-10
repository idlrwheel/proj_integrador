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

    //metodo incluirImagem
    private static void incluirImagem(int produtoId) throws SQLException {
        Scanner sc = new Scanner(System.in);
        ProdutoDAO produtoDAO = new ProdutoDAO();
        boolean continuar = true;
    
        while (continuar) {
            System.out.println("Incluir imagem do produto");
            System.out.println("Nome do arquivo => ");
            String nomeArquivo = sc.nextLine();
            System.out.println("Diretório de origem => ");
            String diretorioOrigem = sc.nextLine();
            System.out.println("É a imagem principal? (Y/N) => ");
            String imagemPrincipal = sc.nextLine();
    
            boolean principal = "Y".equalsIgnoreCase(imagemPrincipal);
    
            if (principal) {
                produtoDAO.atualizarImagemPrincipal(produtoId);
            }
    
            produtoDAO.cadastrarImagemProduto(produtoId, nomeArquivo, diretorioOrigem, principal);
    
            System.out.println("Salvar e incluir mais uma imagem (1), Salvar e finalizar (2), Não salvar e finalizar (3) => ");
            int opcao = sc.nextInt();
            sc.nextLine(); 
    
            switch (opcao) {
                case 1:
                    break;
                case 2:
                    salvarImagens(produtoId);
                    listarProdutos();
                    continuar = false;
                    break;
                case 3:
                    listarProdutos();
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }

    
}
    private static void alterarImagem(int produtoId) throws SQLException {
    Scanner sc = new Scanner(System.in);
    ProdutoDAO produtoDAO = new ProdutoDAO();
    List<ImagemProduto> imagens = produtoDAO.listarImagensProduto(produtoId);

    System.out.println("Imagens do Produto:");
    for (ImagemProduto img : imagens) {
        System.out.println(img.getId() + " | " + img.getNomeArquivo() + " | Principal: " + (img.isPrincipal() ? "Sim" : "Não"));
    }

    System.out.println("Digite o ID da imagem para alterar ou 0 para voltar:");
    int imagemId = sc.nextInt();
    sc.nextLine();

    if (imagemId == 0) return;

    System.out.println("Novo nome do arquivo => ");
    String novoNome = sc.nextLine();
    System.out.println("Novo diretório de origem => ");
    String novoDiretorio = sc.nextLine();
    System.out.println("Definir como principal? (Y/N) => ");
    boolean principal = sc.nextLine().equalsIgnoreCase("Y");

    boolean sucesso = produtoDAO.alterarImagemProduto(imagemId, novoNome, novoDiretorio, principal);
    if (sucesso) {
        System.out.println("Imagem atualizada com sucesso!");
    } else {
        System.out.println("Erro ao atualizar a imagem.");
    }
}

    private static void salvarImagens(int produtoId) {
    String diretorioDestino = "/imagens/" + produtoId;
    File diretorio = new File(diretorioDestino);
    if (!diretorio.exists()) {
        diretorio.mkdirs();
    }
    System.out.println("Imagens salvas com sucesso no diretório: " + diretorioDestino);
    
    }
}
