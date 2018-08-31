package seng202.team4.model;

import org.junit.Before;
import org.junit.Test;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FileImporterTest {

    private static FileImporter testImport;
    private String filename = "seng202_2018_example_data.csv";
    private ArrayList<DataRow> testRows = new ArrayList<>();
    private ArrayList<Activity> testAllActivities = new ArrayList<>();

    //FileImporter testImport = new FileImporter();



    @Before
    public void init() {
        testImport = new FileImporter();
        testImport.readFile(filename, testRows, testAllActivities);
    }

    /**
     * Check that the expected list is the same as the actual list
     */
    @Test
    public void activityListEqualTest() {
        ArrayList<Activity> expected = testImport.readFile(filename, testRows, testAllActivities);
        assertThat(testAllActivities, is(expected));
    }

    /**
     * There are 12 different activities in the sample data file. Check that each of these activities are stored
     */
    @Test
    public void activityListSizeTest() {
        assertEquals(12, testAllActivities.size());
    }


    /**
     * Check if the first activity's list of data points is the correct size
     */
    @Test
    public void firstDataRowListSizeTest() {
        assertEquals(33, testAllActivities.get(0).getRawData().size());
    }

    /**
     * Check if the last activity's list of data points is the correct size
     */
    @Test
    public void lastDataRowListSizeTest() {
        assertEquals(101, testAllActivities.get(11).getRawData().size());
    }


}

