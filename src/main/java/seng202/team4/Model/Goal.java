package seng202.team4.Model;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;

enum GoalType{
    Walking, Running
}

public class Goal {
    private double progress = 0;
    private GoalType type;
    private LocalDate creationDate;
    private LocalDate expiryDate;
    private Date completionDate = null;
    private String description;

    public Goal(GoalType type, LocalDate creationDate, LocalDate expiryDate, String description) {
        this.type = type;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.description = description;
    }

    public boolean isComplete() {
        if (completionDate == null){
            return true;
        }
        return false;
    }

    public int getTimeRemaining() {
        return Period.between(expiryDate, LocalDate.now()).getDays();
    }
}
