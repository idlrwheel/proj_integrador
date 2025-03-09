import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Produto;

public class ProdutoDAO{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_pi";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Password0108!";

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

}
