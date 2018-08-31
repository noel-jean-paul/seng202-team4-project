package seng202.team4;

import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.GoalType;

public class testfile {
    public static void main(String[] args) {
        System.out.println(GoalType.Run.name().equals(ActivityType.Run.name()));
    }

}
