package com.user.fmuser.models;

import com.user.fmuser.models.Location.LocationType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Database {
    // Database URL
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/FeedMobiDB";
    // Database connection handler
    private static Connection connection;

    /**
     * Main function for testing purposes only.
     *
     * @param args Test function args
     */
    public static void main(String[] args) {
        connect();
        fillTables();
        disconnect();
    }

    private static void fillTables() {
        Parada p1 = new Parada("A");
        Parada p2 = new Parada("B");
        Parada p3 = new Parada("C");
        Parada p4 = new Parada("D");
        Parada p5 = new Parada("E");

        addLocation(p1);
        addLocation(p2);
        addLocation(p3);
        addLocation(p4);
        addLocation(p5);

        p1 = (Parada) retrieveLocation(p1);
        p2 = (Parada) retrieveLocation(p2);
        p3 = (Parada) retrieveLocation(p3);
        p4 = (Parada) retrieveLocation(p4);
        p5 = (Parada) retrieveLocation(p5);

        Ciclovia c1 = new Ciclovia("A");
        Ciclovia c2 = new Ciclovia("B");
        Ciclovia c3 = new Ciclovia("C");
        Ciclovia c4 = new Ciclovia("D");
        Ciclovia c5 = new Ciclovia("E");

        addLocation(c1);
        addLocation(c2);
        addLocation(c3);
        addLocation(c4);
        addLocation(c5);

        c1 = (Ciclovia) retrieveLocation(c1);
        c2 = (Ciclovia) retrieveLocation(c2);
        c3 = (Ciclovia) retrieveLocation(c3);
        c4 = (Ciclovia) retrieveLocation(c4);
        c5 = (Ciclovia) retrieveLocation(c5);

        Percurso pc1 = new Percurso(p1, p2);
        Percurso pc2 = new Percurso(p2, p4);
        Percurso pc3 = new Percurso(p1, p5);
        Percurso pc4 = new Percurso(p3, p2);
        Percurso pc5 = new Percurso(p4, p1);

        addRoute(pc1);
        addRoute(pc2);
        addRoute(pc3);
        addRoute(pc4);
        addRoute(pc5);

        pc1 = retrieveRoute(pc1);
        pc2 = retrieveRoute(pc2);
        pc3 = retrieveRoute(pc3);
        pc4 = retrieveRoute(pc4);
        pc5 = retrieveRoute(pc5);

        Usuario u1 = new Usuario("00000000000", "Adécio", "de Almeida", "a@a.a", "a");
        Usuario u2 = new Usuario("00000000001", "Bernardo", "Bernardes", "b@b.b", "b");
        Usuario u3 = new Usuario("00000000002", "Caio", "Castro", "c@c.c", "c");
        Usuario u4 = new Usuario("00000000003", "Dênis", "Dorival", "d@d.d", "d");
        Usuario u5 = new Usuario("00000000004", "Eliane", "Elis", "e@e.e", "e");
        Usuario adm = new Usuario("11111111111", "Nirva", "Neves", "n@n.n", "n");
        adm.setIsAdmin(true);

        try {
            addUser(u1);
            addUser(u2);
            addUser(u3);
            addUser(u4);
            addUser(u5);
            addUser(adm);
        } catch (SQLException e) {
            System.err.println("Users already registered");
        }

        HorarioDiaPercurso hdp1 = new HorarioDiaPercurso(Time.valueOf("12:00:00"), "seg", pc1);
        HorarioDiaPercurso hdp2 = new HorarioDiaPercurso(Time.valueOf("13:00:00"), "ter", pc1);
        HorarioDiaPercurso hdp3 = new HorarioDiaPercurso(Time.valueOf("14:00:00"), "qua", pc3);
        HorarioDiaPercurso hdp4 = new HorarioDiaPercurso(Time.valueOf("09:00:00"), "sex", pc5);
        HorarioDiaPercurso hdp5 = new HorarioDiaPercurso(Time.valueOf("13:00:00"), "seg", pc2);

        addHDP(hdp1);
        addHDP(hdp2);
        addHDP(hdp3);
        addHDP(hdp4);
        addHDP(hdp5);

        hdp1 = retrieveHDP(hdp1);
        hdp2 = retrieveHDP(hdp2);
        hdp3 = retrieveHDP(hdp3);
        hdp4 = retrieveHDP(hdp4);
        hdp5 = retrieveHDP(hdp5);
    }

    /**
     * Function to check for the validity of a CPF.
     *
     * @param cpf The CPF to check.
     * @return true if valid, false otherwise.
     */
    public static boolean validCPF(String cpf) {
        return isAllDigits(cpf) && cpf.length() == 11;
    }

    /**
     * Check if a weekday string is valid.<br>
     * A valid weekday is one of: "seg", "ter", "qua", "qui", "sex", "sab", "dom".
     *
     * @param dia The weekday to check
     * @return true if weekday valid, false otherwise
     */
    public static boolean validDay(String dia) {
        return dia.compareTo("seg") == 0
                || dia.compareTo("ter") == 0
                || dia.compareTo("qua") == 0
                || dia.compareTo("qui") == 0
                || dia.compareTo("sex") == 0
                || dia.compareTo("sab") == 0
                || dia.compareTo("dom") == 0;
    }

    private static boolean isAllDigits(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for a valid vehicle plate string.
     *
     * @param plate The plate to check.
     * @return true if valid, false otherwise.
     */
    public static boolean validPlate(String plate) {
        if (plate == null || plate.length() != 7) {
            return false;
        }
        char[] b = plate.toCharArray();
        for (int i = 0; i < 2; ++i) {
            if (!Character.isUpperCase(b[i]) || !Character.isAlphabetic(b[i])) {
                return false;
            }
        }
        if (!Character.isDigit(b[3])) {
            return false;
        }
        if (!Character.isAlphabetic(b[4]) || !Character.isUpperCase(b[4])) {
            return false;
        }
        return Character.isDigit(b[5]) && Character.isDigit(b[6]);
    }

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
     * @throws SQLException It is possible that the value can't be inserted for various reasons.
     *                      If the update fails, an exception is thrown.
     */
    public static void addUser(Usuario user) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
                "INSERT INTO Usuario VALUES ('" +
                        user.getCPF() + "', '" +
                        user.nome + "', '" +
                        user.sobrenome + "', '" +
                        user.email + "', '" +
                        user.senha + "', '" +
                        (user.isAdmin() ? 1 : 0) + "');"
        );
        // Cleanup
        statement.close();
    }

    /**
     * Removes a database user based on the CPF primary key. No operation happens if
     * the user does not exist.
     *
     * @param cpf The CPF to delete.
     */
    public static void removeUser(String cpf) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "DELETE FROM Usuario WHERE cpf = '" + cpf + "';");

            // Cleanup
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Given a user object, update the database entry according to the CPF
     * stored in that object. All fields/columns are updated using the information
     * in the object.
     * No operation happens if the user does not exist.
     *
     * @param user The user object for updating.
     */
    public static void updateUser(Usuario user) {
        try {
            String updateString = "UPDATE Usuario SET ";
            updateString += "nome = '" + user.nome + "', ";
            updateString += "sobrenome = '" + user.sobrenome + "', ";
            updateString += "email = '" + user.email + "', ";
            updateString += "senha = '" + user.senha + "' ";
            updateString += "WHERE cpf = '" + user.getCPF() + "';";

            Statement statement = connection.createStatement();
            statement.executeUpdate(updateString);

            // Cleanup
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a user object based on the data provided by the database according to
     * the given CPF.
     *
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

                // Cleanup
                results.close();
                statement.close();
                return usuario;
            } else {
                // Cleanup
                results.close();
                statement.close();
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Given a review object type, add this review to the database.
     * Will fail if this user has already made a review for that entity.
     *
     * @param review The review to add.
     * @return true if the review was added, false otherwise.
     */
    public static boolean addReview(Avaliacao review) {
        try {
            // check if user has already reviewed this.
            Statement preStatement = connection.createStatement();

            String targetTable;
            String targetColumns;
            String targetName;
            if (review.tipoAlvo == Avaliacao.TargetType.Ciclovia) {
                targetTable = "Ciclovia_Reclamacao";
                targetColumns = "(reclamacao, ciclovia)";
                targetName = "ciclovia";
            } else if (review.tipoAlvo == Avaliacao.TargetType.Parada) {
                targetTable = "Parada_Reclamacao";
                targetColumns = "(reclamacao, parada)";
                targetName = "parada";
            } else {
                targetTable = "Viagem_Reclamacao";
                targetColumns = "(reclamacao, viagem)";
                targetName = "viagem";
            }

            String preQuery = "SELECT * FROM Usuario JOIN Avaliacao " +
                    "ON Usuario.cpf = '" + review.cpfUsuario + "' AND Avaliacao.usuario = Usuario.cpf " +
                    "JOIN " + targetTable + " c ON c.reclamacao = Avaliacao.codigo AND c." + targetName + " = " + review.codigoAlvo +";";

            ResultSet results = preStatement.executeQuery(preQuery);

            // Any result existing means the user has already made a review for this entity.
            if (results.first()) {
                return false;
            }

            Statement statement = connection.createStatement();
            String update = "INSERT INTO Avaliacao (texto, usuario, nota) " +
                    "VALUES ('" + review.texto + "', '" + review.cpfUsuario + "', " + review.nota + ")"
                    + ";";

            statement.executeUpdate(update);
            statement.close();

            // Retrieve code of created rating
            Statement querystatement = connection.createStatement();
            ResultSet result = querystatement.executeQuery("SELECT codigo FROM Avaliacao WHERE " +
                    "texto = '" + review.texto + "' AND " +
                    "usuario = '" + review.cpfUsuario
                    + "';");
            result.first();
            int ratingCode = result.getInt("codigo");
            result.close();
            querystatement.close();

            Statement statement1 = connection.createStatement();
            String query = "INSERT INTO " + targetTable + " " + targetColumns + " VALUES (" +
                    ratingCode + ", " +
                    review.codigoAlvo + ");";

            statement1.executeUpdate(query);
            statement1.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Avaliacao> retrieveReviews() {
        try {
            ArrayList<Avaliacao> avaliacoes = new ArrayList<>();

            Statement statement1 = connection.createStatement();
            String query = "SELECT * FROM Avaliacao JOIN Parada_Reclamacao ON " +
                    "Parada_Reclamacao.reclamacao = Avaliacao.codigo";
            ResultSet results1 = statement1.executeQuery(query);

            while (results1.next()) {
                Avaliacao temp = new Avaliacao(results1.getInt("Parada_Reclamacao.parada"), Avaliacao.TargetType.Parada,
                        results1.getInt("Avaliacao.nota"), results1.getString("Avaliacao.texto"), results1.getString("Avaliacao.usuario"));
                avaliacoes.add(temp);
            }

            results1.close();
            statement1.close();

            Statement statement2 = connection.createStatement();
            query = "SELECT * FROM Avaliacao JOIN Ciclovia_Reclamacao ON " +
                    "Ciclovia_Reclamacao.reclamacao = Avaliacao.codigo";
            ResultSet results2 = statement2.executeQuery(query);

            while (results2.next()) {
                Avaliacao temp = new Avaliacao(results2.getInt("Ciclovia_Reclamacao.ciclovia"), Avaliacao.TargetType.Ciclovia,
                        results2.getInt("Avaliacao.nota"), results2.getString("Avaliacao.texto"), results2.getString("Avaliacao.usuario"));
                avaliacoes.add(temp);
            }

            results2.close();
            statement2.close();

            return avaliacoes;
        } catch (SQLException e) {
            System.err.println(e.toString());
            return null;
        }
    }

    public static Avaliacao retrieveReview(int targetCode, Avaliacao.TargetType targetType, String userCpf) {
        try {
            Statement stmt = connection.createStatement();
            String targetTable;
            String targetColumns;
            String targetName;

            if (targetType == Avaliacao.TargetType.Ciclovia) {
                targetTable = "Ciclovia_Reclamacao";
                targetColumns = "(reclamacao, ciclovia)";
                targetName = "ciclovia";
            } else if (targetType == Avaliacao.TargetType.Parada) {
                targetTable = "Parada_Reclamacao";
                targetColumns = "(reclamacao, parada)";
                targetName = "parada";
            } else {
                targetTable = "Viagem_Reclamacao";
                targetColumns = "(reclamacao, viagem)";
                targetName = "viagem";
            }

            String query = "SELECT * FROM Usuario JOIN Avaliacao " +
                    "ON Usuario.cpf = Avaliacao.usuario AND Usuario.cpf = '" + userCpf + "' " +
                    "JOIN " + targetTable + " t ON t." + targetName + " = " + targetCode + " " +
                    "AND t.reclamacao = Avaliacao.codigo;";

            ResultSet result = stmt.executeQuery(query);
            Avaliacao av = null;
            if (result.next()) {
                av = new Avaliacao(targetCode, targetType, result.getInt("Avaliacao.nota"), result.getString("Avaliacao.texto"),
                        userCpf);
            }

            result.close();
            stmt.close();
            return av;
        } catch (SQLException e) {
            System.err.println(e.toString());
            return null;
        }
    }

    /**
     * Add an employee to the database using an object. Will fail if there exists an employee
     * with the same name already, even if CPF differs.
     *
     * @param employee The employee to add
     * @return true if it was possible to add the employee, false otherwise.
     */
    public static boolean addEmployee(Funcionario employee) {
        try {
            Statement statement = connection.createStatement();
            String employeeType = (employee.isMotorista) ? "Motorista" : "Cobrador";
            String update = "INSERT INTO " + employeeType + " (cpf, nome, sobrenome) VALUES ('" +
                    employee.getCpf() + "', '" + employee.nome + "', '" + employee.sobrenome + "');";
            statement.executeUpdate(update);

            statement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean removeEmployee(Funcionario employee) {
        try {
            Statement statement = connection.createStatement();
            String employeeType = (employee.isMotorista) ? "Motorista" : "Cobrador";
            String update = "DELETE FROM " + employeeType + " WHERE " +
                    employeeType + ".cpf = '" + employee.getCpf() + "';";
            int updated = statement.executeUpdate(update);

            statement.close();
            return updated > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public static ArrayList<Funcionario> retrieveEmployees() {
        try {
            ArrayList<Funcionario> funcionarios = new ArrayList<>();

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Motorista";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                Funcionario temp = new Funcionario(
                        results.getString("cpf"),
                        results.getString("nome"),
                        results.getString("sobrenome"),
                        true
                );
                funcionarios.add(temp);
            }

            results.close();
            statement.close();

            Statement statement1 = connection.createStatement();
            query = "SELECT * FROM Cobrador";
            ResultSet results1 = statement1.executeQuery(query);

            while (results1.next()) {
                Funcionario temp = new Funcionario(
                        results1.getString("cpf"),
                        results1.getString("nome"),
                        results1.getString("sobrenome"),
                        false
                );
                funcionarios.add(temp);
            }

            results1.close();
            statement1.close();

            return funcionarios;
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean updateEmployee(Funcionario employee) {
        try {
            Statement statement = connection.createStatement();
            String targetTable = (employee.isMotorista) ? "Motorista" : "Cobrador";
            String query = "UPDATE " + targetTable + " " +
                    "SET nome = '" + employee.nome + "', sobrenome = '" + employee.sobrenome + "' " +
                    "WHERE cpf = '" + employee.getCpf() +"';";

            int updated = statement.executeUpdate(query);
            statement.close();
            return updated > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Add a location (bike lane or bus stop) to the database using a Location object.
     * Will fail if there's already a location of the same type with this name in the database.
     *
     * @param location The Parada or Ciclovia object. These implement Location.
     * @return true if the addition was effected, false otherwise.
     */
    public static boolean addLocation(Location location) {
        try {
            Statement statement = connection.createStatement();
            String table = (location instanceof Parada) ? "Parada" : "Ciclovia";
            String update = "INSERT INTO " + table + " (localizacao) VALUES ('" +
                    location.localizacao + "');";
            statement.executeUpdate(update);

            statement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Add a location (bike lane or bus stop) to the database using a name and type.
     * Will fail if there's already a location of the same type with this name in the database.
     *
     * @param name The name for the location.
     * @param type The type of the location (either LocationType.Ciclovia or LocationType.Parada)
     * @return true if the addition was effected, false otherwise.
     */
    public static boolean addLocation(String name, LocationType type) {
        try {
            Statement statement = connection.createStatement();
            String table = (type == LocationType.Parada) ? "Parada" : "Ciclovia";
            String update = "INSERT INTO " + table + " (localizacao) VALUES ('" +
                    name + "');";
            statement.executeUpdate(update);

            statement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Get a location from the database using its name and type
     *
     * @param name Name of the location
     * @param type Type of the location
     * @return A valid location if it was found, null otherwise.
     */
    public static Location retrieveLocation(String name, LocationType type) {
        try {
            Statement statement = connection.createStatement();
            String table = (type == LocationType.Parada) ? "Parada" : "Ciclovia";
            String query = "SELECT * FROM " + table + " WHERE localizacao = '" + name + "';";
            ResultSet result = statement.executeQuery(query);

            Location location = null;
            if (result.first()) {
                if (type == LocationType.Parada) {
                    location = new Parada(result.getInt("codigo"), result.getString("localizacao"));
                } else {
                    location = new Ciclovia(result.getInt("codigo"), result.getString("localizacao"));
                }
            }

            result.close();
            statement.close();
            return location;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a location from the database using an object
     *
     * @param location Location object containing name.
     * @return A valid location if it was found, null otherwise.
     */
    public static Location retrieveLocation(Location location) {
        try {
            Statement statement = connection.createStatement();
            String table = (location instanceof Parada) ? "Parada" : "Ciclovia";
            String query = "SELECT * FROM " + table + " WHERE localizacao = '" + location.localizacao + "';";
            ResultSet result = statement.executeQuery(query);

            Location new_location = null;
            if (result.first()) {
                if (location instanceof Parada) {
                    new_location = new Parada(result.getInt("codigo"), result.getString("localizacao"));
                } else {
                    new_location = new Ciclovia(result.getInt("codigo"), result.getString("localizacao"));
                }
            }

            result.close();
            statement.close();
            return new_location;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve all locations (both Paradas and Ciclovias) from the database.
     *
     * @return ArrayList containing all Location objects, or null if error occurs
     */
    public static ArrayList<Location> retrieveLocations() {
        try {
            ArrayList<Location> locations = new ArrayList<>();

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Parada";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                Parada temp = new Parada(
                        results.getInt("codigo"),
                        results.getString("localizacao")
                );
                locations.add(temp);
            }

            results.close();
            statement.close();

            Statement statement1 = connection.createStatement();
            query = "SELECT * FROM Ciclovia";
            ResultSet results1 = statement1.executeQuery(query);

            while (results1.next()) {
                Ciclovia temp = new Ciclovia(
                        results1.getInt("codigo"),
                        results1.getString("localizacao")
                );
                locations.add(temp);
            }

            results1.close();
            statement1.close();

            return locations;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Receives a location object and tries to update it. The object must have its code set.
     *
     * @param location The location to update.
     * @return true if the update was possible, false otherwise
     */
    public static boolean updateLocation(Location location) {
        try {
            Statement statement = connection.createStatement();
            String table = (location instanceof Parada) ? "Parada" : "Ciclovia";
            String update = "UPDATE " + table + " SET localizacao = '" + location.localizacao + "' " +
                    "WHERE codigo = " + location.codigo + ";";
            int updatecount = statement.executeUpdate(update);

            statement.close();
            return updatecount > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Delete a location from the database. Deletion may fail if a location with
     * this code does not exist, or if the location is tied to a review.
     *
     * @param location A location object with a valid code.
     * @return true if it was possible to remove the location, false otherwise.
     */
    public static boolean removeLocation(Location location) {
        try {
            Statement statement = connection.createStatement();
            String table = (location instanceof Parada) ? "Parada" : "Ciclovia";
            String update = "DELETE FROM " + table + " WHERE codigo = " + location.codigo + ";";
            int updatecount = statement.executeUpdate(update);

            statement.close();
            return updatecount > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Add a route to the database.
     * @param route The route to add, must have its Parada fields set with their codes.
     * @return true if route was added, false otherwise.
     */
    public static boolean addRoute(Percurso route) {
        try {
            Statement statement = connection.createStatement();
            String update = "INSERT INTO Percurso (origem, destino) VALUES " +
                    "('" + route.origem.codigo + "', '" + route.destino.codigo + "');";
            statement.executeUpdate(update);

            statement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Percurso retrieveRoute(Parada origin, Parada destination) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Percurso WHERE " +
                    "Percurso.origem = " + origin.codigo + " AND " +
                    "Percurso.destino = " + destination.codigo + ";";

            ResultSet result = statement.executeQuery(query);

            Percurso percurso = null;
            if (result.first()) {
                percurso = new Percurso(result.getInt("codigo"), origin, destination);
            }

            result.close();
            statement.close();
            return percurso;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Percurso retrieveRoute(Percurso route) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Percurso WHERE " +
                    "Percurso.origem = " + route.origem.codigo + " AND " +
                    "Percurso.destino = " + route.destino.codigo + ";";

            ResultSet result = statement.executeQuery(query);

            Percurso percurso = null;
            if (result.first()) {
                percurso = new Percurso(result.getInt("codigo"), route.origem, route.destino);
            }

            result.close();
            statement.close();
            return percurso;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve all routes (Percursos) from the database.
     * Alternative version: fetches each Parada separately
     *
     * @return ArrayList containing all Percurso objects, or null if error occurs
     */
    public static ArrayList<Percurso> retrieveRoutes() {
        try {
            ArrayList<Percurso> routes = new ArrayList<>();

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Percurso";
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                int codigoPercurso = results.getInt("codigo");
                int codigoOrigem = results.getInt("origem");
                int codigoDestino = results.getInt("destino");

                // Buscar a Parada de origem
                Parada origem = retrieveParada(codigoOrigem);

                // Buscar a Parada de destino
                Parada destino = retrieveParada(codigoDestino);

                // Criar Percurso apenas se ambas as paradas foram encontradas
                if (origem != null && destino != null) {
                    Percurso temp = new Percurso(codigoPercurso, origem, destino);
                    routes.add(temp);
                }
            }

            results.close();
            statement.close();

            return routes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Helper method to fetch a Parada by its code
     */
    public static Parada retrieveParada(int code) {
        try {
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM Parada WHERE codigo = '" + code + "';";
            ResultSet result = statement.executeQuery(query);

            Parada parada = null;
            if (result.first()) {
                parada = new Parada(
                        result.getInt("codigo"),
                        result.getString("localizacao")
                );
            }
            result.close();
            statement.close();
            return parada;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean removeRoute(Percurso route) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM Percurso WHERE " +
                    "Percurso.codigo = " + route.codigo + ";";

            int updated = statement.executeUpdate(query);

            statement.close();
            return updated > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateRoute(Percurso route) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE Percurso SET " +
                    "Percurso.origem = " + route.origem.codigo + ", " +
                    "Percurso.destino = " + route.destino.codigo + " " +
                    "WHERE Percurso.codigo = " + route.codigo + ";";

            int updated = statement.executeUpdate(query);

            statement.close();
            return updated > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean addHDP(HorarioDiaPercurso hdp) {
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO Horario_dia_percurso (hora, dia, percurso) VALUES " +
                    "('" + hdp.hora + "', '" + hdp.getDia() + "', " + hdp.percurso.codigo + ");";

            int updated = statement.executeUpdate(query);

            statement.close();
            return updated > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public static HorarioDiaPercurso retrieveHDP(HorarioDiaPercurso hdp) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Horario_dia_percurso hdp WHERE " +
                    "hdp.dia = '" + hdp.getDia() + "' AND " +
                    "hdp.hora = '" + hdp.hora + "' AND " +
                    "hdp.percurso = " + hdp.percurso.codigo + ";";

            ResultSet result = statement.executeQuery(query);

            HorarioDiaPercurso new_hdp = null;
            if (result.first()) {
                new_hdp = new HorarioDiaPercurso(result.getInt("codigo"), hdp.hora, hdp.getDia(), hdp.percurso);
            }

            result.close();
            statement.close();
            return new_hdp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addTrip(Viagem trip) {
        try {
            Statement statement = connection.createStatement();
            String cobrador = (trip.cobrador == null) ? "NULL" : "'" + trip.cobrador.getCpf() + "'";
            String query = "INSERT INTO Viagem (horario_dia_percurso, motorista, cobrador, veiculo) VALUES " +
                    "(" + trip.horarioDiaPercurso.codigo + ", '" + trip.motorista.getCpf() + "', " +
                    cobrador + ", " + trip.veiculo.numero + ");";

            int updated = statement.executeUpdate(query);

            statement.close();
            return updated > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Given a few parameters, return the corresponding trip from the database.
     *
     * @param vehicleCode A vehicle code to search.
     * @param origin      Trip origin location name
     * @param destination Trip destination location name
     * @param day         Day of the week. See validDay() for details.
     * @param time        An SQL time type. You can obtain this for example with Time.valueOf("hh:mm:ss");
     * @return The regular trip code if the trip is found, -1 otherwise.
     */
    public static int retrieveTripCode(int vehicleCode, String origin, String destination, String day, Time time) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT v.codigo FROM Viagem v, Horario_dia_percurso hdp, Percurso p, Veiculo ve, Parada pa1, Parada pa2 " +
                    "WHERE ve.numero = " + vehicleCode + " AND " +
                    "v.veiculo = ve.numero AND " +
                    "v.horario_dia_percurso = hdp.codigo AND " +
                    "hdp.dia = '" + day + "' AND " +
                    "hdp.hora = '" + time + "' AND " +
                    "hdp.percurso = p.codigo AND " +
                    "pa1.localizacao = '" + origin + "' AND " +
                    "pa2.localizacao = '" + destination + "' AND " +
                    "p.origem = pa1.codigo AND " +
                    "p.destino = pa2.codigo"
                    + ";";

            ResultSet results = statement.executeQuery(query);

            int result = -1;
            if (results.first()) {
                result = results.getInt("v.codigo");
            }

            statement.close();
            results.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean removeTrip(int code) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM Viagem WHERE " +
                    "Viagem.codigo = " + code + ";";

            int updated = statement.executeUpdate(query);

            statement.close();
            return updated > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Add a vehicle to the database using an object. Will fail if there exists a vehicle
     * with the same plate already, even if CPF differs.
     *
     * @param vehicle The vehicle to add
     * @return true if it was possible to add the vehicle, false otherwise.
     */

    public static boolean addVehicle(Veiculo vehicle) {
        try {
            Statement statement = connection.createStatement();
            String updateVeiculo = "INSERT INTO Veiculo (data_validade, assentos, capacidade_em_pe) " +
                    "VALUES ('" + vehicle.dataValidade + "', " + vehicle.assentos + ", " + vehicle.capacidadeEmPe + ")";
            statement.executeUpdate(updateVeiculo);
            statement.close();

            Statement statement1 = connection.createStatement();

            // If it is a bus, insert plate information
            if (vehicle.getPlaca() != null) {
                // Recover vehicle number
                ResultSet vehicleNumber = statement1.executeQuery("SELECT LAST_INSERT_ID()");
                int idGenerated = 0;
                if (vehicleNumber.next()) {
                    idGenerated = vehicleNumber.getInt(1);
                }
                vehicleNumber.close();

                // Insert Plate into Onibus_Placa
                if (idGenerated > 0) {
                    String updatePlate = "INSERT INTO Onibus_Placa (numero, placa) " +
                            "VALUES (" + idGenerated + ", '" + vehicle.getPlaca() + "')";
                    statement1.executeUpdate(updatePlate);
                }
            }
            statement1.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static ArrayList<Veiculo> retrieveVehicles() {
        try {
            ArrayList<Veiculo> veiculos = new ArrayList<>();

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Veiculo";
            ResultSet results = statement.executeQuery(query);

            while(results.next()) {
                Veiculo temp = new Veiculo(
                        results.getInt("numero"),
                        results.getDate("data_validade"),
                        results.getInt("assentos"),
                        results.getInt("capacidade_em_pe")
                );
                veiculos.add(temp);
            }

            results.close();
            statement.close();

            Statement statement1 = connection.createStatement();
            query = "SELECT * FROM Onibus_Placa";
            ResultSet results1 = statement1.executeQuery(query);

            while(results1.next()) {
                int numero = results1.getInt("numero");
                String placa = results1.getString("placa");

                for (Veiculo veiculo : veiculos) {
                    if (veiculo.numero == numero) {
                        veiculo.setPlaca(placa);
                        break;
                    }
                }
            }

            results1.close();
            statement1.close();

            return veiculos;

        } catch (SQLException e) {
            return null;
        }
    }
}
