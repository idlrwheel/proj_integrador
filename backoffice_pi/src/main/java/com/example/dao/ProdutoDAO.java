package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Produto;

public class ProdutoDAO{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_pi";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "58725997";


    //Método para incluir um produto
    public String cadastrarProduto(String nome, double avaliacao, String descricaoDetalhada, int qtdEstoque, double valorProduto, String status) throws SQLException {
        String sql = "INSERT INTO produtos (nome, avaliacao, descricaoDetalhada, qtdEstoque, valorProduto, status) VALUES (?, ?, ?, ?, ?, ?)";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            statement.setString(1, nome);
            statement.setDouble(2, avaliacao);
            statement.setString(3, descricaoDetalhada);
            statement.setInt(4, qtdEstoque);
            statement.setDouble(5, valorProduto);
            statement.setString(6, status);
    
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return "Produto incluído com sucesso!";
            } else {
                return "Falha ao incluir produto.";
            }
        }
    }

public static List<Produto> listarProdutos() {
    List<Produto> produtos = new ArrayList<>();
    String sql = "SELECT * FROM produtos ORDER BY codigo DESC";

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet rs = statement.executeQuery()) {

        while (rs.next()) {
            produtos.add(new Produto(
                rs.getInt("codigo"),
                rs.getString("nome"),
                rs.getDouble("avaliacao"),
                rs.getString("descricaoDetalhada"),
                rs.getInt("qtdEstoque"),
                rs.getDouble("valorProduto"),
                rs.getString("status")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return produtos;
    }

    public void cadastrarImagemProduto(int produtoId, String nomeArquivo, String diretorioOrigem, boolean principal) throws SQLException {
        String sql = "INSERT INTO imagensProduto (produto_id, nome_arquivo, diretorio_origem, principal) VALUES (?, ?, ?, ?)";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            statement.setInt(1, produtoId);
            statement.setString(2, nomeArquivo);
            statement.setString(3, diretorioOrigem);
            statement.setBoolean(4, principal);
    
            statement.executeUpdate();
        }
    }

    public void atualizarImagemPrincipal(int produtoId) throws SQLException {
        String sql = "UPDATE imagensProduto SET principal = false WHERE produto_id = ?";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            statement.setInt(1, produtoId);
            statement.executeUpdate();
        }
    }

    public int obterUltimoProdutoId() throws SQLException {
        String sql = "SELECT MAX(codigo) AS max_id FROM produtos";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
    
            if (rs.next()) {
                return rs.getInt("max_id");
            } else {
                throw new SQLException("Erro ao obter o ID do último produto inserido.");
            }
        }
    }

    public static void adicionarProduto(Produto produto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'adicionarProduto'");
    }
    public Produto buscarProdutoPorId(int id) throws SQLException {
        Produto produto = null;
        String sql = "SELECT * FROM produtos WHERE codigo = ?"; // Seleciona o produto pelo código (ID)
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            statement.setInt(1, id); // Define o valor do ID no preparedStatement
    
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) { // Se encontrar o produto
                    produto = new Produto(
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        rs.getDouble("avaliacao"),
                        rs.getString("descricaoDetalhada"),
                        rs.getInt("qtdEstoque"),
                        rs.getDouble("valorProduto"),
                        rs.getString("status")
                    );
                }
            }
        }
    
        return produto; // Retorna o produto encontrado ou null se não encontrar
    }
    public String atualizarProduto(Produto produto) throws SQLException {
        String sql = "UPDATE produtos SET nome = ?, avaliacao = ?, descricaoDetalhada = ?, qtdEstoque = ?, valorProduto = ?, status = ? WHERE codigo = ?";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getAvaliacao());
            statement.setString(3, produto.getDescricaoDetalhada());
            statement.setInt(4, produto.getQtdEstoque());
            statement.setDouble(5, produto.getValorProduto());
            statement.setString(6, produto.getStatus());
            statement.setInt(7, produto.getCodigo());
    
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return "Produto atualizado com sucesso!";
            } else {
                return "Falha ao atualizar produto.";
            }
        }
    }
    
    

}
