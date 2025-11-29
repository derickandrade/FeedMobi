package com.user.fmuser.models;

import java.sql.*;

public class Database {
    public static void main(String[] args) {
        // Área de testes
        connect();
        // Verifica se o cpf 000.000.000-00 está cadastrado
        String meu_cpf = "00000000000";
        System.out.printf("%b\n", cpfCadastrado(meu_cpf));

        Usuario usuario = new Usuario("12345678900", "a", "b", "c", "d");
        adicionaUsuario(usuario);

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

    public static void adicionaUsuario(Usuario usuario) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "INSERT INTO Usuario VALUES ('" +
                            usuario.getCPF() + "', '" +
                            usuario.getNome() + "', '" +
                            usuario.getSobrenome() + "', '" +
                            usuario.getEmail() + "', '" +
                            usuario.getSenha() + "');"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public static class Usuario {
        public void setCpf(String cpf) {
            if (cpf.length() != 11) {
                return;
            }
            this.cpf = cpf;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setSobrenome(String sobrenome) {
            this.sobrenome = sobrenome;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        private String cpf;
        private String nome;
        private String sobrenome;
        private String email;
        private String senha;

        public String getCPF() {
            return cpf;
        }

        public String getNome() {
            return nome;
        }

        public String getSobrenome() {
            return sobrenome;
        }

        public String getEmail() {
            return email;
        }

        public String getSenha() {
            return senha;
        }

        public Usuario(String cpf, String nome, String sobrenome, String email, String senha) {
            setCpf(cpf);
            this.nome = nome;
            this.sobrenome = sobrenome;
            this.email = email;
            this.senha = senha;
        }
    }
}
