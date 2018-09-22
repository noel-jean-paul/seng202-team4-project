package seng202.team4.model.data;

import seng202.team4.model.data.enums.GoalFields;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.database.DataUpdater;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;


public class Goal implements Comparable<Goal> {
    private int number;
    private double progress;
    private GoalType type;
    private LocalDate creationDate;
    private LocalDate expiryDate;
    private LocalDate completionDate;
    private String description;
    private double goalDistance;
    private double goalDuration;
    private Profile owner;

    /** Constructor for creating new goals */
    public Goal(int number, double progress, GoalType type, String creationDate, String expiryDate,
                double goalDistance, double goalDuration) {
        this.number = number;
        this.progress = progress;
        this.type = type;
        this.creationDate = LocalDate.parse(creationDate);
        this.completionDate = LocalDate.MAX;
        this.expiryDate = LocalDate.parse(expiryDate);
        // TODO: 31/08/18 create a description based on type, distance and duration describing the goal
        this.goalDistance = goalDistance;
        this.goalDuration = goalDuration;
    }

    /** Constructor for loading goals from the database */
    public Goal(int number, double progress, GoalType type, String description, String creationDate, String expiryDate,
                String completionDate, double goalDuration, double goalDistance) {
        this.number = number;
        this.progress = progress;
        this.type = type;
        this.description = description;
        this.creationDate = LocalDate.parse(creationDate);
        this.expiryDate = LocalDate.parse(expiryDate);
        this.completionDate = LocalDate.parse(completionDate);
        this.goalDuration = goalDuration;
        this.goalDistance = goalDistance;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return getNumber() == goal.getNumber() &&
                Double.compare(goal.getProgress(), getProgress()) == 0 &&
                Double.compare(goal.getGoalDistance(), getGoalDistance()) == 0 &&
                Double.compare(goal.getGoalDuration(), getGoalDuration()) == 0 &&
                getType() == goal.getType() &&
                Objects.equals(getCreationDate(), goal.getCreationDate()) &&
                Objects.equals(getExpiryDate(), goal.getExpiryDate()) &&
                Objects.equals(getCompletionDate(), goal.getCompletionDate()) &&
                Objects.equals(getDescription(), goal.getDescription());
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
        return Integer.compare(this.getNumber(), o.getNumber());
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) throws SQLException {
        DataUpdater.updateGoal(this, GoalFields.goalNumber.toString(), Double.toString(number));
        this.number = number;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) throws SQLException {
        DataUpdater.updateGoal(this, GoalFields.progress.toString(), Double.toString(progress));
        this.progress = progress;
    }

    public GoalType getType() {
        return type;
    }

    public void setType(GoalType type) throws SQLException {
        DataUpdater.updateGoal(this, GoalFields.type.toString(), String.valueOf(type));
        this.type = type;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) throws SQLException {
        DataUpdater.updateGoal(this, GoalFields.creationDate.toString(), creationDate);
        this.creationDate = LocalDate.parse(creationDate);
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) throws SQLException {
        DataUpdater.updateGoal(this,GoalFields.expiryDate.toString(), expiryDate);
        this.expiryDate = LocalDate.parse(expiryDate);
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) throws SQLException {
        DataUpdater.updateGoal(this,GoalFields.completionDate.toString(), completionDate);
        this.completionDate = LocalDate.parse(completionDate);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws SQLException {
        DataUpdater.updateGoal(this,GoalFields.description.toString(), description);
        this.description = description;
    }

    public double getGoalDistance() {
        return goalDistance;
    }

    public void setGoalDistance(double goalDistance) throws SQLException {
        DataUpdater.updateGoal(this,GoalFields.goalDistance.toString(), Double.toString(goalDistance));
        this.goalDistance = goalDistance;
    }

    public double getGoalDuration() {
        return goalDuration;
    }

    public void setGoalDuration(double goalDuration) throws SQLException {
        DataUpdater.updateGoal(this,GoalFields.goalDuration.toString(), Double.toString(goalDuration));
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
}
