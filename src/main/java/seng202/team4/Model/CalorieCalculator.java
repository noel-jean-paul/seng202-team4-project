package seng202.team4.Model;

public class CalorieCalculator {

    private double speed;
    private double weight;
    private double duration;
    private double caloriesBurned;
    private double MET;
    private double deltaHeight;
    private ActivityType activity;

    /**
     * CalorieCalculator contructor.
     * @param incomingWeight the weight of the user in kilograms.
     * @param incomingSpeed the average speed of the user - in km/h - during the given time segment.
     * @param incomingDuration the duration of the time segment - in minutes.
     * @param incomingActivity the activity which the user is perfoming during the time segment.
     */
    CalorieCalculator(double incomingWeight, double incomingSpeed, double incomingDuration, ActivityType incomingActivity) {
        speed = incomingSpeed;
        duration = incomingDuration / 60;
        weight = incomingWeight;
        activity = incomingActivity;
        caloriesBurned = calculateCalories();
    }

    private double calculateCalories() {
        if (activity == ActivityType.Walking) {
            MET = walkingMETCalculator();
        } else {
            MET = runningMETCalculator();
        }
        double result = duration * MET * 3.5 * weight / 200;
        return result;
    }

    private double walkingMETCalculator() {
        double localMET = 0;
        if (speed < 3.2) {
            localMET = 2.0;
        } else if (3.2 <= speed && speed < 4.0) {
            localMET = 2.8;
        } else if (4.0 <= speed && speed < 4.5) {
            localMET = 3.0;
        } else if (4.5 <= speed && speed < 5.2) {
            localMET = 3.5;
        } else if (5.2 <= speed && speed < 5.6) {
            localMET = 4.3;
        } else if (5.6 <= speed && speed < 6.4) {
            localMET = 5.0;
        } else if (6.5 <= speed && speed < 7.2) {
            localMET = 7.0;
        } else {
            localMET = 8.3;
        }
        return localMET;
    }

    /**
     * Calculates the MET of the specific running activity through the user's speed.
     * @return the calculated MET.
     */
    private double runningMETCalculator() {
        double localMET = 0;
        if (speed < 6.5) {
            localMET = 6.0;
        } else if (6.5 <= speed && speed < 8.0) {
            localMET = 8.3;
        } else if (8.0 <= speed && speed < 9.6) {
            localMET = 9.0;
        } else if (9.6 <= speed && speed < 10.8) {
            localMET = 9.8;
        } else if (10.8 <= speed && speed < 11.2) {
            localMET = 10.5;
        } else if (10.8 <= speed && speed < 12.9) {
            localMET = 11.8;
        } else if (12.9 <= speed && speed < 13.8) {
            localMET = 12.3;
        } else if (13.8 <= speed && speed < 14.5) {
            localMET = 12.8;
        } else if (14.5 <= speed && speed < 16.1) {
            localMET = 14.5;
        } else if (16.1 <= speed && speed < 17.7) {
            localMET = 16;
        } else if (17.7 <= speed && speed < 19.3) {
            localMET = 19.0;
        } else if (19.3 <= speed && speed < 20.9) {
            localMET = 19.8;
        } else {
            localMET = 23.0;
        }
        return localMET;
    }

    public double getCalories() {
        return caloriesBurned;
    }

    public double getMET() {
        return MET;
    }
}
