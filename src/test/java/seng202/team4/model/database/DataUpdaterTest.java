package seng202.team4.model.database;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.GoalType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DataUpdaterTest {
    private static Profile profile1;
    private static Profile  loadedProfile;

    private static Activity activity1;
    private static Activity activity2;
    private static Activity activity3;

    private static Goal goal1;
    private static Goal goal2;
    private static Goal goal3;

    private static DataRow row1;
    private static DataRow row2;

    @BeforeClass
    public static void setUp() throws SQLException {
        // initialise the database connection
        DataAccesser.initialiseTestConnection();

        // Clean the database
        DataAccesser.clearDatabase();

        activity2 = new Activity("Walk around the block", "2019-12-12",
                ActivityType.Walk, "01:28:30", "PT11M19S", 1.2, 30);

        activity3 = new Activity("Jog through Uni", "2018-12-12",
                ActivityType.Run, "01:28:30", "PT11M19S", 1.2, 30);

        // Initialise Goals
        goal1 = new Goal(1, 100, GoalType.Run,"2018-09-28", "2017-05-12",
                20, 50);
        goal2 = new Goal(2, 100, GoalType.Run,"2018-09-28", "2017-01-12",
                20, 50);
        goal3 = new Goal(3, 100, GoalType.Run,"2018-09-28", "2017-01-12",
                20, 50);

        row1 = new DataRow(1, "2018-07-18", "14:02:20", 182, -87.01902489,
                178.4352, 203);

        row2 = new DataRow(2, "2018-07-18", "14:02:30", 182, -87.01902489,
                178.4352, 203);
    }

    @Before
    public void setUpReccuring() throws SQLException {
        // Initialise profile before every test to undo changes to the primary keys. Also clears activity/goal lists
        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);

        // Initialise activity before every test to undo changes to the primary keys. Also clears raw data list
        activity1 = new Activity("Run in the park", "2017-12-12", ActivityType.Run,
                "12:15:01", "PT40M", 5.13, 187);

        // Clear database
        DataAccesser.clearDatabase();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        DataAccesser.clearDatabase();
        DataAccesser.closeDatabase();
    }

    @Test
    public void updateProfile_updateFirstName_checkActivitiesUpdated() throws SQLException {
        // Setup
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        profile1.addActivity(activity2);
        profile1.addActivity(activity3);

        // Change an aspect of the primary key
        profile1.setFirstName("Ben");

        // Check that the activities were updated
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(profile1.getActivityList(), loadedProfile.getActivityList());
    }

    @Test
    public void updateProfile_updateLastName_checkActivitiesUpdated() throws SQLException {
        // Setup
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        profile1.addActivity(activity2);
        profile1.addActivity(activity3);

        // Change an aspect of the primary key
        profile1.setLastName("Solo");

        // Check that the activities were updated
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(profile1.getActivityList(), loadedProfile.getActivityList());
    }

    @Test
    public void updateProfile_updateFirstName_checkGoalsUpdated() throws SQLException {
        // Setup
        DataStorer.insertProfile(profile1);
        profile1.addGoal(goal1);
        profile1.addGoal(goal2);
        profile1.addGoal(goal3);

        // Change an aspect of the primary key
        profile1.setFirstName("Ben");

        // Check that the goals were updated
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(profile1.getGoalList(), loadedProfile.getGoalList());
    }

    @Test
    public void updateProfile_updateLastName_checkGoalsUpdated() throws SQLException {
        // Setup
        DataStorer.insertProfile(profile1);
        profile1.addGoal(goal1);
        profile1.addGoal(goal2);
        profile1.addGoal(goal3);

        // Change an aspect of the primary key
        profile1.setLastName("Solo");

        // Check that the goals were updated
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(profile1.getGoalList(), loadedProfile.getGoalList());
    }
    
    @Test 
    public void updateProfile_updateNonKey_checkNoPropagation() throws SQLException {
        // Setup
        DataStorer.insertProfile(profile1);
        profile1.addGoal(goal1);
        profile1.addActivity(activity1);

        // Change something that is not part of the primary key
        profile1.setWeight(65);

        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        // We'd expect an exception if the test was failing so this assert is trivial
        assertEquals(profile1, loadedProfile);
    }

    @Test
    public void updateActivity_updateActivityName_checkDataRowsUpdated() throws SQLException {
        // Setup
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        activity1.addDataRow(row2);

        // Change an aspect of the primary key
        activity1.setName("This is a test activity");

        // Check that the raw data was updated
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(profile1.getActivityList().get(0).getRawData(), loadedProfile.getActivityList().get(0).getRawData());
    }

    @Test
    public void updateActivity_updateActivityDate_checkDataRowsUpdated() throws SQLException {
        // Setup
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        activity1.addDataRow(row2);

        // Change an aspect of the primary key
        activity1.setDate("2010-11-12");

        // Check that the raw Data was updated
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(profile1.getActivityList().get(0).getRawData(), loadedProfile.getActivityList().get(0).getRawData());
    }

    @Test
    public void updateActivity_updateFirstName_checkDataRowsUpdated() throws SQLException {
        // Setup
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        activity1.addDataRow(row2);

        // Change an aspect of the primary key
        profile1.setFirstName("Phoebe");

        // Check that the rawData was updated
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(profile1.getActivityList().get(0).getRawData(), loadedProfile.getActivityList().get(0).getRawData());
    }

    @Test
    public void updateActivity_updateLastName_checkDataRowsUpdated() throws SQLException {
        // Setup
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        activity1.addDataRow(row2);

        // Change an aspect of the primary key
        profile1.setLastName("King");

        // Check that the rawData was updated
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(profile1.getActivityList().get(0).getRawData(), loadedProfile.getActivityList().get(0).getRawData());
    }
    
    @Test
    public void updateActivity_updateNonKey_checkNoPropagation() throws SQLException {
        // Setup
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        activity1.addDataRow(row2);

        // Change something that is not part of the primary key of a dataRow
        activity1.setType(ActivityType.Other);
        
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        // We'd expect an exception if the test was failing so this assert is trivial
        assertEquals(profile1, loadedProfile);
    }

}