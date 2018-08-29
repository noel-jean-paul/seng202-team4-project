package seng202.team4.model.data;
import seng202.team4.model.data.enums.GoalType;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Objects;


public class Goal {
    private int number;
    private double progress;
    private GoalType type;
    private LocalDate creationDate;
    private LocalDate expiryDate;
    private LocalDate completionDate;
    private String description;
    private double goalDistance;
    private double goalDuration;

    public Goal(int number, double progress, GoalType type, String creationDate, String expiryDate,
                String completionDate, String description, double goalDistance, double goalDuration) {
        this.number = number;
        this.progress = progress;
        this.type = type;
        this.creationDate = LocalDate.parse(creationDate);
        this.expiryDate = LocalDate.parse(expiryDate);
        this.completionDate = LocalDate.parse(completionDate);
        this.description = description;
        this.goalDistance = goalDistance;
        this.goalDuration = goalDuration;
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
