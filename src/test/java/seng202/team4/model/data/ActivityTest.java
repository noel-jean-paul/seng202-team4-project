package seng202.team4.model.data;

import org.junit.*;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.DataStorer;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class ActivityTest {
    private static Activity activity1;
    private static Activity activity2;

    @BeforeClass
    public static void setUp() {
        activity1 = new Activity("Run in the park", "2000-12-12", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 187);

        activity2 = new Activity("Walk around the block", "2018-09-01", "Quick walk",
                ActivityType.Walk, "01:28:30", "00:11:19", 1.2, 30);
    }

    @Test
    public void compareTo_DifferentDates_checkComesBefore() {
        activity1.setDate(LocalDate.parse("2018-06-01"));   // Earlier date
        activity2.setDate(LocalDate.parse("2018-06-02"));   // Later date

        assert activity1.compareTo(activity2) < 0;
    }

    @Test
    public void compareTo_DifferentDates_checkComesAfter() {
        activity1.setDate(LocalDate.parse("2018-06-01"));   // Earlier date
        activity2.setDate(LocalDate.parse("2018-06-02"));   // Later date

        assert activity2.compareTo(activity1) > 0;
    }

    @Test
    public void compareTo_SameDate_DifferentTime_checkComesBefore() {
        activity1.setDate(LocalDate.parse("2018-06-01"));
        activity2.setDate(LocalDate.parse("2018-06-01"));   // Same Date

        activity1.setStartTime(LocalTime.parse("11:59:59"));    // Earlier time
        activity2.setStartTime(LocalTime.parse("12:00:00"));    // Later time

        assert activity1.compareTo(activity2) < 0;
    }

    @Test
    public void compareTo_SameDate_checkComesAfter() {
        activity1.setDate(LocalDate.parse("2018-06-01"));
        activity2.setDate(LocalDate.parse("2018-06-01"));   // Same Date

        activity1.setStartTime(LocalTime.parse("11:59:59"));    // Earlier time
        activity2.setStartTime(LocalTime.parse("12:00:00"));    // Later time

        assert activity2.compareTo(activity1) > 0;
    }

    @Test
    public void compareTo_SameDate_SameTime() {
        activity1.setDate(LocalDate.parse("2018-06-01"));
        activity2.setDate(LocalDate.parse("2018-06-01"));   // Same Date

        activity1.setStartTime(LocalTime.parse("12:00:00"));
        activity2.setStartTime(LocalTime.parse("12:00:00"));    // Same time

        assert activity1.compareTo(activity2) == 0;
    }
}