package seng202.team4.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class DataAccesser {
    static Connection connection;

    /** Initialise a connection to a the database.
     *
     * @param url the url of the database to connect to
     */
    public static void initialiseConnection(String url) {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
