package seng202.team4.model.database;

import org.junit.*;
import static org.junit.Assert.*;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.GoalType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataStorerTest extends DataAccesser {
    private static Profile profile1;
    private static Profile profile2;
    private Profile loadedProfile;
    private static Activity activity1;
    private static Goal goal1;
    private static DataRow row1;


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
                "12:15:01", "00:40:00", 5.13, 187);

        goal1 = new Goal(1, 55, GoalType.Walk, "2018-03-20", "2020-01-01",
                2.00, 0);

        row1 = new DataRow(1, "2018-07-18", "14:02:20", 182, -87.01902489,
                178.4352, 203);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void insertProfile() throws SQLException {
        DataStorer.insertProfile(profile1);
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(loadedProfile, profile1);
    }

    @Test
    public void insertActivity() throws SQLException {
        // Use the profile stored in the database in the @BeforeClass
        DataStorer.insertActivity(activity1, profile1);
        profile1.addActivity(activity1);
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(profile1, loadedProfile);
    }

    @Test
    public void insertGoal() throws SQLException {
        // Use the profile stored in the database in the @BeforeClass
        DataStorer.insertGoal(goal1, profile1);
        profile1.addGoal(goal1);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(profile1, loadedProfile);
    }

    @Test
    public void insertDataRow() throws SQLException {
        DataStorer.insertDataRow(row1, activity1);
        activity1.addDataRow(row1);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(profile1, loadedProfile);
    }
}