package seng202.team4.model;

import org.junit.Test;
import static org.junit.Assert.*;
import seng202.team4.model.CalorieCalculator;
import seng202.team4.model.ActivityType;

public class CalorieCalculatorTest {

    @Test
    public void runningMETCalculatorTest() {
        CalorieCalculator testCalculator = new CalorieCalculator(5.4, 49, ActivityType.Running);

    }

}