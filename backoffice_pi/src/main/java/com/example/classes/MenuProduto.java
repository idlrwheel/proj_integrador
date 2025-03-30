package com.example.classes;


import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.example.dao.ProdutoDAO;
import com.example.models.ImagemProduto;
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
                Menu.exibirMenu();
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
             String resultado = produtoDAO.cadastrarProduto(nome, avaliacao, descricaoDetalhada, qtdEstoque, valorProduto, status);
                 System.out.println(resultado);
                 int produtoId = produtoDAO.obterUltimoProdutoId(); 
                 incluirImagem(produtoId); 
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }

        listarProdutos(); 
        
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

                System.out.println("Deseja alterar a imagem do produto: ");

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

        listarProdutos(); 
    }

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
                produtoDAO.atualizarImagemPrincipal(produtoId, 0, diretorioOrigem);
            }
    
            produtoDAO.cadastrarImagemProduto(produtoId, nomeArquivo, diretorioOrigem, principal);
    
            System.out.println("Salvar e incluir mais uma imagem (1), Salvar e finalizar (2), Não salvar e finalizar (3) => ");
            int opcao = sc.nextInt();
            sc.nextLine(); 
    
            switch (opcao) {
                case 1:
                    break;
                case 2:
                    salvarImagens(produtoId, nomeArquivo, diretorioOrigem);
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
        salvarImagens(imagemId, novoNome, novoDiretorio);
    } else {
        System.out.println("Erro ao atualizar a imagem.");
    }
}

private static boolean salvarImagens(int produtoId, String nomeArquivo, String diretorioOrigem) {
    if (produtoId <= 0) {
        System.err.println("Erro: ID do produto inválido.");
        return false;
    }

    String diretorioDestino = diretorioOrigem + File.separator + nomeArquivo;
    File diretorio = new File(diretorioDestino);

    try {
        if (!diretorio.exists() && diretorio.mkdirs()) {
            System.out.println("Imagens salvas com sucesso no diretório: " + diretorioDestino);
            return true;
        } else if (diretorio.exists()) {
            System.out.println("O diretório já existe: " + diretorioDestino);
            return true;
        } else {
            System.err.println("Erro ao criar o diretório: " + diretorioDestino);
        }
    } catch (SecurityException e) {
        System.err.println("Erro de permissão ao criar o diretório: " + e.getMessage());
    }

    return false;
}

}
