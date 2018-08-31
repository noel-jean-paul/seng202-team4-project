package seng202.team4.model.utilities;

import org.junit.Test;
import seng202.team4.model.utilities.DataProcessor;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.enums.ActivityType;


import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataProcessorTest {

    @Test
    public void totalDistance() {
        ArrayList<DataRow> dataList = new ArrayList<>();
        dataList.add(new DataRow(0, "2015-04-10", "23:42:28", 69, 30.2553368, -97.83891084, 239.5));
        dataList.add(new DataRow(0, "2015-04-10","23:43:05",87,30.25499189,-97.83913958,239));
        dataList.add(new DataRow(0, "2015-04-10","23:43:15",132,30.25469617,-97.83931962,239));
        dataList.add(new DataRow(0, "2015-04-10","23:43:34",156,30.2541537,-97.83977501,239.5));
        double distance = DataProcessor.totalDistance(dataList);
        assertEquals(156, distance, 10);    // Expected value taken from google maps distance feature
    }

    @Test
    public void calculateDuration() {
        ArrayList<DataRow> dataList = new ArrayList<>();
        dataList.add(new DataRow(0, "2015-04-10", "23:42:28", 69, 30.2553368, -97.83891084, 239.5));
        dataList.add(new DataRow(0, "2015-04-10","23:43:05",87,30.25499189,-97.83913958,239));
        dataList.add(new DataRow(0, "2015-04-10","23:43:15",132,30.25469617,-97.83931962,239));
        dataList.add(new DataRow(0, "2015-04-10","23:43:34",156,30.2541537,-97.83977501,239.5));
        int duration = DataProcessor.calculateDuration(dataList);
        assertEquals(66, duration);     // Expected value was calculated by hand & calculator
    }

    @Test
    public void testRadiusCalculator() {
        double radius = DataProcessor.earthRadiusCalculation(30.2553368);      // Using the value given by the first data point in "Walking in the woods"
        assertEquals(6372742, radius, 100);
    }

    @Test
    public void testRunningModerateSpeed() {
        double calories = DataProcessor.calculateCalories(10.6, 78.6, 1560, ActivityType.Run);
        assertEquals(350.5, calories, 0.1);
    }

    @Test
    public void testRunningLowSpeed() {
        double calories = DataProcessor.calculateCalories(4, 78.6, 1560, ActivityType.Run);
        assertEquals(214.6, calories, 0.1);
    }

    @Test
    public void testRunningHighSpeed() {
        double calories = DataProcessor.calculateCalories(21, 78.6, 1560, ActivityType.Run);
        assertEquals(822.5, calories, 0.1);
    }

    @Test
    public void testWalkingLowSpeed() {
        double calories = DataProcessor.calculateCalories(3.0, 78.6, 1560, ActivityType.Walk);
        assertEquals(71.5, calories, 0.1);
    }

    @Test
    public void testWalkingModerateSpeed() {
        double calories = DataProcessor.calculateCalories(5.4, 78.6, 1560, ActivityType.Walk);
        assertEquals(153.8, calories, 0.1);
    }

    @Test
    public void testWalkingHighSpeed() {
        double calories = DataProcessor.calculateCalories(7.5, 78.6, 1560, ActivityType.Walk);
        assertEquals(296.8, calories, 0.1);
    }
}