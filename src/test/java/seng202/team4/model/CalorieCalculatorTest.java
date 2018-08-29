package seng202.team4.model;

import org.junit.Test;
import seng202.team4.model.CalorieCalculator;
import seng202.team4.model.data.enums.ActivityType;

import static org.junit.Assert.*;

public class CalorieCalculatorTest {

    @Test
    public void testRunningModerateSpeed() {
        double calories = CalorieCalculator.calculateCalories(10.6, 78.6, 1560, ActivityType.Run);
        assertEquals(350.5, calories, 0.1);
    }

    @Test
    public void testRunningLowSpeed() {
        double calories = CalorieCalculator.calculateCalories(4, 78.6, 1560, ActivityType.Run);
        assertEquals(214.6, calories, 0.1);
    }

    @Test
    public void testRunningHighSpeed() {
        double calories = CalorieCalculator.calculateCalories(21, 78.6, 1560, ActivityType.Run);
        assertEquals(822.5, calories, 0.1);
    }

    @Test
    public void testWalkingLowSpeed() {
        double calories = CalorieCalculator.calculateCalories(3.0, 78.6, 1560, ActivityType.Walk);
        assertEquals(71.5, calories, 0.1);
    }

    @Test
    public void testWalkingModerateSpeed() {
        double calories = CalorieCalculator.calculateCalories(5.4, 78.6, 1560, ActivityType.Walk);
        assertEquals(153.8, calories, 0.1);
    }

    @Test
    public void testWalkingHighSpeed() {
        double calories = CalorieCalculator.calculateCalories(7.5, 78.6, 1560, ActivityType.Walk);
        assertEquals(296.8, calories, 0.1);
    }

}