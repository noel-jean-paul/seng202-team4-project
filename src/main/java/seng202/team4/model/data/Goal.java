package seng202.team4.model.data;

import seng202.team4.model.data.DisplayMetrics.DistanceDisplayMetric;
import seng202.team4.model.data.enums.GoalFields;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.database.DataUpdater;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;


public class Goal implements Comparable<Goal>, CalendarItem {
    /** The minimum distance for a distance goal. */
    public static final double MIN_GOAL_DISTANCE = 1.0;

    /** The maximum distance for a distance goal. */
    public static final double MAX_GOAL_DISTANCE = 10000.0;

    /** The minimum calories for a calories goal. */
    public static final int MIN_GOAL_CALORIES = 50;

    /** The maximum calories for a calories goal. */
    public static final int MAX_GOAL_CALORIES = 100000;

    /** The minimum duration for a duration goal. */
    public static final Duration MIN_GOAL_DURATION = Duration.ofMinutes(15);

    /** The maximum duration for a duration goal. */
    public static final Duration MAX_GOAL_DURATION = Duration.ofHours(1000);

    /** The minimum period of time a goal can last in days. */
    public static final long MIN_GOAL_PERIOD = 1;

    /** The maximum period of time a goal can last in days. */
    public static final long MAX_GOAL_PERIOD = 365;



    private int number;
    private double progress;
    private GoalType type;
    private LocalDate creationDate;
    private LocalDate expiryDate;
    private LocalDate completionDate;
    private double goalDistance;    // total distance of the goal in kms
    private Duration goalDuration;
    private int caloriesBurned;
    private Profile owner;
    private boolean current;

    /**
     * Base constructor for goals
     *
     * @param number The unique number of the goal.
     * @param progress The progress of the goal as a percentage.
     * @param type The type of the goal as a GoalType.
     * @param creationDate The creation date of the Goal.
     * @param expiryDate The expiry Date of the Goal.
     * @param goalDistance The target distance of the Goal.
     * @param goalDuration The target duration of the Goal.
     * @param caloriesBurned The calories burned target of the Goal.
     */
    private Goal(int number, double progress, GoalType type, String creationDate, String expiryDate,
                double goalDistance, String goalDuration, int caloriesBurned) {
        this.number = number;
        this.progress = progress;
        this.type = type;
        this.creationDate = LocalDate.parse(creationDate);
        this.completionDate = LocalDate.MAX;
        this.expiryDate = LocalDate.parse(expiryDate);
        this.goalDistance = goalDistance;
        this.goalDuration = Duration.parse(goalDuration);
        this.caloriesBurned = caloriesBurned;
        this.current = true;    // by default the goal is current
    }

    /**
     * Constructor for a distance goal.
     *
     * @param number The unique number of the goal.
     * @param progress The progress of the goal as a percentage.
     * @param type The type of the goal as a GoalType.
     * @param creationDate The creation date of the Goal.
     * @param expiryDate The expiry Date of the Goal.
     * @param goalDistance The target distance of the Goal.
     */
    public Goal(int number, double progress, GoalType type, String creationDate, String expiryDate,
                double goalDistance) {
        this(number, progress, type, creationDate, expiryDate, goalDistance, "PT0M", 0);
    }

    /**
     * Constructor for a duration goal.
     *
     * @param number The unique number of the goal.
     * @param progress The progress of the goal as a percentage.
     * @param type The type of the goal as a GoalType.
     * @param creationDate The creation date of the Goal.
     * @param expiryDate The expiry Date of the Goal.
     * @param goalDuration The target duration of the Goal.
     */
    public Goal(int number, double progress, GoalType type, String creationDate, String expiryDate,
                String goalDuration) {
        this(number, progress, type, creationDate, expiryDate, 0, goalDuration, 0);
    }

    /**
     * Constructor for a calories goal.
     *
     * @param number The unique number of the goal.
     * @param progress The progress of the goal as a percentage.
     * @param type The type of the goal as a GoalType.
     * @param creationDate The creation date of the Goal.
     * @param expiryDate The expiry Date of the Goal.
     * @param caloriesBurned The calories burned target of the Goal.
     */
    public Goal(int number, double progress, GoalType type, String creationDate, String expiryDate,
                int caloriesBurned) {
        this(number, progress, type, creationDate, expiryDate, 0, "PT0M", caloriesBurned);
    }

    /**
     * Constructor for loading goals from the database.
     * @param number The unique number of the goal.
     * @param progress The progress of the goal as a percentage.
     * @param type The type of the goal as a GoalType.
     * @param creationDate The creation date of the Goal.
     * @param expiryDate The expiry Date of the Goal.
     * @param completionDate The completion date of the Goal.
     * @param goalDistance The target distance of the Goal.
     * @param goalDuration The target duration of the Goal.
     * @param caloriesBurned The calories burned target of the Goal.
     * @param current Whether the Goal is current or not.
     */
    public Goal(int number, double progress, GoalType type, String creationDate, String expiryDate,
                String completionDate, String goalDuration, double goalDistance, int caloriesBurned, boolean current) {
        this(number, progress, type, creationDate, expiryDate, goalDistance, goalDuration, caloriesBurned);
        this.completionDate = LocalDate.parse(completionDate);
        this.current = current;

    }

    /**
     * Checks whether this Goal is equal to another.
     * @param o The other goal to compare with.
     * @return true if the two goals are equal false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return getNumber() == goal.getNumber() &&
                Double.compare(goal.getProgress(), getProgress()) == 0 &&
                Double.compare(goal.getGoalDistance(), getGoalDistance()) == 0 &&
                Objects.equals(goal.getGoalDuration(), getGoalDuration()) &&
                getCaloriesBurned() == goal.getCaloriesBurned() &&
                getType() == goal.getType() &&
                Objects.equals(getCreationDate(), goal.getCreationDate()) &&
                Objects.equals(getExpiryDate(), goal.getExpiryDate()) &&
                Objects.equals(getCompletionDate(), goal.getCompletionDate()) &&
                Objects.equals(getDescription(), goal.getDescription()) &&
                Objects.equals(isCurrent(), goal.isCurrent());
    }

    /**
     * Generates a hash code for the Goal.
     *
     * @return The hash code of the Goal.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getProgress(), getType(), getCreationDate(), getExpiryDate(), getCompletionDate(), getDescription(), getGoalDistance(), getGoalDuration());
    }

    /** Compare to another Goal based on goalNumber
     *  Consistent with equals as defined by Comparable
     *
     * @param o the Goal to compare to
     * @return a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Goal o) {
        return Double.compare(this.getProgress(), o.getProgress()) * -1;    // descending order
    }

    @Override
    public String toString() {
        return "Goal{" +
                "number=" + number +
                '}';
    }

    /**
     * Gets the unique number of the Goal.
     * @return The number of the Goal.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number of the Goal and updates it in the database.
     *
     * @param number The new number of the Goal.
     * @throws SQLException Exception that is thrown if the number could not be updated in the database.
     */
    public void setNumber(int number) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.goalNumber.toString(), Double.toString(number));
        this.number = number;
    }

    /**
     * Gets the progress of the Goal.
     *
     * @return The progress of the goal as a percentage.
     */
    public double getProgress() {
        return progress;
    }

    /**
     * Sets the progress of the Goal and updates it in the database.
     *
     * @param progress The new progress of the Goal.
     * @throws SQLException Exception thrown if the progress could not be updated in the database.
     */
    public void setProgress(double progress) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.progress.toString(), Double.toString(progress));
        this.progress = progress;
    }

    /**
     * Updates the progress value with out storing it in the database.
     *
     * @param progress The new progress of the Goal.
     */
    public void updateProgressValue(double progress) {
        this.progress = progress;
    }

    /**
     * Gets the type of the Goal.
     *
     * @return The type of the goal as a GoalType.
     */
    public GoalType getType() {
        return type;
    }

    /**
     * Sets the type of the goal and updates it in the database.
     *
     * @param type The new GoalType of the Goal.
     * @throws SQLException Exception thrown if the type could not be updated in the database.
     */
    public void setType(GoalType type) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.type.toString(), String.valueOf(type));
        this.type = type;
    }

    /**
     * Gets the creation date of the Goal.
     *
     * @return The creation date of the Goal as a LocalDate.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the goal and updates it in the database.
     *
     * @param creationDate The new creation date of the Goal.
     * @throws SQLException Exception thrown if the creation date could not be updated in the database.
     */
    public void setCreationDate(String creationDate) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.creationDate.toString(), creationDate);
        this.creationDate = LocalDate.parse(creationDate);
    }

    /**
     * Gets the expiry date of the Goal.
     *
     * @return The expiry date as a local Date.
     */
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the goal and updates it in the database.
     *
     * @param expiryDate The new expiry date of the Goal.
     * @throws SQLException Exception thrown if the expiry date could not be updated in the database.
     */
    public void setExpiryDate(String expiryDate) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this),GoalFields.expiryDate.toString(), expiryDate);
        this.expiryDate = LocalDate.parse(expiryDate);
    }

    /**
     * Gets the completion date of the Goal.
     *
     * @return The completion date of the Goal as a LocalDate.
     */
    public LocalDate getCompletionDate() {
        return completionDate;
    }

    /**
     * Sets the completion date of the goal and updates it in the database.
     *
     * @param completionDate The new completion date of the Goal.
     * @throws SQLException Exception thrown if the completion date could not be updated in the database.
     */
    public void setCompletionDate(String completionDate) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this),GoalFields.completionDate.toString(), completionDate);
        this.completionDate = LocalDate.parse(completionDate);
    }

    /**
     * Gets the target calories burned of the Goal.
     * @return The target calories burned.
     */
    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    /**
     * Sets the calories burned target of the goal and updates it in the database.
     *
     * @param caloriesBurned The new calories burned target of the Goal.
     * @throws SQLException Exception thrown if the calories burned target could not be updated in the database.
     */
    public void setCaloriesBurned(int caloriesBurned) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.caloriesBurned.toString(),
                Integer.toString(caloriesBurned));
        this.caloriesBurned = caloriesBurned;
    }

    /** Get the description of the goal (regenerates using generateDescription)
     *
     * @return the description of the goal
     */
    public String getDescription() {
        return generateDescription();
    }

    /**
     * Gets the distance target of the Goal.
     *
     * @return The distance target of the Goal in kilometers.
     */
    public double getGoalDistance() {
        return goalDistance;
    }

    /**
     * Sets the distance target of the goal and updates it in the database.
     *
     * @param goalDistance The new distance target of the Goal.
     * @throws SQLException Exception thrown if the distance target could not be updated in the database.
     */
    public void setGoalDistance(double goalDistance) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this),GoalFields.goalDistance.toString(), Double.toString(goalDistance));
        this.goalDistance = goalDistance;
    }

    /**
     * Gets the duration target of the Goal
     *
     * @return The target duration of the Goal as a Duration.
     */
    public Duration getGoalDuration() {
        return goalDuration;
    }

    /**
     * Sets the duration target of the goal and updates it in the database.
     *
     * @param goalDuration The new duration target of the Goal.
     * @throws SQLException Exception thrown if the duration target could not be updated in the database.
     */
    public void setGoalDuration(Duration goalDuration) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.goalDuration.toString(), goalDuration.toString());
        this.goalDuration = goalDuration;
    }

    /**
     * Check if the goal is complete (progress = 100)
     *
     * @return true if the goal is complete, false otherwise
     */
    public boolean isComplete() {
        return progress == 100;
    }

    /**
     * Gets the time remaining until the Goal expires.
     *
     * @return The number of days until the Goal expires.
     */
    public int getTimeRemaining() {
        return Period.between(expiryDate, LocalDate.now()).getDays();
    }

    /**
     * Gets the Profile that owns the Goal.
     *
     * @return The owner of the goal.
     */
    public Profile getOwner() {
        return owner;
    }

    /**
     * Sets the Profile that owns the Goal.
     *
     * @param owner The new owner of the goal.
     */
    public void setOwner(Profile owner) {
        this.owner = owner;
    }

    /**
     * Checks whether the Goal is current.
     *
     * @return true if the goal is current, false otherwise.
     */
    public boolean isCurrent() {
        return current;
    }

    /** Set the value of current to the value passed in and update it in the database
     *
     * @param current the new value for current as a boolean
     * @throws SQLException if an error occurred regarding the database
     */
    public void setCurrent(boolean current) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.current.toString(), Boolean.toString(current));
        this.current = current;
    }

    /** Sets the value of current to the value passed in
     *
     * @param current new value for current as a boolean
     */
    public void updateCurrentValue(boolean current) {
        this.current = current;
    }

    /** Create a description for the goal based of its paramers
     *
     * @return a description of the goal as a String.
     */
    private String generateDescription() {
        String description;
        String suffix;
        description = "";

        if (isDistanceGoal()) {
            // Describe the type of the goal, Walk or Run
            description = String.format("%s ", getType());
            // Use the distanceDisplayMetric class to format the distance value part of the string
            DistanceDisplayMetric distanceDisplayMetric = new DistanceDisplayMetric(getGoalDistance() * 1000);   // Pass a meter value for distance
            description += distanceDisplayMetric.toString();
        } else if (isDurationGoal()) {
            // Get unit of the number of hours
            String dayUnit = getHourUnit(getGoalDuration());
            description = String.format("%s for %d %s and %d minutes",
                    getType().toString(), getGoalDuration().toHours(), dayUnit,
                    getGoalDuration().toMinutes() - getGoalDuration().toHours() * 60);    // toMinutes() includes the hours as well so they must be subtracted out
        } else if (isCaloriesGoal()) {
            // Set an appropriate ending to the description based on the type of the goal
            if (getType() == GoalType.Run) {
                suffix = "ning";
            } else {
                suffix = "ing"; // Walking does not have an n in it
            }
            description = String.format("Burn %s calories while %s%s",
                    getCaloriesBurned(), getType().toString().toLowerCase(), suffix);
        }
        return description;
    }

    /** Increase the goal's progress by an amount up to a max of 100
     *
     * @param amount the amount to increment the goal by as a Double (percentage not decimal)
     */
    public void incrementProgress(double amount) {
        progress += amount;
        if (progress > 100) {
            progress = 100;
        }
    }

    /** Check whether a goal has been completed
     *
     * @return true if the goal is completed, false otherwise
     */
    public boolean isCompleted() {
        return progress == 100;
    }

    /** Check whether the goal is a distance goal
     *
     * @return true if the goal is a distance goal (goalDstance not zero), false otherwise
     */
    public boolean isDistanceGoal() {
        return goalDistance != 0;
    }

    /** Check whether the goal is a duration goal
     *
     * @return true if the goal is a duration goal (goalDuration not zero), false otherwise
     */
    public boolean isDurationGoal() {
        return goalDuration != Duration.ZERO;
    }

    /** Check whether the goal is a calories goal
     *
     * @return true if the goal is a calories goal, false otherwise
     */
    public boolean isCaloriesGoal() {
        return caloriesBurned != 0;
    }

    /** Return a string stating the amount of units this goal has with the appropriate unit. The amount
     *  may be the total amount or the current amount accredited towards the total.
     *
     * @param type the type of the amount - either "current" or "total"
     * @return a String stating the amount with a unit
     */
    public String getAmountDescription(String type) {
        String currentString = "";
        Double calories = 0.0;
        Double distance = 0.0;
        Double minutes = 0.0;
        Duration duration;

        // Generate the amount values based on the type
        if (type.equals("current")) {
            calories = caloriesBurned * progress / 100;
            distance = goalDistance * progress / 100;
            // Get the number of minutes completed so far
            minutes = Double.valueOf(Long.toString(getGoalDuration().toMinutes())) * progress / 100;
        } else if (type.equals("total")) {
            calories = (double) caloriesBurned;
            distance = goalDistance;
            // Get the number of minutes completed so far
            minutes = Double.valueOf(Long.toString(getGoalDuration().toMinutes()));
        }

        // Generate an appropriate description based on the type of the goal
        if (isCaloriesGoal()) {
            currentString += String.format("%.0f calories", calories);
        } else if (isDistanceGoal()) {
            // Use the distanceDisplayMetric class to format the string
            DistanceDisplayMetric distanceDisplayMetric = new DistanceDisplayMetric(distance * 1000);   // Pass a meter value for distance
            currentString += distanceDisplayMetric.toString();
        } else if (isDurationGoal()) {
            // Convert currentMinutes back to a Duration
            duration = Duration.ofMinutes(Long.valueOf(String.format("%.0f", minutes)));
            // Get unit of the number of hours completed
            String dayUnit = getHourUnit(duration);
            currentString += String.format("%d %s and %d minutes", duration.toHours(), dayUnit,
                    duration.toMinutes() - duration.toHours() * 60);    // toMinutes() includes the hours as well so they must be subtracted out);
        }

        return currentString;
    }

    /** Get a string containing the unit of the number of hours of the duration passed in
     *
     * @param duration The duration to compute the hour unit of
     * @return  a string containing 'hour' or 'hours' depending if the duration passed in has 1 hour in it
     *  or not respectively.
     */
    private String getHourUnit(Duration duration) {
        Long hours = duration.toHours();
        String dayUnit;
        if (hours == 1) {
            dayUnit = "hour";   // Singular
        } else {
            dayUnit = "hours";  // Plural
        }

        return dayUnit;
    }

    /** Get a formatted description of the time remaining for this goal
     *
     * @return the time remaining in days (as a string) if the goal is current or expired 'expiry date'
     *  if the goal has expired
     */
    public String getRemainingTimeDescription() {
        if (isCurrent()) {
            Duration remaining = getRemainingTime();
            return String.format("%d %s", remaining.toDays(), getDayUnit(remaining));
        } else {    // Goal is a past goal
            if (isComplete()) {
                return String.format("Completed %s", getCompletionDate().toString());
            } else {    // Expired goal
                return String.format("Expired %s", getExpiryDate().toString());
            }
        }
    }

    /** Get the time remaining before the goal expires
     *
     * @return the time remaining before the goal expires as a Duration
     */
    private Duration getRemainingTime() {
        return Duration.ofDays(ChronoUnit.DAYS.between(LocalDate.now(), getExpiryDate()));
    }

    /** Get a string containing the unit of the number of days of the duration passed in
     *
     * @param duration The duration to compute the day unit of
     * @return  a string containing 'day' or 'days' depending if the duration passed in has 1 day in it
     *  or not respectively.
     */
    private String getDayUnit(Duration duration) {
        Long days = duration.toDays();
        String dayUnit;
        if (days == 1) {
            dayUnit = "day";   // Singular
        } else {
            dayUnit = "days";  // Plural
        }
        return dayUnit;
    }

    /** Get a comparator intended for comparing past goals by creation date (descending order)
     *
     * @return a Goal comparator which orders goals in descending order by creation date
     */
    public static Comparator<Goal> getPastGoalComparator() {
        return (o1, o2) -> {
            int dateCompare;
            if ((dateCompare = o1.getCreationDate().compareTo(o2.getCreationDate())) != 0) {
                return dateCompare * -1;  // Reverse order to descending
            } else {    // Goals have the same creation date
                return 0;
            }
        };
    }

    @Override
    public LocalDate getDate() {
        return getExpiryDate();
    }

    @Override
    public String getDisplayString() {
        return "Goal";
    }
}
