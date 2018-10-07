package seng202.team4.model.utilities;

public class HealthWarningDetector {

    /**
     * Calculates the user's maximum heart rate for their age and evaluates their own heart rate against this max.
     * @return whether the user's heart rate was of their recommended maximum.
     */
    public boolean isTachyRisk(int maxHeartRate, int age) {
        if (maxHeartRate > (220 - age)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculates the user's minimum heart rate for their age bracket and evaluates their own heart rate against this minimum.
     * @return whether the user's heart rate was under the minimum.
     */
    public boolean isBradyRisk(int minHeartRate, int age) {
        if (age >= 18 && minHeartRate < 50) {
            return true;
        } else if (age < 18 && minHeartRate < 60) {
            return true;
        } else {
            return false;
        }
    }
}
