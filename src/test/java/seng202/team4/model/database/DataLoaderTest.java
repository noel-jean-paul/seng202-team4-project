package seng202.team4.model.database;

import org.junit.*;
import seng202.team4.model.data.*;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.GoalType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataLoaderTest {
    private static Profile profile1;
    private static Profile profile2;
    private static Activity activity1;
    private static Activity activity2;
    private static Goal goal1;
    private static Goal goal2;
    private static Goal goal3;
    private static Goal goal4;
    private static DataRow row1;
    private static DataRow row2;

    @BeforeClass
    public static void setUp() throws SQLException {
        // Initialise the database connection for the other classes
        DataAccesser.initialiseTestConnection();

        // Remove all data from the database
        DataAccesser.clearDatabase();

        // Create test DataRows
        row1 = new DataRow(1, "2018-07-18", "14:02:20", 182, -87.01902489,
                178.4352, 203);


        row2 = new DataRow(2, "2018-07-18", "14:02:25", 182, -87.01902489,
                178.4352, 203);

        // create test Activities
        activity1 = new Activity("Run in the park", "2018-08-29", ActivityType.Run,
                "12:15:01", "PT40M", 5.13, 187);

        activity2 = new Activity("Walk around the block", "2018-09-01",
                ActivityType.Walk, "01:28:30", "PT11M19S", 1.2, 30);

        // Create test Goals
        goal1 = new Goal(1, 55, GoalType.Walk, "2018-03-20", "2020-01-01",
                 "PT0M");

        goal2 = new Goal(2, 100, GoalType.Run, "2017-05-21", "2020-01-02",
               "PT60M");

        goal3 = new Goal(3, 55, GoalType.Walk, "2018-03-20", "2020-01-01",
                10);

        goal4 = new Goal(4, 100, GoalType.Run, "2017-05-21", "2020-01-02",
                5.0);

        // Create test profiles
        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);

        profile2 = new Profile("Matthew", "Michewski", "1997-06-23", 76,
                1.85);
    }

    @Before
    public void setUpReccurring() throws SQLException {
        // Insert objects
        DataStorer.insertProfile(profile1);
        DataStorer.insertProfile(profile2);

        profile1.addActivity(activity1);
        profile1.addActivity(activity2);

        activity1.addDataRow(row1);
        activity1.addDataRow(row2);
        activity2.addDataRow(row1);
        activity2.addDataRow(row2);

        profile1.addCurrentGoal(goal1);
        profile1.addCurrentGoal(goal2);

        profile1.addPastGoal(goal3);
        profile1.addPastGoal(goal4);
    }

    @After
    public void tearDownReccurring() throws SQLException {
        DataAccesser.clearDatabase();

        // Clear lists
        activity1.getRawData().clear();
        activity2.getRawData().clear();

        profile1.getActivityList().clear();
        profile1.getCurrentGoals().clear();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        DataAccesser.clearDatabase();
        DataAccesser.closeDatabase();
    }

    @Test
    public void loadProfile_allListsFilled_checkFullProfileLoaded() throws SQLException {
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(profile1, loadedProfile);
    }

    @Test
    public void loadProfile_allListsFilled_checkActivityOwner() throws SQLException {
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(profile1, loadedProfile.getActivityList().get(0).getOwner());
    }

    @Test
    public void loadProfile_allListsFilled_checkGoalOwner() throws SQLException {
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(profile1, loadedProfile.getActivityList().get(0).getOwner());
    }

    @Test
    public void loadProfile_allListsFilled_checkDataRowOwner() throws SQLException {
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(activity2, loadedProfile.getActivityList().get(0).getRawData().get(0).getOwner());
    }

    @Test
    public void loadProfile_allListsEmpty() throws SQLException {
        // Create a new profile with its goal/activity lists empty
        Profile profile = new Profile("Ghengis", "Khan", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);
        Profile loadedProfile = DataLoader.loadProfile(profile.getFirstName(), profile.getLastName());
        assertEquals(profile, loadedProfile);
    }

    @Test
    public void loadProfile_profileDoesNotExist() throws SQLException {
        Profile loadedProfile = DataLoader.loadProfile("@#&*@", "$#&*$*#");
        assertEquals(null, loadedProfile);
    }

    @Test
    public void loadProfile_checkPictureURL() throws SQLException {
        // Create a profile with the non-default URL
        Profile profile = new Profile("Ghengis", "Khan", "1998-03-06", 85.0,
                1.83, "/images/Ghengis-Khan-icon.png");
        DataStorer.insertProfile(profile);
        Profile loadedProfile = DataLoader.loadProfile(profile.getFirstName(), profile.getLastName());

        assertEquals(profile, loadedProfile);
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

        assertEquals(expectedKeys, profileKeys);
    }
}