package seng202.team4.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FileImporterTest {
    FileImporter testImport = new FileImporter();
    String filename = "seng202_2018_example_data.csv";
    ArrayList<ActivityRawData> testRows = new ArrayList<>();
    ArrayList<Activity> testAllActivities = new ArrayList<>();


    /**
     * Early test to check if the early version of FileImporter is acting as expected by having the same number of
     * rows in it as data points in the first activity "Walk in the woods"
     */
    @Test
    public void testRowSize() {
        assertEquals(33, testImport.readFile(filename, testRows, testAllActivities).size());
    }

}