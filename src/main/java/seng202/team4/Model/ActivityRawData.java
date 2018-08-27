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
}
