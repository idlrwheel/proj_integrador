package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutoDAO{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_pi";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Password0108!";

    public String cadastrarProduto(String nome, double avaliacao, String descricaoDetalhada, double valorProduto, int qtdEstoque) throws SQLException {

    if (avaliacao < 1.0 || avaliacao > 5.0 || avaliacao % 0.5 != 0) return "Erro: Avaliação inválida!";
    if (nome == null || nome.isEmpty()) return "Erro: Nome do produto não pode estar vazio!";
    if (valorProduto <= 0) return "Erro: O preço deve ser maior que zero!";
    if (qtdEstoque < 0) return "Erro: A quantidade em estoque não pode ser negativa!";

    String sql = "INSERT INTO Produtos (nome, avaliacao, DescricaoDetalhada, Preco, QtdEstoque) "
            + "VALUES (?, ?, ?, ?, ?)";

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
         PreparedStatement inserirProdutoStmt = connection.prepareStatement(sql)) {

        inserirProdutoStmt.setString(1, nome);
        inserirProdutoStmt.setDouble(2, avaliacao);
        inserirProdutoStmt.setString(3, descricaoDetalhada);
        inserirProdutoStmt.setDouble(4, valorProduto);
        inserirProdutoStmt.setInt(5, qtdEstoque);

        inserirProdutoStmt.executeUpdate();
        return "Produto cadastrado com sucesso!";
    }
}
}