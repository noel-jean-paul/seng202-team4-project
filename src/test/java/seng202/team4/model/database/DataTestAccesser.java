package seng202.team4.model.database;

import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class DataTestAccesser extends DataAccesser {
    /**
     * Initialise the connection to the test database
     * @throws SQLException if the database connection fails
     */
    public static void initialiseConnection() throws SQLException {
        String url = "jdbc:sqlite:testDatabase.sqlite";
        connection = DriverManager.getConnection(url);
    }
}
