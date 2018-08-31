package seng202.team4.model.data;

import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team4.model.data.enums.GoalType;

import static org.junit.Assert.*;

public class GoalTest {

    private static Goal goal1;
    private static Goal goal2;

    @BeforeClass
    public static void setUp() {
        goal1 = new Goal(1, 100, GoalType.Run,"2018-09-28", "2017-05-12",
                20, 50);
        goal2 = new Goal(2, 100, GoalType.Run,"2018-09-28", "2017-01-12",
                20, 50);
    }

    @Test
    public void compareTo_differentNumber_checkComesBefore() {
        goal1.setNumber(3);
        goal2.setNumber(4);
        assert goal1.compareTo(goal2) < 0;
    }

    @Test
    public void compareTo__differentNumber_checkComesAfter() {
        assert goal2.compareTo(goal1) > 0;
    }

    @Test
    public void compareTo_sameNumber() {
        goal1.setNumber(1);
        goal2.setNumber(1);
        assert goal1.compareTo(goal2) == 0;
    }
}