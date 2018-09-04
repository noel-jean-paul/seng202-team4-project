package seng202.team4.model.database;

import org.junit.*;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.data.ProfileKey;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DataLoaderTest extends DataAccesser {
    private static Profile profile1;
    private static Profile profile2;
    private static Activity activity1;
    private static Activity activity2;
    private static Goal goal1;
    private static Goal goal2;
    private static DataRow row1;
    private static DataRow row2;

    @BeforeClass
    public static void setUp() throws SQLException {
        // Initialise the database connection for the other classes
        DataAccesser.initialiseConnection();

        // Remove all data from the database
        DataTestHelper.clearDatabase();

        // Create test DataRows
        row1 = new DataRow(1, "2018-07-18", "14:02:20", 182, -87.01902489,
                178.4352, 203);


        row2 = new DataRow(2, "2018-07-18", "14:02:25", 182, -87.01902489,
                178.4352, 203);

        // create test Activities
        activity1 = new Activity("Run in the park", "2018-08-29", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 187);

        activity2 = new Activity("Walk around the block", "2018-09-01", "Quick walk",
                ActivityType.Walk, "01:28:30", "00:11:19", 1.2, 30);

        // Create test Goals
        goal1 = new Goal(1, 55, GoalType.Walk, "2018-03-20", "2020-01-01",
                2.00, 0);

        goal2 = new Goal(2, 100, GoalType.Run, "2017-05-21", "2020-01-02",
                5.00, 60);

        // Create test profiles
        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);

        profile2 = new Profile("Matthew", "Michewski", "1997-06-23", 76,
                1.85);

        // Add activities/ goals to profile1
        activity1.addAllDataRows(new ArrayList<>(Arrays.asList(row2, row1)));
        activity2.addAllDataRows(new ArrayList<>(Arrays.asList(row1, row2)));
        profile1.addActivity(activity1);
        profile1.addActivity(activity2);

        profile1.addAllGoals(new ArrayList<>(Arrays.asList(goal1, goal2)));


        // Insert objects
        DataStorer.insertProfile(profile1);
        DataStorer.insertProfile(profile2);

        DataStorer.insertActivity(activity1, profile1);
        DataStorer.insertActivity(activity2, profile1);
        DataStorer.insertDataRow(row1, activity1);
        DataStorer.insertDataRow(row2, activity1);
        DataStorer.insertDataRow(row1, activity2);
        DataStorer.insertDataRow(row2, activity2);
        DataStorer.insertGoal(goal1, profile1);
        DataStorer.insertGoal(goal2, profile1);



    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void loadProfile() throws SQLException {
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(profile1, loadedProfile);
    }


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
}