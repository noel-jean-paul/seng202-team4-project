package seng202.team4.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ActivityRawData {
    private LocalDate date;
    private LocalDateTime time;
    private int heartRate;
    private double latitude;
    private double longitude;
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
    public ActivityRawData(LocalDate date, LocalDateTime time, int heartRate, double latitude, double longitude, double elevation) {
        this.date = date;
        this.time = time;
        this.heartRate = heartRate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate dateSet) {
        date = dateSet;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime timeSet) {
        time = timeSet;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRateSet) {
        heartRate = heartRateSet;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitudeSet) {
        latitude = latitudeSet;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitudeSet) {
        longitude = longitudeSet;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevationSet) {
        elevation = elevationSet;
    }

}
