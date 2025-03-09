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
                editarProduto(id);
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
            }
        }
    }

    private static void incluirProduto() {
        Scanner sc = new Scanner(System.in);
        ProdutoDAO produtoDAO = new ProdutoDAO();
        System.out.println("Incluir produto");
        System.out.println("Nome => ");
        String nome = sc.nextLine();
        if (nome.length() > 200) {
            System.out.println("Erro: Nome excede 200 caracteres.");
            return;
        }
    
        double avaliacao = 0;
        boolean avaliacaoValida = false;
        while (!avaliacaoValida) {
            System.out.println("Avaliação => ");
            if (sc.hasNextDouble()) {
                avaliacao = sc.nextDouble();
                sc.nextLine(); 
                if (avaliacao >= 1.0 && avaliacao <= 5.0 && (avaliacao * 10) % 5 == 0) {
                    avaliacaoValida = true;
                } else {
                    System.out.println("Erro: Avaliação inválida! Por favor, insira um valor entre 1.0 e 5.0, com incrementos de 0.5.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número válido para a avaliação.");
                sc.next(); 
            }
        }
    
        System.out.println("Descricao detalhada (máx 2000 caracteres) => ");
        String descricaoDetalhada = sc.nextLine();
        if (descricaoDetalhada.length() > 2000) {
            System.out.println("Erro: Descrição detalhada excede 2000 caracteres.");
            return;
        }
    
        double valorProduto = 0;
        boolean valorValido = false;
        while (!valorValido) {
            System.out.println("Valor (2 casas decimais) => ");
            if (sc.hasNextDouble()) {
                valorProduto = sc.nextDouble();
                valorValido = true;
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número válido para o valor.");
                sc.next(); 
            }
            sc.nextLine(); 
        }
    
        System.out.println("Quantidade em estoque => ");
        int qtdEstoque = sc.nextInt();
        sc.nextLine(); 
    
        System.out.println("Status (ativo/desativado) => ");
        String status = sc.nextLine();
    
        System.out.println("Salvar (Y/N) => ");
        String salvar = sc.nextLine();
    
        if ("Y".equalsIgnoreCase(salvar)) {
            try {
                String resultado = produtoDAO.cadastrarProduto(nome, avaliacao, descricaoDetalhada, qtdEstoque, valorProduto, status);
                System.out.println(resultado);
                int produtoId = produtoDAO.obterUltimoProdutoId(); 
                incluirImagem(produtoId); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if ("N".equalsIgnoreCase(salvar)) {
            listarProdutos();
        } else {
            System.out.println("Opção inválida!");
        }
    }

    private static void editarProduto(int id) {
        
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
    private static void salvarImagens(int produtoId) {
            String diretorioDestino = "/imagens/" + produtoId;
            File diretorio = new File(diretorioDestino);
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }
        
            System.out.println("Imagens salvas com sucesso no diretório: " + diretorioDestino);
        }
        
    }


    
}
