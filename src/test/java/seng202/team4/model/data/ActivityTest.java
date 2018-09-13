package seng202.team4.model.data;

import org.junit.*;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ActivityTest {
    private static Profile profile1;
    private Profile loadedProfile;

    private static Activity activity1;
    private static Activity activity2;

    private static DataRow row1;
    private static DataRow row2;
    private static DataRow row3;

    @BeforeClass
    public static void setUp() throws SQLException {
        DataAccesser.initialiseConnection();
        DataTestHelper.clearDatabase();

        // Initialise profile
        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);

        activity1 = new Activity("Run in the park", "2000-12-12", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 187);

        activity2 = new Activity("Walk around the block", "2018-09-01", "Quick walk",
                ActivityType.Walk, "01:28:30", "00:11:19", 1.2, 30);

        // Insert test DataRows
        row1 = new DataRow(1, "2018-07-18", "14:02:20", 182, -87.01902489,
                178.4352, 203);
        row2 = new DataRow(2, "2018-07-18", "14:02:25", 182, -87.01902489,
                178.4352, 203);
        row3 = new DataRow(3, "2018-07-18", "14:02:30", 182, -87.01902489,
                178.4352, 203);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        DataAccesser.closeDatabase();
    }

    @Before
    public void setUpReccurring() throws SQLException {
        profile1.getActivityList().clear();
        profile1.getGoalList().clear();
        DataTestHelper.clearDatabase();
        activity1.getRawData().clear();
    }

    @Test
    public void compareTo_DifferentDates_checkComesBefore() throws SQLException {
        activity1.setDate("2018-06-01");   // Earlier date
        activity2.setDate("2018-06-02");   // Later date

        assert activity1.compareTo(activity2) < 0;
    }

    @Test
    public void compareTo_DifferentDates_checkComesAfter() throws SQLException {
        activity1.setDate("2018-06-01");   // Earlier date
        activity2.setDate("2018-06-02");   // Later date

        assert activity2.compareTo(activity1) > 0;
    }

    @Test
    public void compareTo_SameDate_DifferentTime_checkComesBefore() throws SQLException {
        activity1.setDate("2018-06-01");
        activity2.setDate("2018-06-01");   // Same Date

        activity1.setStartTime("11:59:59");    // Earlier time
        activity2.setStartTime("12:00:00");    // Later time

        assert activity1.compareTo(activity2) < 0;
    }

    @Test
    public void compareTo_SameDate_checkComesAfter() throws SQLException{
        activity1.setDate("2018-06-01");
        activity2.setDate("2018-06-01");   // Same Date

        activity1.setStartTime("11:59:59");    // Earlier time
        activity2.setStartTime("12:00:00");    // Later time

        assert activity2.compareTo(activity1) > 0;
    }

    @Test
    public void compareTo_SameDate_SameTime() throws SQLException {
        activity1.setDate("2018-06-01");
        activity2.setDate("2018-06-01");   // Same Date

        activity1.setStartTime("12:00:00");
        activity2.setStartTime("12:00:00");    // Same time

        assert activity1.compareTo(activity2) == 0;
    }

    @Test
    public void addAllDataRows() {
        // Add a row to the activity raw data
        activity1.getRawData().add(row3);

        // Create a list of DataRows to be added - list is out of order
        List<DataRow> rows = new ArrayList<>(Arrays.asList(row2, row1));

        // Add rows to the activity rawData
        activity1.addAllDataRows(rows);

        // Create expected list
        List<DataRow> expectedRows = new ArrayList<>(Arrays.asList(row1, row2, row3));

        assertEquals(expectedRows, activity1.getRawData());
    }

    @Test
    public void removeDataRow_checkRemovedFromList() throws SQLException {
        activity1.addDataRow(row1);
        activity1.removeDataRow(row1);

        assertEquals(0, activity1.getRawData().size());
    }

    @Test
    public void removeDataRow_checkRemovedFromDatabase() throws SQLException {
        // Add the goal and profile
        Profile profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        DataStorer.insertProfile(profile1);

        // Remove the activity from the list and the database
        activity1.removeDataRow(row1);

        Profile loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(0, loadedProfile.getActivityList().get(0).getRawData().size());
    }

    @Test
    public void setName() throws SQLException {
       String name = "Test activity name";
        DataStorer.insertActivity(activity1, profile1);
        activity1.setName(name);

        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(name, loadedProfile.getActivityList().get(0).getName());
    }

    @Test
    public void setDescription() throws SQLException {
        String description= "I went running";
        DataStorer.insertActivity(activity1, profile1);
        activity1.setDescription(description);

        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(description, loadedProfile.getActivityList().get(0).getDescription());
    }

    @Test
    public void setDate() throws SQLException {
        String date = "2018-09-13";
        DataStorer.insertActivity(activity1, profile1);
        activity1.setDate(date);

        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(LocalDate.parse(date), loadedProfile.getActivityList().get(0).getDate());
    }

    @Test
    public void setStartTime() throws SQLException {
        String time = "16:32:43";
        DataStorer.insertActivity(activity1, profile1);
        activity1.setStartTime(time);

        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(LocalTime.parse(time), loadedProfile.getActivityList().get(0).getStartTime());
    }

    @Test
    public void setDuration() throws SQLException {
        String duration = "01:32:43";
        DataStorer.insertActivity(activity1, profile1);
        activity1.setDuration(duration);

        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(LocalTime.parse(duration), loadedProfile.getActivityList().get(0).getDuration());
    }

    @Test
    public void setDistance() throws SQLException {
        double distance = 21.3;
        DataStorer.insertActivity(activity1, profile1);
        activity1.setDistance(distance);

        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(distance, loadedProfile.getActivityList().get(0).getDistance());
    }

    @Test
    public void setCaloriesBurned() throws SQLException {
        double calories = 207;
        DataStorer.insertActivity(activity1, profile1);
        activity1.setCaloriesBurned(calories);

        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(calories, loadedProfile.getActivityList().get(0).getCaloriesBurned());
    }

    @Test
    public void setType() throws SQLException {
        ActivityType type = ActivityType.Walk;
        DataStorer.insertActivity(activity1, profile1);
        activity1.setType(type);

        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(type, loadedProfile.getActivityList().get(0).getType());
    }
}