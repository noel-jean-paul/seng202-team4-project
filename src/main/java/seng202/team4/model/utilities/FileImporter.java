package seng202.team4.model.utilities;

import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class FileImporter {
    /**
     *
     * @param file the name of the file that is being imported
     * @param validActivities ArrayList of that will be filled with the activities that were successfully parsed with no warnings or errors.
     * @param warningActivities  ArrayList that will be filled with the activities that were parsed with warnings.
     * @param skippedActivities ArrayList that will be filled with the activities that had to be skipped due to lack of valid data.
     * @return the arrayList of all activities' data points.
     */
    public ArrayList readFile(File file, ArrayList<Activity> validActivities, ArrayList<Activity> warningActivities, ArrayList<Activity> skippedActivities) throws IOException {

        String line;   //empty line into which data will be read
        String csvSplitBy = ",";    //split on the comma
        String[] dataPoints; //create the string array to hold the line
        String activityName;    //Creates a string to hold the name of the activity being performed
        ArrayList<DataRow> rows = new ArrayList<DataRow>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            line = bufferedReader.readLine();
            while (line != null) {  //while end of file has not been reached
                if (line.contains("#")) {   //if the line contains a hash then we know it is a line with the activity name on it
                    activityName = line.split(csvSplitBy)[1];   //grab the activity name and store it

                    line = bufferedReader.readLine();
                    boolean warning = false;
                    int counter = 0;
                    while ((line != null) && (!(line.contains("#")))) { //read up until the next # symbol, signifying the start of a new activity
                        //the next lines split the line by comma into its unique fields
                        counter++;  //update the line counter
                        dataPoints = line.split(csvSplitBy);

                        if (dataPoints.length == 6) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                            String unformattedDate = (dataPoints[0]);   //grabs date from line

                            try {
                                LocalDate localDate = LocalDate.parse(unformattedDate, formatter);     //converts to LocalDate format
                                DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                String date = localDate.format(format1);   //converts this back to a string

                                String time = (dataPoints[1]);
                                int heartRate = (Integer.parseInt(dataPoints[2]));
                                double latitude = (Double.parseDouble(dataPoints[3]));
                                double longitude = (Double.parseDouble(dataPoints[4]));
                                double elevation = (Double.parseDouble(dataPoints[5]));

                                rows.add(new DataRow(counter, date, time, heartRate, latitude, longitude, elevation));   //add the data to a new ActivityRawData element
                            } catch (Exception e) {
                                warning = true;
                            }
                        } else {
                            warning = true;
                        }
                        line = bufferedReader.readLine();
                    }

                    ArrayList<DataRow> rowCopy = new ArrayList<>(rows);     //create a copy of the row list
                    Activity activity = new Activity(activityName, rowCopy);
                    if (rows.size() > 1) {
                        if (warning) {
                            warningActivities.add(activity);
                        } else {
                            validActivities.add(activity);   //add each of the raw activity data for that specific activity, along with the activity name into an Activity object
                        }
                    } else {
                        skippedActivities.add(activity);
                    }
                    rows.clear();    //clear the arrayList of rows in preparation for the next lot of data for the next activity
                }
            }

        } catch (IOException exception) {
            throw exception;
        }
        return validActivities;
    }

    public static void main(String[] args) {
        String filename = "seng202_2018_example_data.csv";  //example file for testing purposes
        FileImporter fileImporter = new FileImporter();
        ArrayList<Activity> validActivities = new ArrayList<>(); // Creates a list of all activities parsed in the file
        ArrayList<Activity> warningActivities = new ArrayList<>();
        ArrayList<Activity> skippedActivities = new ArrayList<>();
        ArrayList<DataRow> rows = new ArrayList<>();
        try {
            fileImporter.readFile(new File(filename), validActivities, warningActivities, skippedActivities);
        } catch (IOException e) {

        }


        for(Activity oneActivity : validActivities) {     //print out each activity's name. Purely for testing purposes
            System.out.println(oneActivity.getName());
            System.out.println(oneActivity.getRawData().size());
            System.out.println(oneActivity.getStartTime());
        }
    }

}
