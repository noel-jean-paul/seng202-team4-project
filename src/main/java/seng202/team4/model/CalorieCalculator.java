package seng202.team4.model;

import seng202.team4.model.data.enums.ActivityType;

public class CalorieCalculator {

    private double speed;
    private int duration;
    private ActivityType activity;

    /**
     * CalorieCalculator contructor.
     * @param incomingSpeed the average speed of the user - in km/h - during the given time segment.
     * @param incomingDuration the duration of the time segment - in seconds.
     * @param incomingActivity the activity which the user is perfoming during the time segment.
     */
    CalorieCalculator(double incomingSpeed, int incomingDuration, ActivityType incomingActivity) {
        speed = incomingSpeed;
        duration = incomingDuration;
        activity = incomingActivity;
    }

    /**
     * Calculates the MET of the specific running activity through the user's speed.
     * @return the calculated MET.
     */
    private double runningMETCalculator() {

        return 1;
    }
}
