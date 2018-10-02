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

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProfileTest {
    private static Profile profile1;
    private static Profile  loadedProfile;
    private static Activity activity1;
    private static Activity activity2;
    private static Activity activity3;
    private static List<Activity> activities;
    private static List<Activity> expected;

    private static Goal goal1;
    private static Goal goal2;
    private static Goal goal3;
    private static Goal goal4;
    private static List<Goal> expectedGoals;
    List<Goal> original;

    @BeforeClass
    public static void setUp() throws SQLException {
        // initialise the database connection
        DataAccesser.initialiseTestConnection();

        // Clean the database
        DataAccesser.clearDatabase();

        // Initialise profile
        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);

        // Initialise activities
        activity1 = new Activity("Run in the park", "2017-12-12", ActivityType.Run,
                "12:15:01", "PT40M36S", 5.13, 187);
        // test dependent on progress, distance and calories burned of this activity

        activity2 = new Activity("Walk around the block", "2019-12-12",
                ActivityType.Walk, "01:28:30", "PT11M19S", 1.2, 30);

        activity3 = new Activity("Jog through Uni", "2018-12-12",
                ActivityType.Run, "01:28:30", "PT11M19S", 1.2, 30);

        expected = new ArrayList<>(Arrays.asList(activity2, activity3, activity1));
        activities = new ArrayList<>();

        // Initialise Goals
        goal1 = new Goal(1, 0, GoalType.Run,"2017-01-01", "2018-05-12",
                20.0);
        goal2 = new Goal(2, 0, GoalType.Run,"2017-01-01", "2050-01-12",
                 "PT50M");
        goal3 = new Goal(3, 0, GoalType.Run,"2017-01-01", "2017-01-12",
                200);
        goal4 = new Goal(3, 0, GoalType.Run,"2020-01-01", "2017-01-12",
                200);

        expectedGoals = new ArrayList<>();
    }

    @Before
    public void setUpReccuring() throws SQLException {
        // clear lists and database
        profile1.getActivityList().clear();
        profile1.getCurrentGoals().clear();
        profile1.getPastGoals().clear();
        expectedGoals.clear();
        activities.clear();

        // Reset goal progress
        goal1.updateProgressValue(0);
        goal2.updateProgressValue(0);
        goal3.updateProgressValue(0);
        goal4.updateProgressValue(0);
        DataAccesser.clearDatabase();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        DataAccesser.clearDatabase();
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
        profile1.getCurrentGoals().clear();

        // Add goals to profile
        profile1.addCurrentGoal(goal3);
        profile1.addCurrentGoal(goal1);
        profile1.addCurrentGoal(goal2);

        // Fill expectedGoals
        expectedGoals.addAll(Arrays.asList(goal3, goal2, goal1));

        assertEquals(expectedGoals, profile1.getCurrentGoals());
    }

    @Test
    public void addGoal_checkStoredInDatabase() throws SQLException {
        DataStorer.insertProfile(profile1);
        profile1.addCurrentGoal(goal1);
        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());
        assertEquals(goal1, loadedProfile.getCurrentGoals().get(0));
    }

    @Test
    public void addGoal_checkOwnerSet() throws SQLException {
        profile1.addCurrentGoal(goal1);
        assertEquals(profile1, goal1.getOwner());
    }

    @Test
    public void addAllGoals() {
        // Clear goal list
        profile1.getCurrentGoals().clear();

        // Add a goal to the profile currentGoals
        profile1.getCurrentGoals().add(goal3);

        // Create a list of goals to be added - list is out of order
        List<Goal> goals = new ArrayList<>(Arrays.asList(goal2, goal1));

        // Add activitities to the currentGoals
        profile1.addAllCurrentGoals(goals);

        // Fill expectedGoals
        expectedGoals.addAll(Arrays.asList(goal3, goal2, goal1));

        assertEquals(expectedGoals, profile1.getCurrentGoals());
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
        profile1.addCurrentGoal(goal1);
        profile1.removeCurrentGoal(goal1);

        assertEquals(0, profile1.getCurrentGoals().size());
    }

    @Test
    public void removeGoal_checkRemovedFromDatabase() throws SQLException {
        // Add the goal and profile
        profile1.addCurrentGoal(goal1);
        DataStorer.insertProfile(profile1);

        profile1.removeCurrentGoal(goal1);

        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(0, loadedProfile.getCurrentGoals().size());
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

    @Test
    public void setPictureURL() throws SQLException {
        String pictureURL = "/images/Noel-Bisson-icon.png";
        DataStorer.insertProfile(profile1);
        profile1.setPictureURL(pictureURL);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(pictureURL, loadedProfile.getPictureURL());
    }

    @Test
    public void updateGoalsForExpiry_checkRemovedFromCurrent() throws SQLException {
        // Setup
        profile1.addCurrentGoal(goal1);
        profile1.addCurrentGoal(goal2);
        expectedGoals.add(goal2);

        profile1.updateGoalsForExpiry();

        // Check the current goals are correct
        assertEquals(expectedGoals, profile1.getCurrentGoals());
    }

    @Test
    public void updateGoalsForExpiry_checkAddedToPast() throws SQLException {
        // Setup
        profile1.addCurrentGoal(goal1);
        profile1.addCurrentGoal(goal2);
        expectedGoals.clear();
        expectedGoals.add(goal1);

        profile1.updateGoalsForExpiry();

        // Check the past goals are correct
        assertEquals(expectedGoals, profile1.getPastGoals());
    }

    @Test
    public void updateGoalsForProgress_validDate_distanceGoal_checkProgressUpdated() throws SQLException {
        // Setup
        profile1.addCurrentGoal(goal1);
        activities.add(activity1);

        profile1.updateGoalsForProgress(activities);

        assertEquals(25.65, goal1.getProgress(), 0.0001);
    }

    @Test
    public void updateGoalsForProgress_validDate_durationGoal_checkProgressUpdated() throws SQLException {
        // Setup
        profile1.addCurrentGoal(goal2); //Duration goal
        activities.add(activity1);

        profile1.updateGoalsForProgress(activities);

        assertEquals(80.0, goal2.getProgress(), 0.0001);
    }

    @Test
    public void updateGoalsForProgress_validDate_caloriesGoal_checkProgressUpdated() throws SQLException {
        // Setup
        profile1.addCurrentGoal(goal3); // Calories goal
        activities.add(activity1);

        profile1.updateGoalsForProgress(activities);

        assertEquals(93.5, goal3.getProgress(), 0.0001);
    }

    @Test
    public void updateGoalsForProgress_invalidDate_checkProgressUnchanged() throws SQLException {
        // Setup
        profile1.addCurrentGoal(goal4); // Creation date in 2020
        activities.add(activity3);  // Date in 2018

        profile1.updateGoalsForProgress(activities);

        assertEquals(0, goal4.getProgress(), 0.0001);
    }

    @Test
    public void updateGoalsForProgress_invalidType_checkProgressUnchanged() throws SQLException {
        // Setup
        profile1.addCurrentGoal(goal1); // Type run
        activities.add(activity2);  // Type walk

        profile1.updateGoalsForProgress(activities);

        assertEquals(0, goal1.getProgress(), 0.0001);
    }

    @Test
            /* multiple activities contributing to the same goal */
    public void updateGoalsForProgress_multipleActivities_validDates_checkProgressUpdated() throws SQLException {
        // Setup
        profile1.addCurrentGoal(goal1); // Distance goal
        activities.add(activity1);
        activities.add(activity3);

        profile1.updateGoalsForProgress(activities);

        // Check that both activities increased the goal progress
        assertEquals(31.65, goal1.getProgress(), 0.0001);
    }

    @Test
        /* multiple goals, heck all goals are updated */
    public void updateGoalsForProgress_multipleActivities_validDates_checkGoal1_ProgressUpdated() throws SQLException {
        // Setup
        profile1.addCurrentGoal(goal1); // Distance goal
        profile1.addCurrentGoal(goal2); // Duration goal
        activities.add(activity1);

        profile1.updateGoalsForProgress(activities);

        // Check that both activities increased the goal progress
        assertEquals(25.65, goal1.getProgress(), 0.0001);
    }

    @Test
            /* multiple goals, heck all goals are updated */
    public void updateGoalsForProgress_multipleActivities_validDates_checkGoa2_1ProgressUpdated() throws SQLException {
        // Setup
        profile1.addCurrentGoal(goal1); // Distance goal
        profile1.addCurrentGoal(goal2); // Duration goal
        activities.add(activity1);

        profile1.updateGoalsForProgress(activities);

        // Check that both activities increased the goal progress
        assertEquals(80.0, goal2.getProgress(), 0.0001);
    }
}