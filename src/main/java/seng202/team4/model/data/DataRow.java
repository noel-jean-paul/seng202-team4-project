package seng202.team4.model.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DataRow {
    private int number;
    private LocalDate date;
    private LocalTime time;
    private int heartRate;
    private double latitude;    // values in range -90 to 90 inclusive
    private double longitude;   // values in range -180 to 180 inclusive
    private double elevation;


    /**
     * Constructor for the ActivityRawData class
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }
}
