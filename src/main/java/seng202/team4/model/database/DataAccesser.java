package seng202.team4.model.database;

import java.sql.*;

abstract public class DataAccesser {
    static Connection connection;
    static ResultSet set;
    static PreparedStatement statement;

    /** Initialise the connection to a the production database.
     *  @throws SQLException if the connection could not be opened
     */
    public static void initialiseMainConnection() throws SQLException {
        String url = "jdbc:sqlite:fitness_tracker.sqlite";
        connection = DriverManager.getConnection(url);

//        // Turn foreign keys on
//        String update = "PRAGMA foreign_keys = ON;";
//        PreparedStatement statement = connection.prepareStatement(update);
//        statement.executeUpdate();
    }

    /** Initialise the connection to a the test database.
     *  @throws SQLException if the connection could not be opened
     */
    public static void initialiseTestConnection() throws SQLException {
        String url = "jdbc:sqlite:testDatabase.sqlite";
        connection = DriverManager.getConnection(url);
    }

    /** Drop all tables from both the production and test databases and
     *  re-create them.
     * @throws SQLException if an error occurred regarding the database
     */
    private static void rebuildAllDatabases() throws SQLException {
        //Rebuild the production database
        DataAccesser.initialiseMainConnection();
        DataAccesser.rebuildDatabase();
        // Rebuild the test database
        DataAccesser.initialiseTestConnection();
        DataAccesser.rebuildDatabase();
    }

    /** Drop all tables from the database referenced by connection
     *  and re-create them
     * @throws SQLException if an error occurred regarding the database
     */
    private static void rebuildDatabase() throws SQLException {
        Statement stmt = connection.createStatement();

        // Drop the tables
        String dropProfile = "drop table profile;";
        String dropActivity = "drop table activity;";
        String dropGoal = "drop table goal;";
        String dropDataRow = "drop table dataRow;";

        stmt.addBatch(dropProfile);
        stmt.addBatch(dropActivity);
        stmt.addBatch(dropGoal);
        stmt.addBatch(dropDataRow);

        // Create new tables
        String createProfile = "create table profile ("+
                "firstName text not null, " +
                "lastName text not null, " +
                "dateOfBirth character(10) not null, " +
                "height real constraint check_height check (height BETWEEN 1.00 and 3.00), " +
                "weight real constraint check_weight check (weight between 0 and 250), " +
                "primary key (firstName, lastName)" +
                ");";

        String createActivity = "create table activity ( " +
                "name text, " +
                "activityDate character(10), " +
                "description text, " +
                "type character(3) constraint check_type check (type in (\"Run\", \"Walk\", \"Other\")), " +
                "startTime character(8) not null, " +
                "duration character(8) not null, " +
                "distance real not null, " +
                "caloriesBurned integer not null, " +
                "firstName text, " +
                "lastName text, " +
                "primary key (name, activityDate, firstName, lastName), " +
                "foreign key (firstName, lastName) references profile" +
                ");";

        String createGoal = "create table goal (\n" +
                "goalNumber integer,\n" +
                "progress integer constraint check_progress check (progress between 0 and 100),\n" +
                "type character(3) constraint check_type check (type in (\"Run\", \"Walk\")),\n" +
                "description text,\n" +
                "creationDate character(10) not null,\n" +
                "expiryDate character(10),\n" +
                "completionDate character(10),\n" +
                "goalDuration character(8),\n" +
                "goalDistance real,\n" +
                "firstName text,\n" +
                "lastName text,\n" +
                "primary key (firstName, lastName, goalNumber),\n" +
                "foreign key (firstName, lastName) references profile\n" +
                "on delete cascade on update no action\n" +
                ");";

        String createDataRow = "create table dataRow (\n" +
                "  rowNumber integer,\n" +
                "  rowDate character(10),\n" +
                "  time character(8) not null,\n" +
                "  heartRate integer constraint check_heartRate check (heartRate between 60 and 250),\n" +
                "  latitude double constraint check_latitude check (latitude between -90 and 90),\n" +
                "  longitude double constraint check_longitude check (longitude between -180 and 180),\n" +
                "  elevation double constraint check_elevation check (elevation between 0 and 4000),\n" +
                "  name text,\n" +
                "  activityDate character(10),\n" +
                "  foreign key (name, activityDate) references activity,\n" +
                "  primary key (name, activityDate, rowNumber)\n" +
                ");";

        stmt.addBatch(createProfile);
        stmt.addBatch(createActivity);
        stmt.addBatch(createGoal);
        stmt.addBatch(createDataRow);

        stmt.executeBatch();

        stmt.close();
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

    /** Rebuild the databases */
    public static void main(String[] args) throws SQLException {
        DataAccesser.rebuildAllDatabases();
    }
}
