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
                "12:15:01", "00:40:00", 5.13, 187);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    public void insertProfile() throws SQLException {
//        DataStorer.insertProfile(profile1);
//        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
//
//        assertTrue(profile1.equals(loadedProfile));
    }

    @Test
    public void insertActivity() throws SQLException {
        // Use the profile stored in the database in the @BeforeClass
        //DataStorer.insertActivity(activity1, profile2);
        //Activity loadedActivity = DataLoader.loadActivity(activity1.getFirstName(), profile2.getLastName());
        // TODO: 31/08/18  
        //assertTrue(activity1.equals(loadedActivity));
    }

    @Test
    public void insertGoal() {
        // Use the profile stored in the database in the @BeforeClass
//        DataStorer.insertGoal(goal1, profile2);
//        Profile loadedProfile = DataLoader.loadProfile(profile2.getFirstName(), profile2.getLastName());
        // TODO: 31/08/18 
//        assertTrue(profile2.equals(loadedProfile));
    }

    @Test
    public void insertDataRow() {
        //fail("not implemented");
        // TODO: 31/08/18  
    }

    @Test
    public void deleteProfile() throws SQLException {
        // Insert a profile
        Profile profile = new Profile("Noel", "Jean-Paul", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);

        // Delete the profile and attempt to load it
        DataStorer.deleteProfile(profile);
        Profile loaded = DataLoader.loadProfile(profile.getFirstName(), profile.getLastName());

        assertEquals(null, loaded);
    }

    @Test
    public void deleteActivity_checkActivityGone() throws SQLException {
        Profile profile = new Profile("Ben", "Kenobi", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);

        // Insert an activity for the profile
        Activity activity = new Activity("Walk in the woods", "2019-08-30", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 187);
        DataStorer.insertActivity(activity, profile);

        // Delete the activty and load the profile
        DataStorer.deleteActivity(activity, profile);
        Profile loadedProfile = DataLoader.loadProfile(profile.getFirstName(), profile.getLastName());

        // Check the activty was not loaded
        assertEquals(0, loadedProfile.getActivityList().size());
    }

    @Test
    public void deleteActivity_checkAllDataRowsDeleted() throws SQLException {
        Profile profile = new Profile("Ben", "Solo", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);

        // Insert an activity for the profile
        Activity activity = new Activity("Walk in the woods", "2019-08-30", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 187);
        DataStorer.insertActivity(activity, profile);

        // Delete the activty
        DataStorer.deleteActivity(activity, profile);

        // Readd the activity so the dataRows can be accessed
        DataStorer.insertActivity(activity, profile);

        // Reload the profile
        Profile loadedProfile = DataLoader.loadProfile(profile.getFirstName(), profile.getLastName());

        // Check the activities dataRows have been deleted
        assertEquals(0, loadedProfile.getActivityList().get(0).getRawData().size());
    }

    @Test
    public void deleteGoal() throws SQLException {
        Profile profile = new Profile("Phoebe", "B", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);

        Goal goal = new Goal(1, 55, GoalType.Walk, "2018-03-20", "2020-01-01",
                2.00, 0);
        DataStorer.insertGoal(goal, profile);

        DataStorer.deleteGoal(goal, profile);
        Profile loadedProfile = DataLoader.loadProfile(profile.getFirstName(), profile.getLastName());

        assertEquals(0, loadedProfile.getGoalList().size());
    }

    @Test
    public void deleteDataRow() throws  SQLException {
        Profile profile = new Profile("N", "B", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);

        Activity activity = new Activity("Run in the Woods", "2019-08-30", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 187);
        DataStorer.insertActivity(activity, profile);

        DataRow row3 = new DataRow(2, "2018-07-18", "14:02:25", 182, -87.01902489,
                178.4352, 203);
        DataStorer.insertDataRow(row3, activity);

        DataStorer.deleteDataRow(row3, activity);
        Profile loadedProfile = DataLoader.loadProfile(profile.getFirstName(), profile.getLastName());

        System.out.println(loadedProfile.getActivityList());

        assertEquals(0, loadedProfile.getActivityList().get(0).getRawData().size());
    }
}