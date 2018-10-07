package seng202.team4.model.data.displaymetrics;

/**
 * Class that stores a calorie metric value and has a nice formatted toString method.
 * It is comparable by value.
 */
public class CalorieDisplayMetric implements Comparable<CalorieDisplayMetric> {
    
    /** The calorie. */
    double calorie;

    /** Creates a new CalorieDisplayMetric
     *
     * @param calorie The calorie to be stored.
     */
    public CalorieDisplayMetric(double calorie) {
        this.calorie = calorie;
    }

    /**
     * Gets the value of calorie.
     *
     * @return The calorie.
     */
    public double getCalorie() {
        return calorie;
    }

    /** Returns the calorie as a nice formatted String.
     *
     * @return Formatted String of the calorie metric.
     */
    @Override
    public String toString() {
        return String.format("%.1f cal", calorie);
    }

    /**
     * Compares this CalorieDisplayMetric with another on calorie.
     *
     * @param otherCalorieMetric The other CalorieDisplayMetric to be compared with.
     * @return The result of comparing two CalorieDisplayMetric by calorie.
     */
    @Override
    public int compareTo(CalorieDisplayMetric otherCalorieMetric) {
        return Double.compare(calorie, otherCalorieMetric.getCalorie());
    }
}
