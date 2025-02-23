package com.example.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.models.Usuario;

public class UsuarioDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_pi";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public Usuario validarLogin(String email, String senha) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            System.out.println("Conexão estabelecida com o banco de dados.");

            String sql = "SELECT * FROM userBackoffice WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String senhaHash = rs.getString("senha");
                String grupo = rs.getString("tipoUser");
                String status = rs.getString("status");
                String nome = rs.getString("nome");

                if (!status.equals("ativado") || (!grupo.equals("adm") && !grupo.equals("estoquista"))) {
                    System.out.println("Acesso negado! Status ou grupo inválido.");
                    return null;
                }

                if (encriptarSenha(senha).equals(senhaHash)) {
                    System.out.println("Senhas coincidem. Login bem-sucedido.");
                    return new Usuario(email, grupo, status, nome);
                } else {
                    System.out.println("Senha incorreta!");
                }
            } else {
                System.out.println("Usuário não encontrado!");
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String encriptarSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(senha.getBytes());
        byte[] bytes = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Novo método para listar todos os usuários
    public static List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String sql = "SELECT * FROM userBackoffice";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String email = rs.getString("email");
                String grupo = rs.getString("tipoUser");
                String status = rs.getString("status");
                String nome = rs.getString("nome");
                // Adicionando o usuário à lista
                usuarios.add(new Usuario(email, grupo, status, nome));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
