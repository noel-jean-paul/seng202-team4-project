package seng202.team4.model.utilities;

import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.enums.ActivityType;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;


public class FileImporter {

    public ArrayList readFile(File file, ArrayList<DataRow> rows, ArrayList<Activity> allActivities) {

        String line;   //empty line into which data will be read
        String csvSplitBy = ",";    //split on the comma
        String[] dataPoint; //create the string array to hold the line
        String activityName;    //Creates a string to hold the name of the activity being performed

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            line = bufferedReader.readLine();
            while (line != null) {  //while end of file has not been reached
                if (line.contains("#")) {   //if the line contains a hash then we know it is a line with the activity name on it
                    activityName = line.split(csvSplitBy)[1];   //grab the activity name and store it

                    line = bufferedReader.readLine();
                    int counter = 0;
                    while ((line != null) && (!(line.contains("#")))) { //read up until the next # symbol, signifying the start of a new activity
                        //the next lines split the line by comma into its unique fields
                        counter++;  //update the line counter
                        dataPoint = line.split(csvSplitBy);

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                        String unformattedDate = (dataPoint[0]);   //grabs date from line
                        LocalDate localDate = LocalDate.parse(unformattedDate, formatter);     //converts to LocalDate format
                        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String date = localDate.format(format1);   //converts this back to a string

                        String time = (dataPoint[1]);
                        int heartRate = (Integer.parseInt(dataPoint[2]));
                        double latitude = (Double.parseDouble(dataPoint[3]));
                        double longitude = (Double.parseDouble(dataPoint[4]));
                        double elevation = (Double.parseDouble(dataPoint[5]));

                        rows.add(new DataRow(counter, date, time, heartRate, latitude, longitude, elevation));   //add the data to a new ActivityRawData element
                        line = bufferedReader.readLine();
                    }

                    ArrayList<DataRow> rowCopy = new ArrayList<>(rows);     //create a copy of the row list

                    allActivities.add(new Activity(activityName, rowCopy));   //add each of the raw activity data for that specific activity, along with the activity name into an Activity object
                    rows.clear();    //clear the arrayList of rows in preparation for the next lot of data for the next activity
                }
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return allActivities;
    }

    public static void main(String[] args) {
        String filename = "seng202_2018_example_data.csv";  //example file for testing purposes
        FileImporter fileImporter = new FileImporter();
        ArrayList<Activity> allActivities = new ArrayList<>(); // Creates a list of all activities parsed in the file
        ArrayList<DataRow> rows = new ArrayList<>();
        fileImporter.readFile(new File(filename), rows, allActivities);

        for(Activity oneActivity : allActivities) {     //print out each activity's name. Purely for testing purposes
            System.out.println(oneActivity.getName());
            System.out.println(oneActivity.getRawData().size());
            System.out.println(oneActivity.getStartTime());
        }
    }

}
