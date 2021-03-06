package seng202.team4.model.data;

import org.junit.*;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.DataAccesser;
import seng202.team4.model.database.DataLoader;
import seng202.team4.model.database.DataStorer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class DataRowTest {
    private static Profile profile1;
    private static Profile loadedProfile;
    private static Activity activity1;
    private static DataRow row1;
    private static DataRow row2;

    @BeforeClass
    public static void setUp() throws SQLException {
        DataAccesser.initialiseTestConnection();
        DataAccesser.clearDatabase();

        profile1 = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        activity1 = new Activity("Run in the park", "2000-12-12", ActivityType.Run,
                "12:15:01", "PT40M", 5.13, 187);

        row1 = new DataRow(1, "2017-09-28", "12:21:12", 164, 50, 50,
                50);
        row2 = new DataRow(2, "2018-09-28", "12:21:12", 164, 50, 50,
                50);

        // Set owner as addCurrentGoal or loadProfile are not called prior to setters
        row1.setOwner(activity1);
        row2.setOwner(activity1);
    }

    @Before
    public void setUpReccurring() throws SQLException {
        activity1.getRawData().clear();
        DataAccesser.clearDatabase();
    }

    @After
    public void tearDownReccurring() throws SQLException {
        activity1.getRawData().clear();
        profile1.getActivityList().clear();
        DataAccesser.clearDatabase();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        DataAccesser.clearDatabase();
        DataAccesser.closeDatabase();
    }

    @Test
    public void compareTo_differentDate_checkComesBefore() throws SQLException {
        row1.setDate("2017-05-05");
        row2.setDate("2018-05-05");
        assert row1.compareTo(row2) < 0;    // row 2 comes before row 1
    }

    @Test
    public void compareTo__differentDate_checkComesAfter() throws SQLException {
        row1.setDate("2018-05-05");
        row2.setDate("2017-05-05");
        assert row1.compareTo(row2) > 0;    // Row 2 comes after row 1
    }

    @Test
    public void compareTo_SameDate__differentTime_checkComesBefore() throws SQLException {
        row1.setDate("2018-05-05");
        row2.setDate("2018-05-05");

        row1.setTime("12:21:12");
        row2.setTime("12:21:13");

        assert row1.compareTo(row2) < 0;    // row 2 comes before row 1
    }

    @Test
    public void compareTo__SameDate__differentTime_checkComesAfter() throws SQLException {
        row1.setDate("2018-05-05");
        row2.setDate("2018-05-05");

        row1.setTime("12:21:12");
        row2.setTime("12:21:11");

        assert row1.compareTo(row2) > 0;    // Row 2 comes after row 1
    }


    @Test
    public void compareTo_sameDate_SameTime() throws SQLException {
        // Set the dates the same
        row1.setDate("2018-05-05");
        row2.setDate("2018-05-05");

        // Set the times to be the same
        row1.setTime("12:21:12");
        row2.setTime("12:21:12");

        // Check compareTo gives 0
        assert row1.compareTo(row2) == 0;
    }

    @Test
    public void setNumber() throws SQLException {
        int num = 5;
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        row1.setNumber(num);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(num, loadedProfile.getActivityList().get(0).getRawData().get(0).getNumber());
    }

    @Test
    public void setDate() throws SQLException {
        String date = "2019-01-15";
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        row1.setDate(date);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(LocalDate.parse(date), loadedProfile.getActivityList().get(0).getRawData().get(0).getDate());
    }

    @Test
    public void setTime() throws SQLException {
        String time = "05:08:00";
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        row1.setTime(time);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(LocalTime.parse(time), loadedProfile.getActivityList().get(0).getRawData().get(0).getTime());
    }

    @Test
    public void setHeartRate() throws SQLException {
        int rate = 170;
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        row1.setHeartRate(rate);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(rate, loadedProfile.getActivityList().get(0).getRawData().get(0).getHeartRate());
    }

    @Test
    public void setLatitude() throws SQLException {
        double latitude = -60;
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        row1.setLatitude(latitude);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(latitude, loadedProfile.getActivityList().get(0).getRawData().get(0).getLatitude(), 0.01);
    }

    @Test
    public void setLongitude() throws SQLException {
        double longitude = -70;
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        row1.setLongitude(longitude);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(longitude, loadedProfile.getActivityList().get(0).getRawData().get(0).getLongitude(), 0.01);
    }

    @Test
    public void setElevation() throws SQLException {
        double elevation = 440;
        DataStorer.insertProfile(profile1);
        profile1.addActivity(activity1);
        activity1.addDataRow(row1);
        row1.setElevation(elevation);
        loadedProfile = DataLoader.loadProfile(profile1.getFirstName(), profile1.getLastName());

        assertEquals(elevation, loadedProfile.getActivityList().get(0).getRawData().get(0).getElevation(), 0.01);
    }
}