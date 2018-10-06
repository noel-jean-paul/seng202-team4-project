package seng202.team4.model.data;

import seng202.team4.model.data.DisplayMetrics.CalorieDisplayMetric;
import seng202.team4.model.data.DisplayMetrics.DistanceDisplayMetric;
import seng202.team4.model.data.DisplayMetrics.SpeedDisplayMetric;
import seng202.team4.model.data.enums.ActivityFields;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.WarningType;
import seng202.team4.model.database.DataStorer;
import seng202.team4.model.database.DataUpdater;
import seng202.team4.model.utilities.DataProcessor;
import seng202.team4.model.utilities.HealthWarning;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Activity implements Comparable<Activity> {

    /** Keywords that indicate an Activity is a walk.*/
    private static String[] walkKeyWords = {"walk", "hike", "stroll", "hiking"};
    /** Keywords that indicate an Activity is a run.*/
    private static String[] runKeyWords = {"run", "ran", "jog"};

    /* The combination of name and date must be unique for a profile */
    private String name;
    private LocalDate date;
    private ActivityType type;
    private LocalTime startTime;
    private Duration duration;
    private double distance;    // Total distance travelled in the activity in meters
    private double caloriesBurned;
    private double averageSpeed;
    private List<DataRow> rawData;
    private Profile owner;
    private int avgHeartRate;
    private int minHeartRate;
    private int maxHeartRate;

    public Activity(String name, String date, ActivityType type, String startTime,
                    String duration, double distance, double caloriesBurned) {
        this.name = name;
        this.date = LocalDate.parse(date);
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
        this.date = this.rawData.get(0).getDate();
        this.startTime = (this.rawData.get(0)).getTime();
        this.distance = DataProcessor.totalDistance(this.rawData);
        this.duration = DataProcessor.calculateDuration(this.rawData);
        this.averageSpeed = DataProcessor.calculateAverageSpeed(distance, this.duration);
        this.type = findActivityType(name);
        updateHeartRateAttributes();

        java.util.Collections.sort(this.rawData);   // ensure the data is in order
    }

    /** Update the activity attributes
     *  Used by the raw data editor
     *
     * @throws SQLException if an error occurred regarding the database
     */
    public void updateActivity() throws SQLException {
        // Only update the data if there are at least 2 datarows to prevent IndexOutOfBounds Exceptions
        if (rawData.size() >= 2) {
            setDate(this.rawData.get(0).getDate().toString());
            setStartTime(this.rawData.get(0).getTime().toString());
            setDistance(DataProcessor.totalDistance(this.rawData));
            setDuration(DataProcessor.calculateDuration(this.rawData).toString());
            setAverageSpeed(DataProcessor.calculateAverageSpeed(distance, this.duration));
            setType(findActivityType(name));
            updateHeartRateAttributes();
        }
    }

    /* Recalculate and update the min, max and average heart rates of the activity. */
    private void updateHeartRateAttributes() {
        avgHeartRate = calculateAvgHeartRate();
        minHeartRate = calculateMinHeartRate();
        maxHeartRate = calculateMaxHeartRate();
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
                getType() == activity.getType() &&
                Objects.equals(getStartTime(), activity.getStartTime()) &&
                Objects.equals(getDuration(), activity.getDuration()) &&
                Objects.equals(getRawData(), activity.getRawData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDate(), getType(), getStartTime(), getDuration(), getDistance(), getCaloriesBurned(), getAverageSpeed(), getRawData());
    }

    /** Compare an Activity to another based on Date and then based on Time in case of ties.
     *  Consistent with equals as defined by Comparable
     *
     * @param o the Activity to compare to
     * @return a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Activity o) {
        int dateCompare;
        int timeCompare;
         if ((dateCompare = this.getDate().compareTo(o.getDate())) != 0) {
             return dateCompare * -1;  // Reverse order to descending
         } else if ((timeCompare = this.getStartTime().compareTo(o.getStartTime())) != 0) {
             return timeCompare * -1;   // Reverse order to descending
         } else {
             return 0;  // Same date and startTime
         }
    }

    public String getName() {
        return name;
    }

    /** Set and update in database */
    public void setName(String name) throws SQLException {
        DataUpdater.updateActivities(Collections.singletonList(this),
                ActivityFields.name.toString(), name, true);
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    /** Set and update in database */
    public void setDate(String date) throws SQLException {
        DataUpdater.updateActivities(Collections.singletonList(this),
                ActivityFields.activityDate.toString(), date, true);
        this.date = LocalDate.parse(date);
        // Sort the activities which this activity belongs to as its order within the list may have changed
        Collections.sort(owner.getActivityList());
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    /** Set and update in database */
    public void setStartTime(String startTime) throws SQLException {
        DataUpdater.updateActivities(Collections.singletonList(this),
                ActivityFields.startTime.toString(), startTime, false);
        this.startTime = LocalTime.parse(startTime);
        // Sort the activities which this activity belongs to as its order within the list may have changed
        Collections.sort(owner.getActivityList());
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
        DataUpdater.updateActivities(Collections.singletonList(this),
                ActivityFields.duration.toString(), duration, false);
        this.duration = Duration.parse(duration);
    }

    /** Gets the distance of the activity */
    public double getDistance() {
        return distance;
    }

    /** Gets a string of the distance rounded to 0 decimal places. */
    public DistanceDisplayMetric getDistanceDisplayMetric() {
        return new DistanceDisplayMetric(distance);
    }

    public CalorieDisplayMetric getCaloriesDisplayMetric() {
        return new CalorieDisplayMetric(caloriesBurned);
    }

    /** Set and update in database */
    public void setDistance(double distance) throws SQLException {
        DataUpdater.updateActivities(Collections.singletonList(this),
                ActivityFields.distance.toString(), Double.toString(distance), false);
        this.distance = distance;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    /** Set and update in database */
    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public SpeedDisplayMetric getAverageSpeedDisplayMetric() {
        return new SpeedDisplayMetric(averageSpeed);
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    /** Set and update in database */
    public void setCaloriesBurned(double caloriesBurned) throws SQLException {
        DataUpdater.updateActivities(Collections.singletonList(this),
                ActivityFields.caloriesBurned.toString(), Double.toString(caloriesBurned), false);
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
        DataUpdater.updateActivities(Collections.singletonList(this),
                ActivityFields.type.toString(), type.toString(), false);
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


    /**
     * Getter method for the average heart rate.
     * @return the average heart rate.
     */
    public int getAvgHeartRate() {
        return avgHeartRate;
    }

    /**
     * Getter method for the minimum heart rate.
     * @return the minimum heart rate.
     */
    public int getMinHeartRate() {
        return minHeartRate;
    }

    /**
     * Getter method for the maximum heart rate.
     * @return the maximum heart rate.
     */
    public int getMaxHeartRate() {
        return maxHeartRate;
    }


    /** Add a dataRow to the rawData list in order and insert it into the database
     *
     * @param row the DataRow to be added
     */
    public void addDataRow(DataRow row) throws SQLException {
        rawData.add(row);
        java.util.Collections.sort(rawData);
        // Set the owner
        row.setOwner(this);

        DataStorer.insertDataRow(row);
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
        DataStorer.deleteDataRows(Collections.singletonList(row));
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

    /**
     * Creates all 3 different warning types for the activities, and if the warning in fact is a health issue, adds it to
     * the user's list of warnings.
     * @return whether or not a warning was added to the user's warning list.
     */
    public boolean addWarnings(boolean heartRatesCalculated) {
        if (!heartRatesCalculated) {
            this.avgHeartRate = calculateAvgHeartRate();
            this.minHeartRate = calculateMinHeartRate();
            this.maxHeartRate = calculateMaxHeartRate();
        }
        boolean hasWarning = false;
        ArrayList<HealthWarning> warnings = new ArrayList<>();
        warnings.add(new HealthWarning(this, owner, WarningType.Tachy, avgHeartRate, minHeartRate, maxHeartRate));
        warnings.add(new HealthWarning(this, owner, WarningType.Brady, avgHeartRate, minHeartRate, maxHeartRate));
        for (HealthWarning warning : warnings) {
            if (warning.isHealthRisk()) {
                owner.addWarning(warning);
                hasWarning = true;
            }
        }
        return hasWarning;
    }

    /**
     * Calculates the average heart rate of the user over the course of the activity.
     * @return the user's average heart rate.
     */
    private int calculateAvgHeartRate() {
        int avgBPM = 0;
        if (rawData.size() > 0) {
            for (DataRow row : rawData) {
                avgBPM += row.getHeartRate();
            }
            avgBPM /= rawData.size();
        }
        return avgBPM;
    }

    /**
     * Calculate the minimum heart rate of the user over the course of the activity.
     * @return the user's minimum heart rate.
     */
    private int calculateMinHeartRate() {
        int minBPM = Integer.MAX_VALUE;
        for (DataRow row : rawData) {
            if (row.getHeartRate() < minBPM) {
                minBPM = row.getHeartRate();
            }
        }
        return minBPM;
    }

    /**
     * Calculate the maximum heart rate of the user over the course of the activity.
     * @return the user's maximum heart rate.
     */
    private int calculateMaxHeartRate() {
        int maxBPM = Integer.MIN_VALUE;
        for (DataRow row : rawData) {
            if (row.getHeartRate() > maxBPM) {
                maxBPM = row.getHeartRate();
            }
        }
        return maxBPM;
    }
}