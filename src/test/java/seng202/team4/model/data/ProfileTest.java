package seng202.team4.model.data;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.database.*;

import java.sql.SQLException;
import java.time.LocalDate;
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
        DataTestAccesser.initialiseConnection();

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
        DataTestAccesser.closeDatabase();
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
    public void addActivity_checkStoredInDatabase() throws SQLException {
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(activity1, loadedProfile.getActivityList().get(0));
    }

    @Test
    public void addActivity_checkOwnerSet() throws SQLException {
        // Add the activity to the profile.
        profile1.addActivity(activity1);
        assertEquals(profile1, activity1.getOwner());
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
    public void addGoal_checkList() throws SQLException {
        // Clear goal list
        profile1.getGoalList().clear();

        // Add goals to profile
        profile1.addGoal(goal3);
        profile1.addGoal(goal1);
        profile1.addGoal(goal2);

        assertEquals(expectedGoals, profile1.getGoalList());
    }

    @Test
    public void addGoal_checkStoredInDatabase() throws SQLException {
        DataStorer.insertProfile(profile1);
        profile1.addGoal(goal1);
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(goal1, loadedProfile.getGoalList().get(0));
    }

    @Test
    public void addGoal_checkOwnerSet() throws SQLException {
        profile1.addGoal(goal1);
        assertEquals(profile1, goal1.getOwner());
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

    @Test
    public void setFirstName() throws SQLException {
        String firstName = "Michael";
        DataStorer.insertProfile(profile1);
        profile1.setFirstName(firstName);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(firstName, loadedProfile.getFirstName());
    }

    @Test
    public void setLastName() throws SQLException {
        String lastName = "MacKay";
        DataStorer.insertProfile(profile1);
        profile1.setLastName(lastName);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(lastName, loadedProfile.getLastName());
    }

    @Test
    public void setDateOfBirth() throws SQLException {
        String dob = "2012-12-12";
        DataStorer.insertProfile(profile1);
        profile1.setDateOfBirth(dob);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(LocalDate.parse(dob), loadedProfile.getDateOfBirth());
    }

    @Test
    public void setWeight() throws SQLException {
        double weight = 100;
        DataStorer.insertProfile(profile1);
        profile1.setWeight(weight);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(weight, loadedProfile.getWeight(), 0.01);
    }

    @Test
    public void setHeight() throws SQLException {
        double height = 1.50;
        DataStorer.insertProfile(profile1);
        profile1.setHeight(height);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(height, loadedProfile.getHeight(), 0.01);
    }
}