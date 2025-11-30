package com.user.fmuser.models;

import java.sql.*;

public class Database {
    /**
     * Main function for testing purposes only.
     * @param args Test function args
     */
    public static void main(String[] args) {
    }

    // Database URL
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/FeedMobiDB";
    // Database connection handler
    private static Connection connection;

    /**
     * Attempt to connect to locally hosted database through "user" with blank password.
     * Failure to connect will cause the program to crash, as this is unrecoverable.
     */
    public static void connect() {
        try {
            // Dynamically load DB driver
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("couldn't load database driver");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            // Attempt connection
            String user = "user";
            String password = "";
            connection = DriverManager.getConnection(DB_URL, user, password);

        } catch (SQLException e) {
            System.err.println("couldn't connect to database");
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    /**
     * Disconnect from database, only call after having used connect().
     */
    public static void disconnect() {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Simple function to determine whether CPF is registered.
     *
     * @param cpf 11 character, digit only CPF
     * @return true if the CPF is already registered, false otherwise
     */
    public static boolean isCpfRegistered(String cpf) {
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(
                    "SELECT COUNT(cpf) FROM Usuario WHERE cpf = '" + cpf + "';"
            );
            results.first();
            int quantity = results.getInt("COUNT(cpf)");

            // cleanup
            results.close();
            statement.close();
            return quantity >= 1;
        } catch (SQLException e) {
            // This function should never fail.
            throw new RuntimeException(e);
        }
    }

    /**
     * Insert a user into the relevant database table. Useful for sign-up.
     *
     * @param user The user object containing the relevant user information for sign-up,
     * @throws SQLException It is possible that the value can't be inserted for various reasons. If the update fails,
     * an exception is thrown.
     */
    public static void addUser(Usuario user) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
                "INSERT INTO Usuario VALUES ('" +
                        user.getCPF() + "', '" +
                        user.getNome() + "', '" +
                        user.getSobrenome() + "', '" +
                        user.getEmail() + "', '" +
                        user.getSenha() + "', '" +
                        (user.isAdmin() ? 1 : 0) + "');"
        );
        statement.close();
    }

    /**
     * Removes a database user based on the CPF primary key. No operation happens if
     * the user does not exist.
     * @param cpf The CPF to delete.
     */
    public static void removeUser(String cpf) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "DELETE FROM Usuario WHERE cpf = '" + cpf + "';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Given a user object, update the database entry according to the CPF
     * stored in that object. All fields/columns are updated using the information
     * in the object.
     * No operation happens if the user does not exist.
     * @param user The user object for updating.
     */
    public static void updateUser(Usuario user) {
        try {
            String updateString = "UPDATE Usuario SET ";
            updateString += "nome = '" + user.getNome() + "', ";
            updateString += "sobrenome = '" + user.getSobrenome() + "', ";
            updateString += "email = '" + user.getEmail() + "', ";
            updateString += "senha = '" + user.getSenha() + "' ";
            updateString += "WHERE cpf = '" + user.getCPF() + "';";

            Statement statement = connection.createStatement();
            statement.executeUpdate(updateString);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a user object based on the data provided by the database according to
     * the given CPF.
     * @param cpf CPF of the user to retrieve.
     * @return A compliant Usuario object if the user exists in the database, null otherwise.
     */
    public static Usuario retrieveUser(String cpf) {
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
