package seng202.team4.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class DataAccesser {
    static Connection connection;

    /** Initialise the connection to a the database.
     *
     */
    public static void initialiseConnection() throws SQLException {
        String url = "jdbc:sqlite:fitness_tracker.sqlite";
        connection = DriverManager.getConnection(url);
    }
}
