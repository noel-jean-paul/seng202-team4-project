package seng202.team4.model.data;

import seng202.team4.model.data.enums.DataRowFields;
import seng202.team4.model.database.DataUpdater;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Objects;

/**
 * Class that encapsulates a single raw data row for an Activity.
 */
public class DataRow implements Comparable<DataRow> {
    public static final double minLatitude = -90;
    public static final double maxLatitude = 90;
    public static final double minLongitude = -180;
    public static final double maxLongitude = 180;
    public static final double minElevation = -100;
    public static final double maxElevation = 10000;
    public static final double minHeartRate = 10;
    public static final double maxHeartRate = 250;


    private int number;
    private LocalDate date;
    private LocalTime time;
    private int heartRate;
    private double latitude;    // values in range -90 to 90 inclusive
    private double longitude;   // values in range -180 to 180 inclusive
    private double elevation;
    private Activity owner;


    /**
     * Constructor for the ActivityRawData class
     * @param number is the number of the row within the activity.
     * @param date is a date in dd/mm/yyyy format
     * @param time is a time in hh/mm/ss format
     * @param heartRate is the heart rate as an integer(BPM)
     * @param latitude is the latitude as a double
     * @param longitude is the longitude as a double
     * @param elevation is the elevation as a double
     */
    public DataRow(int number, String date, String time, int heartRate, double latitude, double longitude,
                   double elevation) {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy"); //formats the date correctly
        this.number = number;
        this.date = LocalDate.parse(date);
        this.time = LocalTime.parse(time);
        this.heartRate = heartRate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataRow dataRow = (DataRow) o;
        return getNumber() == dataRow.getNumber() &&
                getHeartRate() == dataRow.getHeartRate() &&
                Double.compare(dataRow.getLatitude(), getLatitude()) == 0 &&
                Double.compare(dataRow.getLongitude(), getLongitude()) == 0 &&
                Double.compare(dataRow.getElevation(), getElevation()) == 0 &&
                Objects.equals(getDate(), dataRow.getDate()) &&
                Objects.equals(getTime(), dataRow.getTime());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNumber(), getDate(), getTime(), getHeartRate(), getLatitude(), getLongitude(), getElevation());
    }

    /** Compare to another DataRow based on rowNumber
     *  Consistent with equals as defined by Comparable
     *
     * @param o the DataRow to compare to
     * @return a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(DataRow o) {
        int dateCompare;
        int timeCompare;
        if ((dateCompare = this.getDate().compareTo(o.getDate())) != 0) {
            return dateCompare;
        } else if ((timeCompare = this.getTime().compareTo(o.getTime())) != 0) {
            return timeCompare;
        } else {
            return 0;  // Same date and startTime
        }
    }

    /**
     * Gets the unique number of the DataRow.
     *
     * @return The number of the DataRow.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number of row and updates it in the database.
     *
     * @param number The new number of the DataRow.
     * @throws SQLException The Exception thrown if the number could not be updated in the database.
     */
    public void setNumber(int number) throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.rowNumber.toString(), Double.toString(number));
        this.number = number;
    }

    /**
     * Gets the date of the DataRow.
     *
     * @return The date of the DataRow as a LocalDate.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the DataRow and updates it in the database.
     *
     * @param date The new date of the DataRow.
     * @throws SQLException The Exception thrown if the date could not be updated in the database.
     */
    public void setDate(String date)throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.rowDate.toString(), date);
        this.date = LocalDate.parse(date);
        // Sort the raw data which this row belongs to as its order within the list may have changed
        Collections.sort(owner.getRawData());
    }

    /**
     * Gets the time of the DataRow.
     *
     * @return The time of the DataRow as a LocalTime.
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Sets the time of the DataRow and updates it in the database.
     * @param time The new time of the DataRow.
     * @throws SQLException The Exception thrown if the time could not be updated in the database.
     */
    public void setTime(String time) throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.time.toString(), time);
        this.time = LocalTime.parse(time);
        // Sort the raw data which this row belongs to as its order within the list may have changed
        Collections.sort(owner.getRawData());
    }

    /**
     * Gets the heart rate of the DataRow.
     *
     * @return The heart rate of the DataRow.
     */
    public int getHeartRate() {
        return heartRate;
    }

    /**
     * Sets the heart rate of the DataRow and updates it in the database.
     *
     * @param heartRate The new heart rate of the DataRow.
     * @throws SQLException The Exception thrown if the heart rate could not be updated in the database.
     */
    public void setHeartRate(int heartRate) throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.heartRate.toString(), Double.toString(heartRate));
        this.heartRate = heartRate;
    }

    /**
     * Gets the latitude of the DataRow.
     *
     * @return The latitude of the DataRow.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the DataRow and updates it in the database.
     *
     * @param latitude The new latitude of the DataRow.
     * @throws SQLException The Exception thrown if the latitude could not be updated in the database.
     */
    public void setLatitude(double latitude) throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.latitude.toString(), Double.toString(latitude));
        this.latitude = latitude;
    }

    /**
     * Gets the longitude of the DataRow.
     *
     * @return The longitude of the DataRow.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the latitude of the DataRow and updates it in the database.
     *
     * @param longitude The new longitude of the DataRow.
     * @throws SQLException The Exception thrown if the longitude could not be updated in the database.
     */
    public void setLongitude(double longitude) throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.longitude.toString(), Double.toString(longitude));
        this.longitude = longitude;
    }

    /**
     * Gets the elevation of the DataRow.
     *
     * @return The elevation of the DataRow as a double in meters.
     */
    public double getElevation() {
        return elevation;
    }

    /**
     * Sets the latitude of the DataRow and updates it in the database.
     *
     * @param elevation The new elevation of the DataRow.
     * @throws SQLException The Exception thrown if the elevation could not be updated in the database.
     */
    public void setElevation(double elevation) throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.elevation.toString(), Double.toString(elevation));
        this.elevation = elevation;
    }

    /**
     * Gets the Activity that owns the DataRow.
     *
     * @return The owner of the DataRow.
     */
    public Activity getOwner() {
        return owner;
    }

    /**
     * Sets the Activity that owns the DataRow.
     *
     * @param owner The new owner of the DataRow.
     */
    public void setOwner(Activity owner) {
        this.owner = owner;
    }

    /**
     * Method that returns the data row as a string that has the data and time of the DataRow.
     *
     * @return A String with the date and time of the DataRow.
     */
    @Override
    public String toString() {
        return String.format("(%s, %s)", date, time);
    }
}
