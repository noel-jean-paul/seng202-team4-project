package seng202.team4;

import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.database.DataAccesser;
import seng202.team4.model.database.DataLoader;
import seng202.team4.model.database.DataStorer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;

public class testfile  {
    public static void main(String[] args) throws SQLException {
        DataAccesser.initialiseConnection();
        DataAccesser.clearDatabase();

        Profile profile1 = new Profile("Noel", "Bisson", "1998-03-06", 299,
                1.83);
        DataStorer.insertProfile(profile1);
    }
}
