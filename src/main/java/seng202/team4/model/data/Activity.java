package seng202.team4.model.data;

import seng202.team4.model.data.enums.ActivityType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Activity {
    /* The combination of name and date must be unique for a profile */
    private String name;
    private LocalDate date;
    private String description;
    private ActivityType type;
    private LocalTime startTime;
    private LocalTime duration;
    private double distance;
    private double caloriesBurned;
    private double averageSpeed;
    private List<DataRow> rawData;

    public Activity(String name, String date, String description, ActivityType type, String startTime,
                    String duration, double distance, double caloriesBurned, double averageSpeed) {
        this.name = name;
        this.date = LocalDate.parse(date);
        this.description = description;
        this.type = type;
        this.startTime = LocalTime.parse(startTime);
        this.duration = LocalTime.parse(duration);
        this.distance = distance;
        this.caloriesBurned = caloriesBurned;
        this.averageSpeed = averageSpeed;
        rawData = new ArrayList<>();
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
                Objects.equals(getDuration(), activity.getDuration());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getDate(), getDescription(), getType(), getStartTime(), getDuration(), getDistance(), getCaloriesBurned(), getAverageSpeed());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Collection<DataRow> getRawData() {
        return rawData;
    }
}