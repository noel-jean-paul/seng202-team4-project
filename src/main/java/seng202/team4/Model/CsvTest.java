package seng202.team4.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;


public class CsvTest {

    /*The current major problem with this class is the fact that it currently only prints out the last data point for an activity
    rather than each datapoint individually. However it prints out this datapoint the same number of times as the rows of datapoints
    expected.
     */

    CsvTest() {
        Activity activity = new Activity();
    }

    public ArrayList readFile(String filename, ActivityRawData row, ArrayList<ActivityRawData> rows) {
        String line;   //empty line into which data will be read
        String csvSplitBy = ",";    //split on the comma
        String[] dataPoint = new String[6];
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = bufferedReader.readLine().split(csvSplitBy);   //read the first line
            System.out.println(firstLine[1]);
            while (!(line = bufferedReader.readLine()).contains("#")) { //read up until the next # symbol, signifying the start of a new activity
                dataPoint = line.split(csvSplitBy);
                //still need to set date and time
                row.setHeartRate(Integer.parseInt(dataPoint[2]));
                row.setLatitude(Double.parseDouble(dataPoint[3]));
                row.setLongitude(Double.parseDouble(dataPoint[4]));
                row.setElevation(Double.parseDouble(dataPoint[5]));
                rows.add(row);
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        System.out.println(rows.size());
        return rows;
    }

    /*public ArrayList addActivity(ArrayList dataRow) {

    }*/

    public static void main(String[] args) {
        String filename = "seng202_2018_example_data.csv";
        CsvTest test = new CsvTest();
        ActivityRawData row = new ActivityRawData(null, null,0,0,0,0);  //Date and time not set yet as they are slightly more difficult
        ArrayList<ActivityRawData> rows = new ArrayList<>();
        test.readFile(filename, row, rows);
        for(ActivityRawData oneRow : rows) {
            System.out.println("Heart Rate: " + oneRow.getHeartRate() + " Latitude: " + oneRow.getLatitude() + " Longitude: " + oneRow.getLongitude() + " Elevation: " + oneRow.getElevation());
        }


    }

}
