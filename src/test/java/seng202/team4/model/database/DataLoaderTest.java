package seng202.team4.model.database;

import org.junit.*;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.data.utilities.ProfileKey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataLoaderTest extends DataAccesser {
    private static Profile profile1;
    private static Profile profile2;
    private static Activity activity1;
    private static Goal goal1;
    private static DataRow row1;

    @BeforeClass
    public static void setUp() throws SQLException {
        // Initialise the database connection for the other classes
        DataAccesser.initialiseConnection();

        // Remove all data from the database
        DataTestHelper.clearDatabase();

        // Insert test profiles
        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile1);

        profile2 = new Profile("Matthew", "Michewski", "1997-06-23", 76,
                1.85);
        DataStorer.insertProfile(profile2);

        // Insert test Activities
        activity1 = new Activity("Run in the park", "2018-08-29", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 18, 7.7, null);
        DataStorer.insertActivity(activity1, profile1);

        // Insert test Goals
        goal1 = new Goal(1, 55, GoalType.Walk, "2018-03-20", "2020-01-01",
                "2019-01-15", "Go for a walk", 2.00, 0);
        DataStorer.insertGoal(goal1, profile1);

        // Insert test DataRows
        row1 = new DataRow(1, "2018-07-18", "14:02:20", 182, -87.01902489,
                178.4352, 203);
        DataStorer.insertDataRow(row1, activity1);
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

//    @Test
//    public void loadActivity() throws SQLException {
//        Activity loadedActivity = DataLoader.loadActivity(activity1.getName(), activity1.getDate(), profile1);
//        assertTrue(activity1.equals(loadedActivity));
//    }
//
//    @Test
//    public void loadGoal() throws SQLException {
//        Goal loadedGoal = DataLoader.loadGoal(goal1.getNumber(), profile1);
//        assertTrue(goal1.equals(loadedGoal));
//    }
//
//    @Test
//    public void loadDataRow() throws SQLException {
//        DataRow loadedDataRow = DataLoader.loadDataRow(row1.getNumber(), activity1);
//        assertTrue(row1.equals(loadedDataRow));
//    }

    @Test
    public void fetchAllProfileKeys() throws SQLException {
        // Get the returned list
        List<ProfileKey> profileKeys = DataLoader.fetchAllProfileKeys();

        // Create the expected list
        List<ProfileKey> expectedKeys = new ArrayList<>();
        expectedKeys.add(new ProfileKey(profile1.getFirstName(), profile1.getLastName()));
        expectedKeys.add(new ProfileKey(profile2.getFirstName(), profile2.getLastName()));
        java.util.Collections.sort(expectedKeys);

        assertEquals(profileKeys, expectedKeys);
    }


    @Test
    public void fetchAllActivityKeys() {
    }
}