package seng202.team4.model.database;

import org.junit.*;
import static org.junit.Assert.*;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.GoalType;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataStorerTest extends DataTestAccesser {
    private static Profile profile1;
    private static Profile profile2;
    private Profile loadedProfile;
    private static Activity activity1;
    private static Goal goal1;
    private static DataRow row1;


    @BeforeClass
    public static void setUp() throws SQLException {
        // Initialise the database connection
        DataTestAccesser.initialiseConnection();

        // Remove all data from the database
        DataTestHelper.clearDatabase();

        // Initialise objects and insert some into the database
        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);

        activity1 = new Activity("Run in the park", "2018-08-29", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 187);

        goal1 = new Goal(1, 55, GoalType.Walk, "2018-03-20", "2020-01-01",
                2.00, 0);

        row1 = new DataRow(1, "2018-07-18", "14:02:20", 182, -87.01902489,
                178.4352, 203);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        DataTestAccesser.closeDatabase();
    }

    @Before
    public void setUpReccurring() throws SQLException {
        DataTestHelper.clearDatabase();
        profile1.getActivityList().clear();
        profile1.getGoalList().clear();
        activity1.getRawData().clear();
    }


    @Test
    public void insertProfile() throws SQLException {
        DataStorer.insertProfile(profile1);
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(loadedProfile, profile1);
    }

    @Test
    public void insertActivity() throws SQLException {
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(profile1, loadedProfile);
    }

    @Test
    public void insertGoal() throws SQLException {
        DataStorer.insertProfile(profile1);
        profile1.addGoal(goal1);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(profile1, loadedProfile);
    }

    @Test
    public void insertDataRow() throws SQLException {
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(profile1, loadedProfile);
    }

    @Test
    public void deleteProfile_checkProfileDeleted() throws SQLException {
        // Insert a profile
        Profile profile = new Profile("Noel", "Jean-Paul", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);

        // Delete the profile and attempt to load it
        DataStorer.deleteProfile(profile);
        Profile loaded = DataLoader.loadProfile(profile.getFirstName(), profile.getLastName());

        assertNull(loaded);
    }

    @Test
    public void deleteProfile_checkActivitiesDeleted() throws SQLException {
        // Insert a profile
        Profile profile = new Profile("Noel", "Jean-Paul", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);

        // Add 2 activities to the profile
        Activity activity2 = new Activity("Jog", "2018-08-29", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 187);
        Activity activity3 = new Activity("Other", "2018-08-29", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 187);

        profile.addActivity(activity2);
        profile.addActivity(activity3);

        // Delete the profile
        DataStorer.deleteProfile(profile);

        // Reinsert the profile so the activities can be accessed
        DataStorer.insertProfile(profile);

        // Load the profile
        Profile loaded = DataLoader.loadProfile(profile.getFirstName(), profile.getLastName());

        // Check the activities have been removed from the database
        assertEquals(0, loaded.getActivityList().size());
    }

    @Test
    public void deleteProfile_checkGoalsDeleted() throws SQLException {
        // Insert a profile
        Profile profile = new Profile("Bis", "Jean-Paul", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);

        // Add 2 goals to the profile
        Goal goal2 = new Goal(1, 55, GoalType.Walk, "2018-03-20", "2020-01-01",
                2.00, 0);
        Goal goal3 = new Goal(2, 100, GoalType.Run, "2017-05-21", "2020-01-02",
                5.00, 60);

        profile.addGoal(goal2);
        profile.addGoal(goal3);

        // Delete the profile
        DataStorer.deleteProfile(profile);

        // Reinsert the profile so the goals can be accessed
        DataStorer.insertProfile(profile);

        // Load the profile
        Profile loaded = DataLoader.loadProfile(profile.getFirstName(), profile.getLastName());

        // Check the goals have been removed from the database
        assertEquals(0, loaded.getGoalList().size());
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

        assertEquals(0, loadedProfile.getActivityList().get(0).getRawData().size());
    }
}