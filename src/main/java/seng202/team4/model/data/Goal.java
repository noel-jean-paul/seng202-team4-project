package seng202.team4.model.data;

import seng202.team4.model.data.enums.GoalFields;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.database.DataUpdater;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Objects;


public class Goal implements Comparable<Goal> {
    public static final double minGoalDistance = 0;

    private int number;
    private double progress;
    private GoalType type;
    private LocalDate creationDate;
    private LocalDate expiryDate;
    private LocalDate completionDate;
    private String description;
    private double goalDistance;
    private Duration goalDuration;
    private int caloriesBurned;
    private Profile owner;
    private boolean current;

    /** Base constructor for goals */
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
        this.current = true;
        this.description = Goal.generateDescription(this);
    }

    /** Constructor for a distance goal */
    public Goal(int number, double progress, GoalType type, String creationDate, String expiryDate,
                double goalDistance) {
        this(number, progress, type, creationDate, expiryDate, goalDistance, "PT0M", 0);
    }

    /** Constructor for a duration goal */
    public Goal(int number, double progress, GoalType type, String creationDate, String expiryDate,
                String goalDuration) {
        this(number, progress, type, creationDate, expiryDate, 0, goalDuration, 0);
    }

    /** Constructor for a calories goal */
    public Goal(int number, double progress, GoalType type, String creationDate, String expiryDate,
                int caloriesBurned) {
        this(number, progress, type, creationDate, expiryDate, 0, "PT0M", caloriesBurned);
    }

    /** Constructor for loading goals from the database */
    public Goal(int number, double progress, GoalType type, String creationDate, String expiryDate,
                String completionDate, String goalDuration, double goalDistance, int caloriesBurned, boolean current) {
        this(number, progress, type, creationDate, expiryDate, goalDistance, goalDuration, caloriesBurned);
        this.completionDate = LocalDate.parse(completionDate);
        this.current = current;

    }

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
        return Integer.compare(this.getNumber(), o.getNumber()) * -1;
    }   // descending order

    @Override
    public String toString() {
        return "Goal{" +
                "number=" + number +
                '}';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.goalNumber.toString(), Double.toString(number));
        this.number = number;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.progress.toString(), Double.toString(progress));
        this.progress = progress;
    }

    /* Set without updating the database */
    public void updateProgressValue(double progress) {
        this.progress = progress;
    }

    public GoalType getType() {
        return type;
    }

    public void setType(GoalType type) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.type.toString(), String.valueOf(type));
        this.type = type;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.creationDate.toString(), creationDate);
        this.creationDate = LocalDate.parse(creationDate);
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this),GoalFields.expiryDate.toString(), expiryDate);
        this.expiryDate = LocalDate.parse(expiryDate);
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this),GoalFields.completionDate.toString(), completionDate);
        this.completionDate = LocalDate.parse(completionDate);
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.caloriesBurned.toString(),
                Integer.toString(caloriesBurned));
        this.caloriesBurned = caloriesBurned;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getGoalDistance() {
        return goalDistance;
    }

    public void setGoalDistance(double goalDistance) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this),GoalFields.goalDistance.toString(), Double.toString(goalDistance));
        this.goalDistance = goalDistance;
    }

    public Duration getGoalDuration() {
        return goalDuration;
    }

    public void setGoalDuration(Duration goalDuration) throws SQLException {
        DataUpdater.updateGoals(Collections.singletonList(this), GoalFields.goalDuration.toString(), goalDuration.toString());
        this.goalDuration = goalDuration;
    }

    public boolean isComplete() {
        return completionDate == null;
    }

    public int getTimeRemaining() {
        return Period.between(expiryDate, LocalDate.now()).getDays();
    }

    public Profile getOwner() {
        return owner;
    }

    public void setOwner(Profile owner) {
        this.owner = owner;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    /** Create a description for the goal based of its paramers
     *
     * @param goal the goal to generate the description for
     * @return a description of the goal as a String.
     */
    private static String generateDescription(Goal goal) {
        String description = "";

        if (goal.isDistanceGoal()) {
            description = String.format("%s %.0f meters", goal.getType().toString(),
                    goal.getGoalDistance() * 1000);    // Convert kms to meters
        } else if (goal.isDurationGoal()) {
            description = String.format("%s for %d hours and %d minutes",
                    goal.getType().toString(), goal.getGoalDuration().toHours(),
                    goal.getGoalDuration().toMinutes() - goal.getGoalDuration().toHours() * 60);    // toMinutes() includes the hours as well
        } else if (goal.isCaloriesGoal()) {
            description = String.format("Burn %s calories while %sning",
                    goal.getCaloriesBurned(), goal.getType().toString().toLowerCase());
        }
        return description;
    }

    /** Increase the goal's progress by an amount up to a max of 100
     *
     * @param amount the amount to increment the goal by as a Double (percentage not decimal)
     *
     * */
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
}
