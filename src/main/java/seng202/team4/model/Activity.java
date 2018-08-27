package seng202.team4.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class Activity {
    private String name;
    private String description;
    private int id;
    private LocalDate Date;
    private LocalDateTime startTime;
    private LocalDateTime duration;
    private double distance;
    private double averageSpeed;
    private double caloriesBurned;
    private ActivityType type;
    private Collection<DataRow> rawData;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}