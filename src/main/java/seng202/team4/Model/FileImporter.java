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

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = bufferedReader.readLine().split(csvSplitBy);   //read the first line
            String activityName = firstLine[1];
            System.out.println(firstLine[1]);
                while (!(line = bufferedReader.readLine()).contains("#")) { //read up until the next # symbol, signifying the start of a new activity
                    dataPoint = line.split(csvSplitBy);

                    LocalDate date = LocalDate.parse(dataPoint[0], dateFormatter);
                    LocalTime time = LocalTime.parse(dataPoint[1], timeFormatter);
                    int heartRate = (Integer.parseInt(dataPoint[2]));
                    double latitude = (Double.parseDouble(dataPoint[3]));
                    double longitude = (Double.parseDouble(dataPoint[4]));
                    double elevation = (Double.parseDouble(dataPoint[5]));
                    rows.add(new ActivityRawData(date, time, heartRate, latitude, longitude, elevation));
                }
                allActivities.add(new Activity(activityName, rows)); // Creates a new Activity class containing the activity name and all activity data and adds it to the list of all activity data.

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        System.out.println(rows.size());
        return rows;
    }


    public static void main(String[] args) {
        String filename = "seng202_2018_example_data.csv";
        FileImporter test = new FileImporter();
        ArrayList<Activity> allActivities = new ArrayList<>(); // Creates a list of all activities parsed in the file
        ArrayList<ActivityRawData> rows = new ArrayList<>();
        test.readFile(filename, rows, allActivities);
        for(ActivityRawData oneRow : rows) {
            System.out.println("Heart Rate: " + oneRow.getHeartRate() + " Latitude: " + oneRow.getLatitude() + " Longitude: " + oneRow.getLongitude() + " Elevation: " + oneRow.getElevation());
        }
        for(Activity oneActivity : allActivities) {
            System.out.println(oneActivity.getName());
        }
    }

}
