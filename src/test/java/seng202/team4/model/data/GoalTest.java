package seng202.team4.model.data;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.database.DataAccesser;
import seng202.team4.model.database.DataLoader;
import seng202.team4.model.database.DataStorer;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class GoalTest {
    private static Profile profile1;
    private Profile loadedProfile;
    private static Goal goal1;
    private static Goal goal2;
    private String expectedDescription;
    private String description;

    @BeforeClass
    public static void setUp() throws SQLException {
        DataAccesser.initialiseTestConnection();
        DataAccesser.clearDatabase();

        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        goal1 = new Goal(1, 100, GoalType.Run,"2018-09-28", "2017-05-12",
                 "PT50M");
        goal2 = new Goal(2, 100, GoalType.Run,"2018-09-28", "2017-01-12",
                "PT50M");

        // Set owner as addCurrentGoal or loadProfile are not called prior to setters
        goal1.setOwner(profile1);
        goal2.setOwner(profile1);
    }

    @Before
    public void setUpReccurring() throws SQLException {
        profile1.getCurrentGoals().clear();
        DataAccesser.clearDatabase();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        DataAccesser.clearDatabase();
        DataAccesser.closeDatabase();
    }

    @Test
    public void setNumber() throws SQLException {
        int num = 5;
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setNumber(num);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(num, loadedProfile.getCurrentGoals().get(0).getNumber());
    }

    @Test
    public void setProgress() throws SQLException {
        double progress = 57;
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setProgress(progress);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(progress, loadedProfile.getCurrentGoals().get(0).getProgress(), 0.01);
    }

    @Test
    public void setType() throws SQLException {
        GoalType type = GoalType.Walk;
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setType(type);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(type, loadedProfile.getCurrentGoals().get(0).getType());
    }

    @Test
    public void setCreationDate() throws SQLException {
        String date = "2015-11-30";
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setCreationDate(date);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(LocalDate.parse(date), loadedProfile.getCurrentGoals().get(0).getCreationDate());
    }

    @Test
    public void setExpiryDate() throws SQLException {
        String date = "2020-11-30";
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setExpiryDate(date);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(LocalDate.parse(date), loadedProfile.getCurrentGoals().get(0).getExpiryDate());
    }

    @Test
    public void setCompletionDate() throws SQLException {
        String date = "2016-11-30";
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setCompletionDate(date);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(LocalDate.parse(date), loadedProfile.getCurrentGoals().get(0).getCompletionDate());
    }

    @Test
    public void setGoalDistance() throws SQLException {
        double distance = 15.0;
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setGoalDistance(distance);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(distance, loadedProfile.getCurrentGoals().get(0).getGoalDistance(), 0.01);
    }

    @Test
    public void setGoalDuration() throws SQLException {
        Duration duration = Duration.parse("PT65M");
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setGoalDuration(duration);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(duration, loadedProfile.getCurrentGoals().get(0).getGoalDuration());
    }

    @Test
    public void setCurrent() throws SQLException {
        // insert the a goal into the database inside a profile
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        // Current for goal1 is currently true so change it to false
        boolean current = false;
        goal1.setCurrent(current);
        // Load profile1 from the database
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        // Check that current was updated correctly in the database - will get index out of bounds exception if it was not
        assertEquals(current, loadedProfile.getPastGoals().get(0).isCurrent());
    }

    @Test
    public void incrementProgress() {
        Goal goal = new Goal(2, 99, GoalType.Run,"2018-09-28", "2017-01-12",
                "PT50M");
        double originalProgress = goal.getProgress();
        goal.incrementProgress(100);
        assertEquals(100, goal.getProgress(), 0.001);
    }

    @Test
    public void generateDescription_running_distanceGoal() {
        // Create a new running distance goal
        Goal goal = new Goal(2, 99, GoalType.Run,"2018-09-28", "2017-01-12",
                14.0);
        // Create the real and expected descriptions
        description = goal.getDescription();
        expectedDescription = "Run 14000 meters";

        // Check that the real and expected descriptions match
        assertEquals(expectedDescription, description);
    }

    @Test
    public void generateDescription_walking_durationGoal() {
        // Create a new walking duration goal
        Goal goal = new Goal(2, 99, GoalType.Walk,"2018-09-28", "2017-01-12",
                "PT2H40M");
        // Create the real and expected descriptions
        description = goal.getDescription();
        expectedDescription = "Walk for 2 hours and 40 minutes";

        // Check that the real and expected descriptions match
        assertEquals(expectedDescription, description);
    }

    @Test
    public void generateDescription_running_caloriesGoal() {
        // Create a running, calories goal
        Goal goal = new Goal(2, 99, GoalType.Run, "2018-09-28", "2017-01-12",
                400);
        // Create the real and expected descriptions
        description = goal.getDescription();
        expectedDescription = "Burn 400 calories while running";

        // Check that the real and expected descriptions match
        assertEquals(expectedDescription, description);
    }

    @Test
    public void generateDescription_walking_caloriesGoal() {
        // Create a new walking calories goal
        Goal goal = new Goal(2, 99, GoalType.Walk, "2018-09-28", "2017-01-12",
                400);
        // Create the real and expected descriptions
        description = goal.getDescription();
        expectedDescription = "Burn 400 calories while walking";

        // Check that the real and expected descriptions match
        assertEquals(expectedDescription, description);
    }
}