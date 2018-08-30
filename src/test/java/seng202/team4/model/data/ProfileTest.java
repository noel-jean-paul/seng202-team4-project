package seng202.team4.model.data;

import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.DataAccesser;
import seng202.team4.model.database.DataTestHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProfileTest {
    private static Profile profile1;
    private static Activity activity1;
    private static Activity activity2;
    private static Activity activity3;

    @BeforeClass
    public static void setUp() throws SQLException {
        // initialise the database connection
        DataAccesser.initialiseConnection();

        // Clean the database
        DataTestHelper.clearDatabase();

        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        activity1 = new Activity("Run in the park", "2017-12-12", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 187);

        activity2 = new Activity("Walk around the block", "2019-12-12", "Quick walk",
                ActivityType.Walk, "01:28:30", "00:11:19", 1.2, 30);

        activity3 = new Activity("Jog through Uni", "2018-12-12", "Quick walk",
                ActivityType.Run, "01:28:30", "00:11:19", 1.2, 30);
    }

    @Test
    public void addActivity() throws SQLException {
        // Add the activities to the profile.
        profile1.addActivity(activity1);
        profile1.addActivity(activity2);
        profile1.addActivity(activity3);

        // Create the expected list
        List<Activity> expected = new ArrayList<>();
        // Add in correct date order
        expected.add(activity1);
        expected.add(activity3);
        expected.add(activity2);

        assertEquals(expected, profile1.getActivityList());
    }
}