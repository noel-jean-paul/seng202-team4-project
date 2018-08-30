package seng202.team4.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class FileImporter {

    public ArrayList readFile(String filename, ArrayList<ActivityRawData> rows, ArrayList<Activity> allActivities) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");    //Formats the date correctly
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");  //Formats the time correctly
        String line;   //empty line into which data will be read
        String csvSplitBy = ",";    //split on the comma
        String[] dataPoint; //create the string array to hold the line
        String activityName;    //Creates a string to hold the name of the activity being performed

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            line = bufferedReader.readLine();
            while (line != null) {  //while end of file has not been reached
                if (line.contains("#")) {   //if the line contains a hash then we know it is a line with the activity name on it
                    activityName = line.split(csvSplitBy)[1];   //grab the activity name and store it
                    line = bufferedReader.readLine();
                    while ((line != null) && (!(line.contains("#")))) { //read up until the next # symbol, signifying the start of a new activity
                        //the next lines split the line by comma into its unique fields
                        dataPoint = line.split(csvSplitBy);
                        LocalDate date = LocalDate.parse(dataPoint[0], dateFormatter);
                        LocalTime time = LocalTime.parse(dataPoint[1], timeFormatter);
                        int heartRate = (Integer.parseInt(dataPoint[2]));
                        double latitude = (Double.parseDouble(dataPoint[3]));
                        double longitude = (Double.parseDouble(dataPoint[4]));
                        double elevation = (Double.parseDouble(dataPoint[5]));

                        rows.add(new ActivityRawData(date, time, heartRate, latitude, longitude, elevation));   //add the data to a new ActivityRawData element
                        line = bufferedReader.readLine();
                    }
                    for(ActivityRawData row : rows) {
                        System.out.println("Activity Name: " + activityName + " Heart rate: " + row.getHeartRate()); //this for loop was use purely for testing purposes and can be removed
                    }
                    allActivities.add(new Activity(activityName, rows));   //add each of the raw activity data for that specific activity, along with the activity name into an Activity object
                    rows.clear();    //clear the arrayList of rows in preparation for the next lot of data for the next activity
                }
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return allActivities;
    }


    public static void main(String[] args) {
        String filename = "seng202_2018_example_data.csv";
        FileImporter fileImporter = new FileImporter();
        ArrayList<Activity> allActivities = new ArrayList<>(); // Creates a list of all activities parsed in the file
        ArrayList<ActivityRawData> rows = new ArrayList<>();
        fileImporter.readFile(filename, rows, allActivities);

        for(Activity oneActivity : allActivities) {     //print out each activities name (also need to print out each row of data for each activity)
            System.out.println(oneActivity.getName());
            System.out.println(Math.round(oneActivity.getDistance())); // prints out the activity total distance covered rounded to int
        }
    }

}
