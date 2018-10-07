package seng202.team4.model.data.displaymetrics;

/**
 * Class that stores a speed metric value and has a nice formatted toString method.
 * It is comparable by value.
 */
public class SpeedDisplayMetric implements Comparable<SpeedDisplayMetric> {

    /** The speed. */
    double speed;

    /** Creates a new SpeedDisplayMetric
     *
     * @param speed The speed to be stored.
     */
    public SpeedDisplayMetric(double speed) {
        this.speed = speed;
    }

    /**
     * Gets the value of speed.
     *
     * @return The speed.
     */
    public double getSpeed() {
        return speed;
    }

    /** Returns the speed as a nice formatted String.
     *
     * @return Formatted String of the speed metric.
     */
    @Override
    public String toString() {
        return String.format("%.1f km/h", speed);
    }

    /**
     * Compares this SpeedDisplayMetric with another on speed.
     *
     * @param otherSpeedMetric The other SpeedDisplayMetric to be compared with.
     * @return The result of comparing two SpeedDisplayMetric by speed.
     */
    @Override
    public int compareTo(SpeedDisplayMetric otherSpeedMetric) {
        return Double.compare(speed, otherSpeedMetric.getSpeed());
    }
}
