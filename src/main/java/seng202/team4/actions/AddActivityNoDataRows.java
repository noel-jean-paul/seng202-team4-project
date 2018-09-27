package seng202.team4.actions;

import seng202.team4.model.data.Activity;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.DataAccesser;
import seng202.team4.model.database.DataStorer;

import java.sql.SQLException;

public class AddActivityNoDataRows {
    public static void main(String[] args) throws SQLException {
        DataAccesser.initialiseMainConnection();

        Profile profile = new Profile("Maew", "Micki", "1997-06-23", 76,
                3.00);
        DataStorer.insertProfile(profile);
        Activity activity = new Activity("Run in the park", "2018-08-29", "", ActivityType.Run,
                "12:15:01", "PT40M", 5.13, 187);
        profile.addActivity(activity);

        DataAccesser.closeDatabase();
    }

}
