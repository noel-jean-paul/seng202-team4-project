package seng202.team4.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

enum ActivityType{
    Walking, Running
}

public class Activity {
    /**
     * An activity class which consists of attributes detailing
     */
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
    private ArrayList<ActivityRawData> rawActivityList;

    /**
     * Constructor for the Activity class
     * @param name is the name of the activity as a string
     */
    public Activity(String name, ArrayList<ActivityRawData> rawActivityList) {
        this.name = name;
        this.rawActivityList = rawActivityList;
    }

    public String getName() {
        return name;
    }

    public ArrayList getRawActivityList() {
        return rawActivityList;
    }
}