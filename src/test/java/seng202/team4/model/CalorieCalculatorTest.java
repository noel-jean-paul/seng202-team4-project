package seng202.team4.model;

import org.junit.Test;
import seng202.team4.model.data.enums.ActivityType;

public class CalorieCalculatorTest {

    @Test
    public void runningMETCalculatorTest() {
        CalorieCalculator testCalculator = new CalorieCalculator(5.4, 49, ActivityType.Run);

    }

}