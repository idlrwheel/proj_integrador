package com.example.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.models.Usuario;

public class UsuarioDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_pi";
    private static final String DB_username = "root";
    private static final String DB_password = "Password0108!";

    public Usuario validarLogin(String email, String senha) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_username, DB_password)) {
            System.out.println("Conexão estabelecida com o banco de dados.");

            String sql = "SELECT * FROM userBackoffice WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String senhaHash = rs.getString("senha");
                String grupo = rs.getString("tipoUser");
                String status = rs.getString("status");

                if (!status.equals("ativado") || (!grupo.equals("adm") && !grupo.equals("estoquista"))) {
                    System.out.println("Acesso negado! Status ou grupo inválido.");
                    return null;
                }

                if (encriptarSenha(senha).equals(senhaHash)) {
                    System.out.println("Senhas coincidem. Login bem-sucedido.");
                    return new Usuario(email, grupo, status);
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

}


