package seng202.team4.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

enum ActivityType{
    Walking, Running
}

public class Activity {
    /**
     * An activity class which consists of attributes detailing
     */
    private String name;
    private String description;
    private int id;
    private LocalDate Date;
    private LocalTime startTime;
    private LocalTime duration;
    private double distance;
    private double averageSpeed;
    private double caloriesBurned;
    private ActivityType type;
    private ArrayList<ActivityRawData> rawActivityList;

    /**
     * Constructor for the Activity class
     * @param name is the name of the activity as a string
     */
    public Activity(String name, ArrayList<ActivityRawData> rawActivityList) {
        this.name = name;
        this.rawActivityList = rawActivityList;
        calcTotalDistance();
        this.Date = (rawActivityList.get(0)).getDate();
        this.startTime = (rawActivityList.get(0)).getTime();
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    public ArrayList getRawActivityList() {
        return rawActivityList;
    }

    /**
     * A function to calculate the distance between two points
     * @param lat1 The latitude of the first point
     * @param lat2 The latitude of the second point
     * @param lon1 The longitude of the first point
     * @param lon2 The longitude of the second point
     * @param el1 The elevation of the first point
     * @param el2 The elevation of the second point
     * @return a double representing the distance between the two points in metres
     */
    public double calcDistance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * Calculates the total distance covered in meters for an activity
     */
    public void calcTotalDistance() {
        double totalDistance = 0;
        ActivityRawData previous = null;
        for (ActivityRawData data: rawActivityList) {
            if (!(previous == null)) {
                totalDistance += calcDistance(previous.getLatitude(), data.getLatitude(),
                        previous.getLongitude(), data.getLongitude(),
                        previous.getElevation(), data.getElevation());
            }
            previous = data;
        }
        this.distance = totalDistance;
    }

    /**
     *
     */
}