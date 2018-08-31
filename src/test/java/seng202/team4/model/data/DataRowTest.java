package seng202.team4.model.data;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataRowTest {
    private static DataRow row1;
    private static DataRow row2;

    @BeforeClass
    public static void setUp() {
        row1 = new DataRow(1, "2018-09-28", "12:21:12", 164, 50, 50,
                50);
        row2 = new DataRow(2, "2018-09-28", "12:21:12", 164, 50, 50,
                50);
    }

    @Test
    public void compareTo_differentNumber_checkComesBefore() {
        row1.setNumber(3);
        row2.setNumber(4);
        assert row1.compareTo(row2) < 0;
    }

    @Test
    public void compareTo__differentNumber_checkComesAfter() {
        assert row2.compareTo(row1) > 0;
    }

    @Test
    public void compareTo_sameNumber() {
        row1.setNumber(1);
        row2.setNumber(1);
        assert row1.compareTo(row2) == 0;
    }
}