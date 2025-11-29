package com.user.fmuser.models;

import java.sql.*;

public class Database {
    public static void main(String[] args) {
        // Área de testes
        connect();
        // Verifica se o cpf 000.000.000-00 está cadastrado
        String meu_cpf = "00000000000";
        System.out.printf("%b", cpfCadastrado(meu_cpf));
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
}
