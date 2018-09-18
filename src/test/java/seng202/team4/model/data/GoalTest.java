package seng202.team4.model.data;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.database.DataAccesser;
import seng202.team4.model.database.DataTestAccesser;
import seng202.team4.model.database.DataLoader;
import seng202.team4.model.database.DataStorer;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class GoalTest {
    private static Profile profile1;
    private Profile loadedProfile;
    private static Goal goal1;
    private static Goal goal2;

    @BeforeClass
    public static void setUp() throws SQLException {
        DataTestAccesser.initialiseConnection();
        DataAccesser.clearDatabase();

        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        goal1 = new Goal(1, 100, GoalType.Run,"2018-09-28", "2017-05-12",
                20, 50);
        goal2 = new Goal(2, 100, GoalType.Run,"2018-09-28", "2017-01-12",
                20, 50);

        // Set owner as addGoal or loadProfile are not called prior to setters
        goal1.setOwner(profile1);
        goal2.setOwner(profile1);
    }

    @Before
    public void setUpReccurring() throws SQLException {
        profile1.getGoalList().clear();
        DataAccesser.clearDatabase();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        DataTestAccesser.closeDatabase();
    }

    @Test
    public void compareTo_differentNumber_checkComesBefore() throws SQLException {
        goal1.setNumber(3);
        goal2.setNumber(4);
        assert goal1.compareTo(goal2) < 0;
    }

    @Test
    public void compareTo__differentNumber_checkComesAfter() throws SQLException  {
        goal1.setNumber(3);
        goal2.setNumber(4);
        assert goal2.compareTo(goal1) > 0;
    }

    @Test
    public void compareTo_sameNumber() throws SQLException  {
        goal1.setNumber(1);
        goal2.setNumber(1);
        assert goal1.compareTo(goal2) == 0;
    }

    @Test
    public void setNumber() throws SQLException {
        int num = 5;
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setNumber(num);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(num, loadedProfile.getGoalList().get(0).getNumber());
    }

    @Test
    public void setProgress() throws SQLException {
        int progress = 57;
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setProgress(progress);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(progress, loadedProfile.getGoalList().get(0).getProgress(), 0.01);
    }

    @Test
    public void setType() throws SQLException {
        GoalType type = GoalType.Other;
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setType(type);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(type, loadedProfile.getGoalList().get(0).getType());
    }

    @Test
    public void setCreationDate() throws SQLException {
        String date = "2015-11-30";
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setCreationDate(date);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(LocalDate.parse(date), loadedProfile.getGoalList().get(0).getCreationDate());
    }

    @Test
    public void setExpiryDate() throws SQLException {
        String date = "2020-11-30";
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setExpiryDate(date);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(LocalDate.parse(date), loadedProfile.getGoalList().get(0).getExpiryDate());
    }

    @Test
    public void setCompletionDate() throws SQLException {
        String date = "2016-11-30";
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setCompletionDate(date);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(LocalDate.parse(date), loadedProfile.getGoalList().get(0).getCompletionDate());
    }

    @Test
    public void setDescription() throws SQLException {
        String description = "Run 15 km";
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setDescription(description);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(description, loadedProfile.getGoalList().get(0).getDescription());
    }

    @Test
    public void setGoalDistance() throws SQLException {
        double distance = 15.0;
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setGoalDistance(distance);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(distance, loadedProfile.getGoalList().get(0).getGoalDistance(), 0.01);
    }

    @Test
    public void setGoalDuration() throws SQLException {
        double duration = 65;
        DataStorer.insertProfile(profile1);
        DataStorer.insertGoal(goal1, profile1);
        goal1.setGoalDuration(duration);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(duration, loadedProfile.getGoalList().get(0).getGoalDuration(), 0.01);
    }
}