package seng202.team4.model.database;

import org.junit.*;
import seng202.team4.model.data.Profile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DataLoaderTest {
    private static Connection connection;
    private static Profile profile1;
    private static Profile profile2;

    @BeforeClass
    public static void setUp() throws SQLException {
        String url = "jdbc:sqlite:fitness_tracker.sqlite";
        connection = DriverManager.getConnection(url);

        // Initialise the database connection for the other classes
        DataStorer.initialiseConnection(url);
        DataLoader.initialiseConnection(url);
        DataTestHelper.initialiseConnection(url);

        // Remove all data from the database
        DataTestHelper.clearDatabase();

        // Insert needed data into the database
        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile1);

        profile2 = new Profile("Matthew", "Michewski", "1997-06-23", 76,
                1.85);
        DataStorer.insertProfile(profile2);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void loadProfile() throws SQLException {
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertTrue(profile1.equals(loadedProfile));
    }

    @Test
    public void fetchAllProfileNames() {
    }


    @Test
    public void fetchAllActivityKeys() {
    }
}