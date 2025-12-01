package com.user.fmuser.models;

import com.user.fmuser.models.Location.LocationType;

import java.sql.*;

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
        disconnect();
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
            if (review.tipoAlvo == Avaliacao.TargetType.Ciclovia) {
                targetTable = "Ciclovia_Reclamacao";
                targetColumns = "(reclamacao, ciclovia)";
            } else if (review.tipoAlvo == Avaliacao.TargetType.Parada) {
                targetTable = "Parada_Reclamacao";
                targetColumns = "(reclamacao, parada)";
            } else {
                targetTable = "Viagem_Reclamacao";
                targetColumns = "(reclamacao, viagem)";
            }

            String preQuery = "SELECT * FROM Usuario JOIN Avaliacao " +
                    "ON Usuario.cpf = '" + review.cpfUsuario + "' AND Avaliacao.usuario = Usuario.cpf " +
                    "JOIN " + targetTable + " c ON c.reclamacao = Avaliacao.codigo;";

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
                location = new Location(result.getInt("codigo"), result.getString("localizacao"));
            }

            result.close();
            statement.close();

            if (type == LocationType.Parada && location != null) {
                location = new Parada(location.codigo, location.localizacao);
            } else if (location != null) {
                location = new Ciclovia(location.codigo, location.localizacao);
            }

            return location;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
}
