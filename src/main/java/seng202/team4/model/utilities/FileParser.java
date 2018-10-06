package seng202.team4.model.utilities;

import seng202.team4.model.data.Activity;
import seng202.team4.model.data.CorruptActivity;
import seng202.team4.model.data.DataRow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class FileParser {

    /**
     * Parse a given csv file into lists of activities.
     *
     * @param file the name of the file that is to be parsed.
     * @param validActivities ArrayList of that will be filled with the activities that were successfully parsed with no warnings or errors.
     * @param warningActivities  ArrayList that will be filled with the activities that were parsed with warnings.
     * @param skippedActivities ArrayList that will be filled with the activities that had to be skipped due to lack of valid data.
     * @return the arrayList of all activities' data points.
     */
    public ArrayList parseFileToActivites(File file, ArrayList<Activity> validActivities, ArrayList<CorruptActivity> warningActivities, ArrayList<Activity> skippedActivities) throws IOException {

        String line;   //empty line into which data will be read
        String csvSplitBy = ",";    //split on the comma
        String[] dataPoints; //create the string array to hold the line
        String activityName;    //Creates a string to hold the name of the activity being performed
        ArrayList<DataRow> rows = new ArrayList<DataRow>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            line = bufferedReader.readLine();
            while (line != null) {  //while end of file has not been reached
                if (line.contains("#")) {   //if the line contains a hash then we know it is a line with the activity name on it
                    try {
                        activityName = line.split(csvSplitBy)[1];   //grab the activity name and store it
                    } catch (IndexOutOfBoundsException e) {
                        line = bufferedReader.readLine();
                        continue;
                    }


                    line = bufferedReader.readLine();
                    int rowNumber = 1;
                    int numCorruptRows = 0;

                    while ((line != null) && (!(line.contains("#")))) { //read up until the next # symbol, signifying the start of a new activity
                        //the next lines split the line by comma into its unique fields
                        line = line.trim();
                        dataPoints = line.split(csvSplitBy);

                        if (line.length() == 0) {
                            line = bufferedReader.readLine();
                            continue;
                        }

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

                                if ((latitude >= -90 && latitude <= 90) && (longitude >= -180 && longitude <= 180)) {
                                    if (heartRate < 10 || heartRate > 250) {
                                        // TODO: If an invalid heart rate is found approximate what it should be based on other data rows.
                                        heartRate = 80; // Set heart rate to a sensible value.
                                    }

                                    if (elevation < -100 || elevation > 10000) {
                                        // TODO: If an invalid elevation is found approximate what it should be based on other data rows.
                                        elevation = 0;  // Set elevation to a sensible value.
                                    }
                                    rows.add(new DataRow(rowNumber, date, time, heartRate, latitude, longitude, elevation));   //add the data to a new ActivityRawData element
                                    rowNumber += 1;  //update the line rowNumber
                                } else {
                                    numCorruptRows += 1;
                                }

                            } catch (Exception e) {
                                numCorruptRows += 1;
                            }
                        } else {
                            numCorruptRows += 1;
                        }
                        line = bufferedReader.readLine();
                    }

                    ArrayList<DataRow> rowCopy = new ArrayList<>(rows);     //create a copy of the row list
                    if (rows.size() > 1) {
                        if (numCorruptRows > 0) {
                            CorruptActivity corruptActivity = new CorruptActivity(activityName, rowCopy, numCorruptRows);
                            warningActivities.add(corruptActivity);
                        } else {
                            Activity activity = new Activity(activityName, rowCopy);
                            validActivities.add(activity);   //add each of the raw activity data for that specific activity, along with the activity name into an Activity object
                        }
                    } else {
                        Activity activity = new Activity(activityName, rowCopy);
                        skippedActivities.add(activity);
                    }
                    rows.clear();    //clear the arrayList of rows in preparation for the next lot of data for the next activity
                } else {
                    line = bufferedReader.readLine();
                }
            }

        } catch (IOException exception) {
            throw exception;
        }
        return validActivities;
    }

//    public static void main(String[] args) {
//        String filename = "seng202_2018_example_data.csv";  //example file for testing purposes
//        FileParser fileParser = new FileParser();
//        ArrayList<Activity> validActivities = new ArrayList<>(); // Creates a list of all activities parsed in the file
//        ArrayList<Activity> warningActivities = new ArrayList<>();
//        ArrayList<Activity> skippedActivities = new ArrayList<>();
//        ArrayList<DataRow> rows = new ArrayList<>();
//        try {
//            fileParser.parseFileToActivites(new File(filename), validActivities, warningActivities, skippedActivities);
//        } catch (IOException e) {
//
//        }
//// TODO: 6/10/18 remove this main
//
//        for(Activity oneActivity : validActivities) {     //print out each activity's name. Purely for testing purposes
//            System.out.println(oneActivity.getName());
//            System.out.println(oneActivity.getRawData().size());
//            System.out.println(oneActivity.getStartTime());
//        }
//    }

}
