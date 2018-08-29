package seng202.team4.Model;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataProcessorTest {

    @Test
    public void totalDistance() {
    }

    @Test
    public void calculateDuration() {
        ArrayList<ActivityRawData> testArrayList = new ArrayList<ActivityRawData>();
        testArrayList.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:42:28"), 0, 0, 0, 0));
        testArrayList.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:43:05"), 0, 0, 0, 0));
        testArrayList.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:43:15"), 0, 0, 0, 0));
        testArrayList.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:43:34"), 0, 0, 0, 0));
        int duration = DataProcessor.calculateDuration(testArrayList);
        assertEquals(66, duration);
    }
}