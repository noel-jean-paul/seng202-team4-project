package seng202.team4.model.data.displaymetrics;

/**
 * Class that stores a distance metric value and has a nice formatted toString method.
 * It is comparable by value.
 */
public class DistanceDisplayMetric implements Comparable<DistanceDisplayMetric> {

    /** Constant value of 1 Kilometer. */
    private static final double KILOMETER = 1000;

    /** The distance. */
    double distance;

    /** Creates a new DistanceDisplayMetric
     *
     * @param distance The distance to be stored.
     */
    public DistanceDisplayMetric(double distance) {
        this.distance = distance;
    }

    /**
     * Gets the value of distance.
     *
     * @return The distance.
     */
    public double getDistance() {
        return distance;
    }

    /** Returns the distance as a nice formatted String.
     * If the distance is over 1000 meters then Kilometers are used as meters.
     *
     * @return Formatted String of the distance metric.
     */
    @Override
    public String toString() {
        if (distance >= KILOMETER) {
            return String.format("%.1f km", distance/KILOMETER);
        } else {
            return String.format("%.0f m", distance);
        }
    }

    /**
     * Compares this DistanceDisplayMetric with another on distance.
     *
     * @param otherDistanceMetric The other DistanceDisplayMetric to be compared with.
     * @return The result of comparing two DistanceDisplayMetrics by distance.
     */
    @Override
    public int compareTo(DistanceDisplayMetric otherDistanceMetric) {
        return Double.compare(distance, otherDistanceMetric.getDistance());
    }
}
