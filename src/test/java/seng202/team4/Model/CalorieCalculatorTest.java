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

}