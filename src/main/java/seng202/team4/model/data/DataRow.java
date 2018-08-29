package seng202.team4.model.data;

import java.time.LocalDate;
import java.time.LocalTime;

public class DataRow {
    private int number;
    private LocalDate date;
    private LocalTime time;
    private int heartRate;
    private double latitude;    // values in range -90 to 90 inclusive
    private double longitude;   // values in range -180 to 180 inclusive
    private double altitude;

    public DataRow(int number, String date, String time, int heartRate, double latitude, double longitude, double altitude) {
        this.number = number;
        this.date = LocalDate.parse(date);
        this.time = LocalTime.parse(time);
        this.heartRate = heartRate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
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

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
}
