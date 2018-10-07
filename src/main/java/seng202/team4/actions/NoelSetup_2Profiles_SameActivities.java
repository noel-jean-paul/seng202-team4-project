package seng202.team4.actions;

import seng202.team4.model.data.Profile;
import seng202.team4.model.database.DataAccesser;
import seng202.team4.model.database.DataStorer;

import java.sql.SQLException;

@SuppressWarnings("ALL")
public class NoelSetup_2Profiles_SameActivities {
    public static void Main(String[] args) throws SQLException {
        DataAccesser.initialiseMainConnection();
        DataAccesser.clearDatabase();

        Profile profile = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        DataStorer.insertProfile(profile);


    }



}
