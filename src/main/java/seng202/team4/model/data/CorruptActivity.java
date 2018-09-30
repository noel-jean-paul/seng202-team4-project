package seng202.team4.model.data;

import seng202.team4.model.utilities.DataProcessor;

import java.util.ArrayList;

/**
 * Corrupt Activity that has all the same properties an attributes of Activity as well as the number
 * of data rows that could not be read on import.
 */
public class CorruptActivity extends Activity {

    private int corruptRows;
    private int totalRows;

    /**
     * Constructor for the Activity class
     * @param name is the name of the activity as a string
     */
    public CorruptActivity(String name, ArrayList<DataRow> rawActivityList, int corruptRows) {
        super(name, rawActivityList);
        this.corruptRows = corruptRows;
        this.totalRows = rawActivityList.size();
    }

    /**
     * Returns the percentage of rows that were corrupt.
     *
     * @return Percentage of rows that were corrupt.
     */
    public double getPercentageCorrupt() {
        return (((double) corruptRows)/((double)totalRows))*100;
    }



}
