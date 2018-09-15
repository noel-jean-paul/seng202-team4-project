package seng202.team4.Model.utilities;

import org.junit.Before;
import org.junit.Test;
import seng202.team4.model.data.Activity;
import seng202.team4.model.utilities.DataProcessor;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.enums.ActivityType;


import java.time.Month;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataProcessorTest {

    Profile testProfile;

    @Before
    public void setupProfile() {
        testProfile = new Profile("Jane", "Doe", 1998, Month.MAY, 18, 78.6, 182);
        ArrayList<DataRow> levelDataList = new ArrayList<>(); // Data where all points are at same elevation
        levelDataList.add(new DataRow(0, "2015-04-10", "23:42:28", 69, 30.2553368, -97.83891084, 0));
        levelDataList.add(new DataRow(0, "2015-04-10","23:43:05",87,30.25499189,-97.83913958,0));
        levelDataList.add(new DataRow(0, "2015-04-10","23:43:15",132,30.25469617,-97.83931962,0));
        levelDataList.add(new DataRow(0, "2015-04-10","23:43:34",156,30.2541537,-97.83977501,0));

        ArrayList<DataRow> elevatedDataList = new ArrayList<>(); // Data with points at different levels
        elevatedDataList.add(new DataRow(0, "2015-04-10", "23:42:28", 69, 30.2553368, -97.83891084, 301));
        elevatedDataList.add(new DataRow(0, "2015-04-10","23:43:05",87,30.25499189,-97.83913958,285));
        elevatedDataList.add(new DataRow(0, "2015-04-10","23:43:15",132,30.25469617,-97.83931962,302));
        elevatedDataList.add(new DataRow(0, "2015-04-10","23:43:34",156,30.2541537,-97.83977501,249));

        testProfile.addActivity(new Activity("Test Activity on same level", levelDataList));
        testProfile.addActivity(new Activity("Test Activity with elevation", elevatedDataList));
    }


    @Test
    public void totalDistanceLevel() {
        double distance = DataProcessor.totalDistance(testProfile.getActivityList().get(0).getRawData());
        assertEquals(156, distance, 10);    // Expected value taken from google maps distance feature
    }

    @Test
    public void totalDistanceElevation() {
        double distance = DataProcessor.totalDistance(testProfile.getActivityList().get(1).getRawData());
        assertEquals(179.3, distance, 1);
    }

    @Test
    public void calculateDuration() {
        int duration = DataProcessor.calculateDuration(testProfile.getActivityList().get(0).getRawData());
        assertEquals(66, duration);     // Expected value was calculated by hand & calculator
    }

    @Test
    public void testRadiusCalculator() {
        double radius = DataProcessor.earthRadiusCalculation(testProfile.getActivityList().get(0).getRawData().get(0).getLatitude());
        assertEquals(6372742, radius, 10);
    }

    @Test
    public void testRunningModerateSpeed() {

        double calories = DataProcessor.calculateCalories(10.6, 1560, ActivityType.Run, testProfile);
        assertEquals(350.5, calories, 0.1);
    }

    @Test
    public void testRunningLowSpeed() {
        double calories = DataProcessor.calculateCalories(4,1560, ActivityType.Run, testProfile);
        assertEquals(214.6, calories, 0.1);
    }

    @Test
    public void testRunningHighSpeed() {
        double calories = DataProcessor.calculateCalories(21,1560, ActivityType.Run, testProfile);
        assertEquals(822.5, calories, 0.1);
    }

    @Test
    public void testWalkingLowSpeed() {
        double calories = DataProcessor.calculateCalories(3.0,1560, ActivityType.Walk, testProfile);
        assertEquals(71.5, calories, 0.1);
    }

    @Test
    public void testWalkingModerateSpeed() {
        double calories = DataProcessor.calculateCalories(5.4,1560, ActivityType.Walk, testProfile);
        assertEquals(153.8, calories, 0.1);
    }

    @Test
    public void testWalkingHighSpeed() {
        double calories = DataProcessor.calculateCalories(7, 1560, ActivityType.Walk, testProfile);
        assertEquals(250.3, calories, 0.1);
    }
}