package seng202.team4.actions;

import seng202.team4.model.data.Goal;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.database.DataStorer;

import java.sql.SQLException;

public class DemoSetup {

    public static void main() throws SQLException {
        // Wipe the database
        ClearDatabase.main();

        // Add a new profile
        Profile profile = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);

        // 1 goal which has expired
        Goal expiredGoal = new Goal(1, 25, GoalType.Run,"2018-09-28", "2018-10-09",
                "PT50M");
        profile.addCurrentGoal(expiredGoal);

        // A goal which is in progress
        Goal currentGoal1 = new Goal(2, 76, GoalType.Walk,"2018-09-30", "2018-10-17",
                300);
        Goal currentGoal2 = new Goal(3, 59, GoalType.Run,"2018-10-01", "2018-11-23",
                5.0);
        profile.addCurrentGoal(currentGoal1);
        profile.addCurrentGoal(currentGoal2);
    }
}
