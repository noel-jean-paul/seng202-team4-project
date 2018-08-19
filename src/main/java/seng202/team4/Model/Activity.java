package seng202.team4.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

enum ActivityType{
    Walking, Running
}

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
    private ActivityRawData rawData;


}