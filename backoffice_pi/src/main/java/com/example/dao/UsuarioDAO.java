package com.example.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import com.example.models.Usuario;

public class UsuarioDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_pi";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "58725997";

    // Método para validar login
    public Usuario validarLogin(String email, String senha) {
        String sql = "SELECT * FROM userBackoffice WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String senhaHash = rs.getString("senha");
                String grupo = rs.getString("tipoUser");
                String status = rs.getString("status");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");

                if (!"ativado".equalsIgnoreCase(status) || (!"adm".equalsIgnoreCase(grupo) && !"estoquista".equalsIgnoreCase(grupo))) {
                    System.out.println("Acesso negado! Conta desativada ou grupo inválido.");
                    return null;
                }

                if (encriptarSenha(senha).equals(senhaHash)) {
                    System.out.println("Login bem-sucedido!");
                    return new Usuario(email, grupo, status, nome, cpf, senha);
                } else {
                    System.out.println("Erro: Senha incorreta!");
                }
            } else {
                System.out.println("Erro: Usuário não encontrado!");
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para criptografar senha
    public String encriptarSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(senha.getBytes());
        byte[] bytes = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Método para listar todos os usuários
    public static List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM userBackoffice";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getString("email"),
                        rs.getString("tipoUser"),
                        rs.getString("status"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("senha")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Validação de e-mail
    private boolean validarEmail(String email) {
        String regex = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    // Validação de CPF
    private boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // Remove caracteres não numéricos

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            System.out.println("Erro: CPF inválido! Deve conter 11 dígitos e não pode ser uma sequência repetida.");
            return false;
        }

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

    // Método para cadastrar usuário
    public String cadastrarUsuario(String nome, String cpf, String email, String tipoUser, String s1, String s2)
            throws SQLException, NoSuchAlgorithmException {

        if (!s1.equals(s2)) return "Erro: As senhas não coincidem!";
        if (!validarEmail(email)) return "Erro: E-mail inválido!";
        if (!validarCPF(cpf)) return "Erro: CPF inválido!";

        String sql = "INSERT INTO userBackoffice (nome, cpf, email, tipoUser, senha, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement inserirUsuarioStmt = connection.prepareStatement(sql)) {

            inserirUsuarioStmt.setString(1, nome);
            inserirUsuarioStmt.setString(2, cpf);
            inserirUsuarioStmt.setString(3, email);
            inserirUsuarioStmt.setString(4, tipoUser);
            inserirUsuarioStmt.setString(5, encriptarSenha(s1));
            inserirUsuarioStmt.setString(6, "ativado"); // Status padrão

            inserirUsuarioStmt.executeUpdate();
            return "Usuário cadastrado com sucesso!";
        }
    }

    // Método para alterar usuário
    public void alterarUsuario(Usuario usuario) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            connection.setAutoCommit(false);

            // Validação do CPF antes da atualização
            if (!validarCPF(usuario.getCpf())) {
                System.out.println("Erro: CPF inválido! Nenhuma alteração foi feita.");
                return;
            }

            // Garantir que o tipoUser seja válido
            String tipoUser = usuario.getGrupo().toLowerCase().trim();
            if (!tipoUser.equals("adm") && !tipoUser.equals("estoquista") && !tipoUser.equals("cliente")) {
                System.out.println("Erro: tipoUser inválido! Apenas 'adm', 'estoquista' ou 'cliente' são permitidos.");
                return;
            }

            // Garantir que o status seja válido
            String status = usuario.getStatus().toLowerCase().trim();
            if (!status.equals("ativado") && !status.equals("desativado")) {
                System.out.println("Erro: status inválido! Apenas 'ativado' ou 'desativado' são permitidos.");
                return;
            }

            String sql = "UPDATE userBackoffice SET nome = ?, cpf = ?, tipoUser = ?, status = ? WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getCpf());
            statement.setString(3, tipoUser);
            statement.setString(4, status);
            statement.setString(5, usuario.getEmail());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                connection.commit();
                System.out.println("Usuário atualizado com sucesso.");
            } else {
                connection.rollback();
                System.out.println("Nenhuma alteração foi feita. Verifique os dados informados.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para alterar senha
    public void alterarSenha(String email, String novaSenha) {
        String sql = "UPDATE userBackoffice SET senha = ? WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, encriptarSenha(novaSenha));
            statement.setString(2, email);

            if (statement.executeUpdate() > 0) {
                System.out.println("Senha atualizada com sucesso.");
            } else {
                System.out.println("Erro: Usuário não encontrado.");
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
