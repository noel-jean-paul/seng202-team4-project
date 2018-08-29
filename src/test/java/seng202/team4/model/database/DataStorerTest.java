package seng202.team4.model.database;

import org.junit.*;
import static org.junit.Assert.*;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ActivityType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataStorerTest extends DataAccesser {
    private static Profile profile1;
    private static Profile profile2;
    private static Activity activity1;


    @BeforeClass
    public static void setUp() throws SQLException {
        // Initialise the database connection
        DataAccesser.initialiseConnection();

        // Remove all data from the database
        DataTestHelper.clearDatabase();

        // Initialise objects and insert some into the database
        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);

        profile2 = new Profile("Matthew", "Michewski", "1997-06-23", 76,
                1.85);
        DataStorer.insertProfile(profile2);

        activity1 = new Activity("Run in the park", "2018-08-29", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 18, 7.7);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void insertProfileTest() throws SQLException {
        DataStorer.insertProfile(profile1);
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertTrue(profile1.equals(loadedProfile));
    }

    @Test
    public void insertActivityTest() throws SQLException {
        // Use the profile stored in the database in the @BeforeClass
        DataStorer.insertActivity(activity1, profile2);
        //Activity loadedActivity = DataLoader.loadActivity(activity1.getFirstName(), profile2.getLastName());

        //assertTrue(activity1.equals(loadedActivity));
    }

    @Test
    public void insertGoal() {
        // Use the profile stored in the database in the @BeforeClass
//        DataStorer.insertGoal(goal1, profile2);
//        Profile loadedProfile = DataLoader.loadProfile(profile2.getFirstName(), profile2.getLastName());
//
//        assertTrue(profile2.equals(loadedProfile));
    }

    @Test
    public void insertDataRow() {
        //fail("not implemented");
    }
}