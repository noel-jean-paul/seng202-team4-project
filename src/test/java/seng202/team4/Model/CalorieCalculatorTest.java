package seng202.team4.Model;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalorieCalculatorTest {

    @Test
    public void runningMETCalculatorTest() {
        CalorieCalculator testCalculator = new CalorieCalculator(83.4,9.3, 49, ActivityType.Running);
        double MET = testCalculator.getMET();
        assertEquals(9.0, MET, 0.1);
    }

    @Test
    public void runningCalorieCalculatorTest() {
        // Basing these stats off my own running experience on treadmills, numbers seem consistent
        // with what I see while running - Matt Kenny
        CalorieCalculator testCalculator = new CalorieCalculator(78.9, 11, 1560, ActivityType.Running);
        double calories = testCalculator.getCalories();
        assertEquals(376.9, calories, 0.5);
    }

    @Test
    public void walkingMETCalculatorTest() {
        CalorieCalculator testCalculator = new CalorieCalculator(7.8, 4.1, 103, ActivityType.Walking);
        double MET = testCalculator.getMET();
        assertEquals(3.0, MET, 0.1);
    }

}