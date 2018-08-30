package seng202.team4.Model.data;

import org.junit.Test;
import seng202.team4.model.data.DataCalculator;
import seng202.team4.model.data.DataRow;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataCalculatorTest {

    @Test
    public void totalDistance() {
        ArrayList<DataRow> dataList = new ArrayList<>();
        dataList.add(new DataRow(0, "2015-04-10", "23:42:28", 69, 30.2553368, -97.83891084, 239.5));
        dataList.add(new DataRow(0, "2015-04-10","23:43:05",87,30.25499189,-97.83913958,239));
        dataList.add(new DataRow(0, "2015-04-10","23:43:15",132,30.25469617,-97.83931962,239));
        dataList.add(new DataRow(0, "2015-04-10","23:43:34",156,30.2541537,-97.83977501,239.5));
        double distance = DataCalculator.totalDistance(dataList);
        assertEquals(156, distance, 10);    // Expected value taken from google maps distance feature
    }

    @Test
    public void calculateDuration() {
        ArrayList<DataRow> dataList = new ArrayList<>();
        dataList.add(new DataRow(0, "2015-04-10", "23:42:28", 69, 30.2553368, -97.83891084, 239.5));
        dataList.add(new DataRow(0, "2015-04-10","23:43:05",87,30.25499189,-97.83913958,239));
        dataList.add(new DataRow(0, "2015-04-10","23:43:15",132,30.25469617,-97.83931962,239));
        dataList.add(new DataRow(0, "2015-04-10","23:43:34",156,30.2541537,-97.83977501,239.5));
        int duration = DataCalculator.calculateDuration(dataList);
        assertEquals(66, duration);     // Expected value was calculated by hand & calculator
    }

    @Test
    public void testRadiusCalculator() {
        double radius = DataCalculator.earthRadiusCalculation(30.2553368);      // Using the value given by the first data point in "Walking in the woods"
        assertEquals(6372742, radius, 100);
    }
}