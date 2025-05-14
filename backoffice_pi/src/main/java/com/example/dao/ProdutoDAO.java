package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.ImagemProduto;
import com.example.models.Produto;

public class ProdutoDAO{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_pi";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Password0108!";


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
     String desmarcarSql = "UPDATE imagensProduto SET principal = false WHERE produto_id = ?";
     String insertSql = "INSERT INTO imagensProduto (produto_id, nome_arquivo, diretorio_origem, principal) VALUES (?, ?, ?, ?)";

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
        connection.setAutoCommit(false); 

        if (principal) {
            try (PreparedStatement desmarcar = connection.prepareStatement(desmarcarSql)) {
                desmarcar.setInt(1, produtoId);
                desmarcar.executeUpdate();
            }
        }

        try (PreparedStatement inserir = connection.prepareStatement(insertSql)) {
            inserir.setInt(1, produtoId);
            inserir.setString(2, nomeArquivo);
            inserir.setString(3, diretorioOrigem);
            inserir.setBoolean(4, principal);
            inserir.executeUpdate();
        }

        connection.commit();
    }
}

    public void atualizarImagemPrincipal(int produtoId, int imagemId, String diretorio) throws SQLException {
        String sqlUpdatePrincipal = "UPDATE imagensProduto SET principal = false WHERE produto_id = ?";
        String sqlSetPrincipal = "UPDATE imagensProduto SET principal = true, diretorio_origem = ? WHERE id = ?";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
    
            try (PreparedStatement statement = connection.prepareStatement(sqlUpdatePrincipal)) {
                statement.setInt(1, produtoId);
                statement.executeUpdate();
            }
    
            try (PreparedStatement statement = connection.prepareStatement(sqlSetPrincipal)) {
                statement.setString(1, diretorio);
                statement.setInt(2, imagemId);
                statement.executeUpdate();
            }
        }
    }
    

    public boolean alterarImagemProduto(int imagemId, String novoNomeArquivo, String novoDiretorio, boolean principal) throws SQLException {
        if (principal) {
            String sqlUpdatePrincipal = "UPDATE imagensProduto SET principal = false WHERE produto_id = (SELECT produto_id FROM imagensProduto WHERE id = ?)";
    
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(sqlUpdatePrincipal)) {
                statement.setInt(1, imagemId);
                statement.executeUpdate();
            }
        }
    
        String sql = "UPDATE imagensProduto SET nome_arquivo = ?, diretorio_origem = ?, principal = ? WHERE id = ?";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, novoNomeArquivo);
            statement.setString(2, novoDiretorio);
            statement.setBoolean(3, principal);
            statement.setInt(4, imagemId);
    
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public ImagemProduto getImagemPrincipal(int produtoId) throws SQLException {
        String sql = "SELECT id, produto_id, nome_arquivo, diretorio_origem, principal FROM imagensProduto WHERE produto_id = ? AND principal = true";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            statement.setInt(1, produtoId);
    
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ImagemProduto(
                        resultSet.getInt("id"),
                        resultSet.getInt("produto_id"),
                        resultSet.getString("nome_arquivo"),
                        resultSet.getString("diretorio_origem"),
                        resultSet.getBoolean("principal")
                    );
                }
            }
        }
        return null; // Nenhuma imagem principal encontrada
    }
    
    


    public List<ImagemProduto> listarImagensProduto(int produtoId) throws SQLException {
        List<ImagemProduto> imagens = new ArrayList<>();
        String sql = "SELECT id, nome_arquivo, diretorio_origem, principal FROM imagensProduto WHERE produto_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, produtoId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    imagens.add(new ImagemProduto(
                        rs.getInt("id"),
                        produtoId,
                        rs.getString("nome_arquivo"),
                        rs.getString("diretorio_origem"),
                        rs.getBoolean("principal")
                    ));
                }
            }
        }
        return imagens;
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
