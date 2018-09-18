package seng202.team4.model.database;

import java.sql.*;

abstract public class DataAccesser {
    static Connection connection;
    static ResultSet set;
    static PreparedStatement statement;

    /** Initialise the connection to a the database.
     *  @throws SQLException if the connection could not be opened
     */
    public static void initialiseConnection() throws SQLException {
        String url = "jdbc:sqlite:fitness_tracker.sqlite";
        connection = DriverManager.getConnection(url);

//        // Turn foreign keys on
//        String update = "PRAGMA foreign_keys = ON;";
//        PreparedStatement statement = connection.prepareStatement(update);
//        statement.executeUpdate();
    }

    /**
     * Close the database connection
     * @throws SQLException if the connection could not be closed
     */
    public static void closeDatabase() throws SQLException {
        connection.close();
    }

    /** Removes all data from the database
     *
     * @throws SQLException if an error occurs regarding the database
     */
    public static void clearDatabase() throws SQLException {
        // Delete all profiles from the database
        String select = "delete from profile";
        PreparedStatement statement = connection.prepareStatement(select);
        statement.executeUpdate();

        // Delete all activities from the database
        select = "delete from activity";
        statement = connection.prepareStatement(select);
        statement.executeUpdate();

        // Delete all goals from the database
        select = "delete from goal";
        statement = connection.prepareStatement(select);
        statement.executeUpdate();

        // Delete all dataRows from the database
        select = "delete from dataRow";
        statement = connection.prepareStatement(select);
        statement.executeUpdate();
    }
}
