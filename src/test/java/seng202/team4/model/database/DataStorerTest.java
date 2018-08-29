package seng202.team4.model.database;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.Profile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataStorerTest {
    private static Connection connection;
    Profile profile;
    Activity activity;


    @BeforeClass
    public static void setUp() throws SQLException {
        String url = "jdbc:sqlite:fitness_tracker.sqlite";
        connection = DriverManager.getConnection(url);

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

    @Test
    public void insertProfileTest() {
        //fail("not implemented");
    }

    @Test
    public void insertActivityTest() throws SQLException {
//        Activity activity = new Activity("Run in the park", "2018-08-29", "", ActivityType.Run,
//                "12:15:01", "00:40:00", 5.13, 18, 7.7);
//
//        insertActivity(activity, profile);
    }

    @Test
    public void insertGoal() {
        //fail("not implemented");
    }

    @Test
    public void insertDataRow() {
        //fail("not implemented");
    }

    @AfterClass
    public void tearDown() throws SQLException {
        connection.close();
    }
}