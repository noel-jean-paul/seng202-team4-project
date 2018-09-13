package seng202.team4.model.data;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.database.DataAccesser;
import seng202.team4.model.database.DataLoader;
import seng202.team4.model.database.DataStorer;
import seng202.team4.model.database.DataTestHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProfileTest {
    private static Profile profile1;
    private static Profile  loadedProfile;
    private static Activity activity1;
    private static Activity activity2;
    private static Activity activity3;
    private static List<Activity> expected;

    private static Goal goal1;
    private static Goal goal2;
    private static Goal goal3;
    private static List<Goal> expectedGoals;

    @BeforeClass
    public static void setUp() throws SQLException {
        // initialise the database connection
        DataAccesser.initialiseConnection();

        // Clean the database
        DataTestHelper.clearDatabase();

        // Initialise profile
        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);

        // Initialise activities
        activity1 = new Activity("Run in the park", "2017-12-12", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 187);

        activity2 = new Activity("Walk around the block", "2019-12-12", "Quick walk",
                ActivityType.Walk, "01:28:30", "00:11:19", 1.2, 30);

        activity3 = new Activity("Jog through Uni", "2018-12-12", "Quick walk",
                ActivityType.Run, "01:28:30", "00:11:19", 1.2, 30);

        expected = new ArrayList<>(Arrays.asList(activity1, activity3, activity2));

        // Initialise Goals
        goal1 = new Goal(1, 100, GoalType.Run,"2018-09-28", "2017-05-12",
                20, 50);
        goal2 = new Goal(2, 100, GoalType.Run,"2018-09-28", "2017-01-12",
                20, 50);
        goal3 = new Goal(3, 100, GoalType.Run,"2018-09-28", "2017-01-12",
                20, 50);

        expectedGoals = new ArrayList<>(Arrays.asList(goal1, goal2, goal3));
    }

    @Before
    public void setUpReccuring() throws SQLException {
        // clear lists and database
        profile1.getActivityList().clear();
        profile1.getGoalList().clear();
        DataTestHelper.clearDatabase();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        DataAccesser.closeDatabase();
    }

    @Test
    public void addActivity_checkList() throws SQLException {
        // Clear the activity list
        profile1.getActivityList().clear();

        // Add the activities to the profile.
        profile1.addActivity(activity1);
        profile1.addActivity(activity2);
        profile1.addActivity(activity3);

        assertEquals(expected, profile1.getActivityList());
    }

    @Test
    public void addAllActivities() {
        // Clear the activity list
        profile1.getActivityList().clear();

        // Add an activity to the profile activityList
        profile1.getActivityList().add(activity3);

        // Create a list of activities to be added - list is out of order
        List<Activity> activities = new ArrayList<>(Arrays.asList(activity2, activity1));

        // Add activitities to the activityList
        profile1.addAllActivities(activities);

        assertEquals(expected, profile1.getActivityList());
    }

    @Test
    public void addGoal() throws SQLException {
        // Clear goal list
        profile1.getGoalList().clear();

        // Add goals to profile
        profile1.addGoal(goal3);
        profile1.addGoal(goal1);
        profile1.addGoal(goal2);

        assertEquals(expectedGoals, profile1.getGoalList());
    }

    @Test
    public void addAllGoals() {
        // Clear goal list
        profile1.getGoalList().clear();

        // Add a goal to the profile goalList
        profile1.getGoalList().add(goal3);

        // Create a list of goals to be added - list is out of order
        List<Goal> goals = new ArrayList<>(Arrays.asList(goal2, goal1));

        // Add activitities to the goalList
        profile1.addAllGoals(goals);

        assertEquals(expectedGoals, profile1.getGoalList());
    }

    @Test
    public void removeActivity_checkRemovedFromList() throws SQLException {
        profile1.addActivity(activity1);
        profile1.removeActivity(activity1);

        assertEquals(0, profile1.getActivityList().size());
    }

    @Test
    public void removeActivity_checkRemovedFromDatabase() throws SQLException {
        // Add the goal and profile
        profile1.addActivity(activity1);
        DataStorer.insertProfile(profile1);

        profile1.removeActivity(activity1);

        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(0, loadedProfile.getActivityList().size());
    }

    @Test
    public void removeGoal_checkRemovedFromList() throws SQLException {
        profile1.addGoal(goal1);
        profile1.removeGoal(goal1);

        assertEquals(0, profile1.getGoalList().size());
    }

    @Test
    public void removeGoal_checkRemovedFromDatabase() throws SQLException {
        // Add the goal and profile
        profile1.addGoal(goal1);
        DataStorer.insertProfile(profile1);

        profile1.removeGoal(goal1);

        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(0, loadedProfile.getGoalList().size());
    }
}