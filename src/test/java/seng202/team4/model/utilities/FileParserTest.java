package seng202.team4.model.utilities;

import org.junit.Before;
import org.junit.Test;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.CorruptActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class FileParserTest {

    private static FileParser testImport;
    private String filename = "inDemoData.csv";
    private ArrayList<Activity> testValidActivities = new ArrayList<>();
    private ArrayList<CorruptActivity> testWarningActivities = new ArrayList<>();
    private ArrayList<Activity> testSkippedActivities = new ArrayList<>();

    //FileParser testImport = new FileParser();



    @Before
    public void init() {
        testImport = new FileParser();
        try {
            testImport.parseFileToActivites(new File(filename), testValidActivities, testWarningActivities, testSkippedActivities);
        } catch (IOException e) {

        }
    }

    /**
     * Check that the expected list is the same as the actual list
     */
    @Test
    public void activityListEqualTest() {
        ArrayList<Activity> expected;
        try {
            expected = testImport.parseFileToActivites(new File(filename), testValidActivities, testWarningActivities, testSkippedActivities);
        } catch (IOException e) {
            expected = new ArrayList<Activity>();
        }
        assertThat(testValidActivities, is(expected));
    }

    /**
     * There are 12 different activities in the sample data file. Check that each of these activities are stored
     */
    @Test
    public void activityListSizeTest() {
        assertEquals(12, testValidActivities.size());
    }


    /**
     * Check if the first activity's list of data points is the correct size
     */
    @Test
    public void firstDataRowListSizeTest() {
        assertEquals(33, testValidActivities.get(0).getRawData().size());
    }

    /**
     * Check if the last activity's list of data points is the correct size
     */
    @Test
    public void lastDataRowListSizeTest() {
        assertEquals(101, testValidActivities.get(11).getRawData().size());
    }


}

