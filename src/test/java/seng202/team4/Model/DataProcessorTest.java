package seng202.team4.Model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataProcessorTest {

    @Test
    public void totalDistance() {
        ArrayList<ActivityRawData> testData = new ArrayList<ActivityRawData>();
        testData.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:42:28"), 0, 0, 0, 0));
        testData.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:43:05"), 0, 0, 0, 0));
        testData.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:43:15"), 0, 0, 0, 0));
        testData.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:43:34"), 0, 0, 0, 0));
        float distance = DataProcessor.totalDistance(testData);
        assertEquals(155.86, distance, 0.1);
    }

    @Test
    public void basicDurationTest() {
        ArrayList<ActivityRawData> testData = new ArrayList<ActivityRawData>();
        testData.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:42:28"), 0, 0, 0, 0));
        testData.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:43:05"), 0, 0, 0, 0));
        testData.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:43:15"), 0, 0, 0, 0));
        testData.add(new ActivityRawData(LocalDate.parse("2015-04-10"), LocalTime.parse("23:43:34"), 0, 0, 0, 0));
        int duration = DataProcessor.calculateDuration(testData);
        assertEquals(66, duration);
    }
}