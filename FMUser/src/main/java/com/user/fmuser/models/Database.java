package com.user.fmuser.models;

import java.sql.*;

public class Database {
    public static void main(String[] args) {
        // Área de testes
        connect();
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            String[] types = {"TABLE"};

            ResultSet tables = dbm.getTables(null, null, "%", types);

            System.out.println("Table Names:");
            while (tables.next()) {
                System.out.println(tables.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
}
