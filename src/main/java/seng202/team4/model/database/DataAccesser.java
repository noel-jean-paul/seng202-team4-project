package seng202.team4.model.database;

import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.Profile;

import java.sql.*;

/**
 * Class for establishing connection to the database.
 */
abstract public class DataAccesser {
    static Connection connection;
    static ResultSet set;
    static PreparedStatement statement;
    private static String dbFileName = "fitness_tracker.sqlite";

    /** Initialise the connection to a the production database.
     *  @throws SQLException if the connection could not be opened
     */
    public static void initialiseMainConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
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
    public static void rebuildAllDatabases() throws SQLException {
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

        // Drop the tables if they exist
        String dropProfile = "drop table if exists profile;";
        String dropActivity = "drop table if exists activity;";
        String dropGoal = "drop table if exists goal;";
        String dropDataRow = "drop table if exists dataRow;";

        stmt.addBatch(dropProfile);
        stmt.addBatch(dropActivity);
        stmt.addBatch(dropGoal);
        stmt.addBatch(dropDataRow);

        // Create new tables
        String createProfile = String.format("create table if not exists profile ("+
                "firstName text not null, " +
                "lastName text not null, " +
                "dateOfBirth character(10) not null, " +
                "height real constraint check_height check (height BETWEEN %f and %f), " +
                "weight real constraint check_weight check (weight between %f and %f), " +
                "pictureURL text not null, " +
                "primary key (firstName, lastName)" +
                ");", Profile.MIN_HEIGHT, Profile.MAX_HEIGHT, Profile.MIN_WEIGHT, Profile.MAX_WEIGHT);

        String createActivity = "create table if not exists activity ( " +
                "name text, " +
                "activityDate character(10), " +
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

        String createGoal = "create table if not exists goal (\n" +
                "goalNumber integer,\n" +
                "progress integer constraint check_progress check (progress between 0 and 100),\n" +
                "type character(3) constraint check_type check (type in (\"Run\", \"Walk\")),\n" +
                "creationDate character(10) not null,\n" +
                "expiryDate character(10),\n" +
                "completionDate character(10),\n" +
                "goalDuration character(8),\n" +
                "goalDistance real constraint check_goalDistance check (goalDistance >= 0),\n" +
                "caloriesBurned integer not null, \n" +
                "current text constratint check_current check (current in (\"true\", \"false\")), \n" +
                "firstName text,\n" +
                "lastName text,\n" +
                "primary key (firstName, lastName, goalNumber),\n" +
                "foreign key (firstName, lastName) references profile\n" +
                "on delete cascade on update no action\n" +
                ");";

        String createDataRow = String.format("create table if not exists dataRow (\n" +
                "  rowNumber integer,\n" +
                "  rowDate character(10),\n" +
                "  time character(8) not null,\n" +
                "  heartRate integer constraint check_heartRate check (heartRate between %f and %f),\n" +
                "  latitude double constraint check_latitude check (latitude between %f and %f),\n" +
                "  longitude double constraint check_longitude check (longitude between %f and %f),\n" +
                "  elevation double constraint check_elevation check (elevation between %f and %f),\n" +
                "  name text,\n" +
                "  activityDate character(10),\n" +
                "  firstName text not NULL,\n" +
                "  lastName text not null," +
                "  foreign key (name, activityDate) references activity,\n" +
                "  foreign key (firstName, lastName) references Profile, \n" +
                "  primary key (firstName, lastName, name, activityDate, rowNumber)\n" +
                ");", DataRow.minHeartRate, DataRow.maxHeartRate, DataRow.minLatitude, DataRow.maxLatitude,
                DataRow.minLongitude, DataRow.maxLongitude, DataRow.minElevation, DataRow.maxElevation);

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
        statement = connection.prepareStatement(select);
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

        statement.close();
    }

    /** Clear contents of all non-profile tables
     *
     * @throws SQLException if an error occurred regarding the database
     */
    public static void clearNonProfileData() throws SQLException {
        DataAccesser.initialiseMainConnection();
        // Delete all activities from the database
        String select = "delete from activity";
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

        // Cleanup
        statement.close();
        connection.close();
    }

    /** Get the url to the main database
     *
     * @return the url to the main database as a string
     */
    public static String getDbFileName() {
        return dbFileName;
    }
}
