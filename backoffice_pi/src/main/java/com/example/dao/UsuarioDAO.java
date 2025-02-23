package com.example.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.example.models.Usuario;

public class UsuarioDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_pi";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Password0108!";

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
                String cpf = rs.getString("cpf");

                if (!status.equals("ativado") || (!grupo.equals("adm") && !grupo.equals("estoquista"))) {
                    System.out.println("Acesso negado! Status ou grupo inválido.");
                    return null;
                }

                if (encriptarSenha(senha).equals(senhaHash)) {
                    System.out.println("Senhas coincidem. Login bem-sucedido.");
                    return new Usuario(email, grupo, status, nome, cpf);
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
                String cpf = rs.getString("cpf");
                // Adicionando o usuário à lista
                usuarios.add(new Usuario(email, grupo, status, nome, cpf));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    private boolean validarEmail(String email) {
        String regex = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    private boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        int[] p1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] p2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        return validarDigito(cpf, p1, 9) && validarDigito(cpf, p2, 10);
    }

    private boolean validarDigito(String cpf, int[] pesos, int pos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += (cpf.charAt(i) - '0') * pesos[i];
        }
        int resto = soma % 11;
        int digito = (resto < 2) ? 0 : (11 - resto);
        return digito == (cpf.charAt(pos) - '0');
    }


    public String cadastrarUsuario(String nome, String cpf, String email, String tipoUser, String s1, String s2) throws SQLException, NoSuchAlgorithmException{
        if(!s1.equals(s2))
            return "Senhas não coincidem!";
        if(!validarEmail(email))
           return "E-Mail inválido";
        if(!validarCPF(cpf))
           return "CPF invalido!";
        
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
           String verEmailSQL = "SELECT * FROM userBackoffice WHERE email = ?";
           PreparedStatement verEmailStmt = connection.prepareStatement(verEmailSQL);
           verEmailStmt.setString(1, email);
           ResultSet rs = verEmailStmt.executeQuery();
           if(rs.next())
             return "E-mail já cadastrado!";

            if(!tipoUser.equalsIgnoreCase("Adm") && !tipoUser.equalsIgnoreCase("Estoquista"))
             return "Grupo invalido!";  

           String senhaCripto = encriptarSenha(s1);

           String inserirUsuarioSQL = "INSERT INTO userBackoffice (nome, cpf, email, tipoUser, senha, status) VALUES (?, ?, ?, ?, ?, ?)";
           PreparedStatement inserirUsuarioStmt = connection.prepareStatement(inserirUsuarioSQL);
           inserirUsuarioStmt.setString(1, nome);
           inserirUsuarioStmt.setString(2, cpf);
           inserirUsuarioStmt.setString(3, email);
           inserirUsuarioStmt.setString(4, tipoUser);
           inserirUsuarioStmt.setString(5, senhaCripto);
           inserirUsuarioStmt.setString(6, "Ativado");
           inserirUsuarioStmt.executeUpdate();

           return "Usuario cadastrado com sucesso!";
        }  
    }
}
