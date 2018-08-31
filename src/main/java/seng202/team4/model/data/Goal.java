package seng202.team4.model.data;
import seng202.team4.model.data.enums.GoalType;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
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

    public void setNumber(int number) {
        this.number = number;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public GoalType getType() {
        return type;
    }

    public void setType(GoalType type) {
        this.type = type;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
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

    public void setGoalDistance(double goalDistance) {
        this.goalDistance = goalDistance;
    }

    public double getGoalDuration() {
        return goalDuration;
    }

    public void setGoalDuration(double goalDuration) {
        this.goalDuration = goalDuration;
    }

    public boolean isComplete() {
        return completionDate == null;
    }

    public int getTimeRemaining() {
        return Period.between(expiryDate, LocalDate.now()).getDays();
    }



}
