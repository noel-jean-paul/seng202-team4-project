package seng202.team4.model.data;

import java.util.ArrayList;

/**
 * Corrupt Activity that has all the same properties an attributes of Activity as well as the number
 * of data rows that could not be read on import.
 */
public class CorruptActivity extends Activity {

    private int corruptRows;
    private int totalRows;

    /**
     * Constructor for the Activity class.
     * @param name The name of the activity as a string.
     * @param rawActivityList An ArrayList of the raw activity data rows of the CorruptActivity.
     * @param corruptRows The number of rows that got corrupted on import.
     */
    public CorruptActivity(String name, ArrayList<DataRow> rawActivityList, int corruptRows) {
        super(name, rawActivityList);
        this.corruptRows = corruptRows;
        this.totalRows = rawActivityList.size() + corruptRows;
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
