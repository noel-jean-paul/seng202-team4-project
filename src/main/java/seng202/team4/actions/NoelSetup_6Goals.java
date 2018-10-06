package seng202.team4.actions;

import seng202.team4.model.data.Goal;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.database.DataAccesser;
import seng202.team4.model.database.DataStorer;

import java.sql.SQLException;

public class NoelSetup_6Goals {
    public static void main(String[] args) throws SQLException {
        DataAccesser.initialiseMainConnection();
        DataAccesser.clearDatabase();

        Profile profile = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);

        Goal goal1 = new Goal(1, 100, GoalType.Run,"2018-09-28", "2018-12-12",
                "PT50M");

        Goal goal2 = new Goal(2, 80, GoalType.Run,"2018-09-28", "2018-12-12",
                500);

        Goal goal3 = new Goal(3, 55, GoalType.Walk, "2018-03-20", "2018-01-01",
                200);

        Goal goal4 = new Goal(4, 30, GoalType.Run, "2017-05-21", "2020-01-02",
                "PT60M");

        Goal goal5 = new Goal(5, 5, GoalType.Walk, "2018-03-20", "2020-01-01",
                40.0);

        Goal goal6 = new Goal(6, 17, GoalType.Run, "2017-05-21", "3000-01-02",
                0.999);

        profile.addCurrentGoal(goal1);
        profile.addCurrentGoal(goal2);
        profile.addCurrentGoal(goal3);
        profile.addCurrentGoal(goal4);
        profile.addCurrentGoal(goal5);
        profile.addCurrentGoal(goal6);
    }
}
