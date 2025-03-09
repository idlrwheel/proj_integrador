package com.example.classes;
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
    
        double avaliacao = 0;
        boolean avaliacaoValida = false;
        while (!avaliacaoValida) {
            System.out.println("Avaliação (entre 1.0 e 5.0, incrementos de 0.5) => ");
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
    
        System.out.println("Descricao detalhada => ");
        String descricaoDetalhada = sc.nextLine();
    
        double valorProduto = 0;
        boolean valorValido = false;
        while (!valorValido) {
            System.out.println("Valor => ");
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
                listarProdutos();
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

    
}
