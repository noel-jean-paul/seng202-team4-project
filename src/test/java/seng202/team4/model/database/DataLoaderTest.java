package seng202.team4.model.database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import seng202.team4.model.data.Profile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DataLoaderTest {
    private static Connection connection;


    @Before
    public void setUp() throws SQLException {
        String url = "jdbc:sqlite:fitness_tracker.sqlite";
        connection = DriverManager.getConnection(url);

        // initialise DataStorer and DataLoader connections to the database
        DataStorer.initialiseConnection(url);
        DataLoader.initialiseConnection(url);

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

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void loadProfile() throws SQLException {
        String firstName = "Noel";
        String lastName = "Bisson";
        Profile profile = new Profile(firstName, lastName, "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);

        Profile loadedProfile = DataLoader.loadProfile(firstName, lastName);
        assertTrue(profile.equals(loadedProfile));
    }

    @Test
    public void fetchAllProfileNames() {
    }


    @Test
    public void fetchAllActivityKeys() {
    }
}