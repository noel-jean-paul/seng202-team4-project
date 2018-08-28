package seng202.team4.model;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

enum GoalType{
    Walking, Running
}

public class Goal {
    private double progress;
    private GoalType type;
    private LocalDate creationDate;
    private LocalDate expiryDate;
    private Date completionDate;
    private String description;
    private double goalDistance;
    private double goalDuration;

    public Goal(double progress, GoalType type, LocalDate creationDate, LocalDate expiryDate, Date completionDate, String description, double goalDistance, double goalDuration) {
        this.progress = progress;
        this.type = type;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.completionDate = completionDate;
        this.description = description;
        this.goalDistance = goalDistance;
        this.goalDuration = goalDuration;
    }

    public boolean isComplete() {
        return completionDate == null;
    }

    public int getTimeRemaining() {
        return Period.between(expiryDate, LocalDate.now()).getDays();
    }



}
