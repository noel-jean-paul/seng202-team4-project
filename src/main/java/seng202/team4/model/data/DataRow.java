package seng202.team4.model.data;

import seng202.team4.model.data.enums.DataRowFields;
import seng202.team4.model.database.DataUpdater;
import sun.text.resources.lt.CollationData_lt;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Objects;

public class DataRow implements Comparable<DataRow> {
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
        return Integer.compare(this.getNumber(), o.getNumber());
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.rowNumber.toString(), Double.toString(number));
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String date)throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.rowDate.toString(), date);
        this.date = LocalDate.parse(date);
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(String time) throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.time.toString(), time);
        this.time = LocalTime.parse(time);
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.heartRate.toString(), Double.toString(heartRate));
        this.heartRate = heartRate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.latitude.toString(), Double.toString(latitude));
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.longitude.toString(), Double.toString(longitude));
        this.longitude = longitude;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) throws SQLException {
        DataUpdater.updateDataRows(Collections.singletonList(this), DataRowFields.elevation.toString(), Double.toString(elevation));
        this.elevation = elevation;
    }

    public Activity getOwner() {
        return owner;
    }

    public void setOwner(Activity owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", number, date, owner.getName(), owner.getOwner().getFirstName());
    }
}
