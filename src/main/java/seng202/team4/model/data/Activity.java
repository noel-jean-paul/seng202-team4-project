package seng202.team4.model.data;

import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.DataStorer;
import seng202.team4.model.database.DataUpdater;
import seng202.team4.model.utilities.DataProcessor;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Activity implements Comparable<Activity> {

    /** Keywords that indicate an Activity is a walk.*/
    private static String[] walkKeyWords = {"walk", "hike", "stroll", "hiking"};
    /** Keywords that indicate an Activity is a run.*/
    private static String[] runKeyWords = {"run", "ran", "jog"};

    /* The combination of name and date must be unique for a profile */
    private String name;
    private LocalDate date;
    private String description;
    private ActivityType type;
    private LocalTime startTime;
    private Duration duration;
    private double distance;
    private double caloriesBurned;
    private double averageSpeed;
    private List<DataRow> rawData;
    private Profile owner;

    public Activity(String name, String date, String description, ActivityType type, String startTime,
                    String duration, double distance, double caloriesBurned) {
        this.name = name;
        this.date = LocalDate.parse(date);
        this.description = description;
        this.type = type;

        this.startTime = LocalTime.parse(startTime);
        this.duration = Duration.parse(duration);
        this.distance = distance;
        this.caloriesBurned = caloriesBurned;
        this.averageSpeed = DataProcessor.calculateAverageSpeed(distance, this.duration);
        this.rawData = new ArrayList<>();
    }

    /**
     * Constructor for the Activity class
     * @param name is the name of the activity as a string
     */
    public Activity(String name, ArrayList<DataRow> rawActivityList) {
        this.name = name;
        this.rawData = rawActivityList;
        java.util.Collections.sort(this.rawData);   // ensure the data is in order
        this.date = (rawActivityList.get(0)).getDate();
        this.startTime = (rawActivityList.get(0)).getTime();
        this.distance = DataProcessor.totalDistance(rawActivityList);
        this.duration = DataProcessor.calculateDuration(rawActivityList);
        this.averageSpeed = DataProcessor.calculateAverageSpeed(distance, this.duration);
        this.type = findActivityType(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Double.compare(activity.getDistance(), getDistance()) == 0 &&
                Double.compare(activity.getCaloriesBurned(), getCaloriesBurned()) == 0 &&
                Double.compare(activity.getAverageSpeed(), getAverageSpeed()) == 0 &&
                Objects.equals(getName(), activity.getName()) &&
                Objects.equals(getDate(), activity.getDate()) &&
                Objects.equals(getDescription(), activity.getDescription()) &&
                getType() == activity.getType() &&
                Objects.equals(getStartTime(), activity.getStartTime()) &&
                Objects.equals(getDuration(), activity.getDuration()) &&
                Objects.equals(getRawData(), activity.getRawData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDate(), getDescription(), getType(), getStartTime(), getDuration(), getDistance(), getCaloriesBurned(), getAverageSpeed(), getRawData());
    }

    /** Compare an Activity to another based on Date and then based on Time in case of ties.
     *  Consistent with equals as defined by Comparable
     *
     * @param o the ProfileKey to compare to
     * @return a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Activity o) {
        int dateCompare;
        int timeCompare;
         if ((dateCompare = this.getDate().compareTo(o.getDate())) != 0) {
             return dateCompare;
         } else if ((timeCompare = this.getStartTime().compareTo(o.getStartTime())) != 0) {
             return timeCompare;
         } else {
             return 0;  // Same date and startTime
         }
    }

    public String getName() {
        return name;
    }

    /** Set and update in database */
    public void setName(String name) throws SQLException {
        DataUpdater.updateActivity(this,"name", name);
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    /** Set and update in database */
    public void setDescription(String description) throws SQLException {
        DataUpdater.updateActivity(this,"description", description);
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    /** Set and update in database */
    public void setDate(String date) throws SQLException {
        DataUpdater.updateActivity(this,"activityDate", date);
        this.date = LocalDate.parse(date);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    /** Set and update in database */
    public void setStartTime(String startTime) throws SQLException {
        DataUpdater.updateActivity(this,"startTime", startTime);
        this.startTime = LocalTime.parse(startTime);
    }

    public Duration getDuration() {
        return duration;
    }

    public String getDurationString() {
        long totalSeconds = duration.getSeconds();
        int seconds = (int) totalSeconds % 60;
        int minutes = (int) (totalSeconds/60) % 60;
        int hours = (int) totalSeconds/3600;

        return String.format("%2s:%2s:%2s", hours, minutes, seconds).replace(' ', '0');
    }

    /** Set and update in database */
    public void setDuration(String duration) throws SQLException {
        DataUpdater.updateActivity(this,"duration", duration);
        this.duration = Duration.parse(duration);
    }

    /** Gets the distance of the activity */
    public double getDistance() {
        return distance;
    }

    /** Gets a string of the distance rounded to 0 decimal places. */
    public String getDistanceDisplayString() {
        return String.format("%.0f m", distance);
    }

    public String getCaloriesDisplayString() {
        return String.format("%.1f", caloriesBurned);
    }

    /** Set and update in database */
    public void setDistance(double distance) throws SQLException {
        DataUpdater.updateActivity(this,"distance", Double.toString(distance));
        this.distance = distance;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    /** Set and update in database */
    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getAverageSpeedDisplayString() {
        return String.format("%.1f km/h", averageSpeed);
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    /** Set and update in database */
    public void setCaloriesBurned(double caloriesBurned) throws SQLException {
        DataUpdater.updateActivity(this,"caloriesBurned", Double.toString(caloriesBurned));
        this.caloriesBurned = caloriesBurned;
    }

    public void setCaloriesBurnedValue(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public ActivityType getType() {
        return type;
    }

    /** Set and update in database */
    public void setType(ActivityType type) throws SQLException {
        DataUpdater.updateActivity(this,"type", type.toString());
        this.type = type;
    }

    public List<DataRow> getRawData() {
        return rawData;
    }


    public Profile getOwner() {
        return owner;
    }

    public void setOwner(Profile owner) {
        this.owner = owner;
    }

    /** Add a dataRow to the rawData list in order and insert it into the database
     *
     * @param row the DataRow to be added
     */
    public void addDataRow(DataRow row) throws SQLException {
        rawData.add(row);
        java.util.Collections.sort(rawData);
        DataStorer.insertDataRow(row, this);

        // Set the owner
        row.setOwner(this);
    }

    /** Adds all dataRows of the specified collection to rawData and sorts the rawData list
     *  Intended for use by DataLoader as it does not insert into the database
     *
     * @param rows the collection to be added
     */
    public void addAllDataRows(Collection<DataRow> rows) {
        rawData.addAll(rows);
        java.util.Collections.sort(rawData);
    }

    /** Remove the dataRow from the rawData list and the database
     *
     * @param row the dataRow to be removed
     */
    public void removeDataRow(DataRow row) throws SQLException {
        rawData.remove(row);
        DataStorer.deleteDataRow(row, this);
    }


    //The functions detailed below will likely be moved to DataProcessor -Matt M


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
     * Finds the ActivityType of an Activity from its name.
     * It does this by searching the name for keywords that may
     * indicated whether an Activity is a walk or a run. If no
     * keywords are found or the name has keywords from both
     * the run and walk types then the other activity type
     * is returned.
     *
     * @param activityName the name of the activity
     * @return The ActivityType found from the activity name.
     */
    public static ActivityType findActivityType(String activityName) {
        ActivityType type = ActivityType.Other;
        boolean isWalk = false;
        boolean isRun = false;

        // Looks for walk key words in the name.
        for (String walkKeyWord: walkKeyWords) {
            if (activityName.toLowerCase().contains(walkKeyWord)) {
                type = ActivityType.Walk;
                isWalk = true;
            }
        }

        // Looks for run key words in the name.
        for (String runKeyWord: runKeyWords) {
            if (activityName.toLowerCase().contains(runKeyWord)) {
                type = ActivityType.Run;
                isRun = true;
            }
        }

        // Checks whether both walk and run key words were found in the name.
        if (isWalk && isRun) {
            type = ActivityType.Other;
        }

        return type;
    }

//    /**
//     * Calculates the total distance covered in meters for an activity
//     */
//    public void calcTotalDistance() {
//        double totalDistance = 0;
//        ActivityRawData previous = null;
//        for (ActivityRawData data: rawActivityList) {
//            if (!(previous == null)) {
//                totalDistance += calcDistance(previous.getLatitude(), data.getLatitude(),
//                        previous.getLongitude(), data.getLongitude(),
//                        previous.getElevation(), data.getElevation());
//            }
//            previous = data;
//        }
//        this.distance = totalDistance;
//    }
}