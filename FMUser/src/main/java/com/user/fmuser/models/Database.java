package com.user.fmuser.models;

import java.sql.*;

public class Database {
    public static void main(String[] args) {
        // Área de testes
        connect();
        // Verifica se o cpf 000.000.000-00 está cadastrado
        String meu_cpf = "00000000000";
        System.out.printf("%b\n", cpfCadastrado(meu_cpf));

        Usuario teste = recolheUsuario("00000000000");
        if (teste != null) {
            System.out.println(teste.getNome());
        }

        teste = recolheUsuario("00000000001");
        if (teste == null) {
            System.out.println("Usuário inexistente");
        }

        Usuario usuario = new Usuario("12345678900", "a", "b", "c", "d");

        try {
            adicionaUsuario(usuario);
        } catch (SQLException e) {
            System.err.println("Usuário já cadastrado :)");
        }

        usuario.setNome("j");
        usuario.setCpf("38383838388");
        // Atualização não ocorre porque usuário não existe
        atualizaUsuario(usuario);

        System.out.printf("%b\n", cpfCadastrado("12345678900"));

        removeUsuario("12345678900");

        System.out.printf("%b\n", cpfCadastrado("12345678900"));

        disconnect();
    }

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/FeedMobiDB";
    private static Connection connection;

    public static void connect() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("couldn't load database driver");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            String user = "user";
            String password = "";
            connection = DriverManager.getConnection(DB_URL, user, password);

        } catch (SQLException e) {
            System.err.println("couldn't connect to database");
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public static void disconnect() {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cpfCadastrado(String cpf) {
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(
                    "SELECT COUNT(cpf) FROM Usuario WHERE cpf = '" + cpf + "';"
            );
            results.first();
            int quantity = results.getInt("COUNT(cpf)");
            return quantity >= 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void adicionaUsuario(Usuario usuario) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
                "INSERT INTO Usuario VALUES ('" +
                        usuario.getCPF() + "', '" +
                        usuario.getNome() + "', '" +
                        usuario.getSobrenome() + "', '" +
                        usuario.getEmail() + "', '" +
                        usuario.getSenha() + "', '" +
                        (usuario.isAdmin() ? 1 : 0) + "');"
        );
    }

    public static void removeUsuario(String cpf) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "DELETE FROM Usuario WHERE cpf = '" + cpf + "';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void atualizaUsuario(Usuario usuario) {
        try {
            String updateString = "UPDATE Usuario SET ";
            updateString += "nome = '" + usuario.getNome() + "', ";
            updateString += "sobrenome = '" + usuario.getSobrenome() + "', ";
            updateString += "email = '" + usuario.getEmail() + "', ";
            updateString += "senha = '" + usuario.getSenha() + "' ";
            updateString += "WHERE cpf = '" + usuario.getCPF() + "';";

            Statement statement = connection.createStatement();
            statement.executeUpdate(updateString);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Usuario recolheUsuario(String cpf) {
        try {
            String query = "SELECT * FROM Usuario WHERE cpf = '" + cpf + "';";

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            if (results.first()) {
                Usuario usuario = new Usuario(
                        results.getString("cpf"),
                        results.getString("nome"),
                        results.getString("sobrenome"),
                        results.getString("email"),
                        results.getString("senha")
                );

                usuario.setIsAdmin(results.getBoolean("administrador"));
                return usuario;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
