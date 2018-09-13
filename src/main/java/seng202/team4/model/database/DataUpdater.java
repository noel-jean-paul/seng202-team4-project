package seng202.team4.model.database;

import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.Profile;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class DataUpdater extends DataAccesser {
    private static String update;

    /** Update the field of a profile
     *
     * @param profile the profile to be updated
     * @param field the field of the profile to be updated
     * @param value the new value for the field
     * @throws SQLException if an error occurred regarding the database
     */
    public static void updateProfile(Profile profile, String field, String value) throws SQLException {
        update = "update profile set " + field + " = (?) where firstName = (?) and lastName = (?)";
        statement = connection.prepareStatement(update);
        // Set wildcards (indexed from 1)
        statement.setString(1, value);
        statement.setString(2, profile.getFirstName());
        statement.setString(3, profile.getLastName());

        statement.executeUpdate();

        // Cleanup
        statement.close();
    }

    public static void main(String[] args) throws SQLException {
        DataAccesser.initialiseConnection();
        // Delete all profiles from the database
        String select = "delete from profile";
        PreparedStatement statement = connection.prepareStatement(select);
        statement.executeUpdate();

        Profile profile = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        String firstName = "Michael";
        DataStorer.insertProfile(profile);
        updateProfile(profile, "", firstName);
        profile.setFirstName(firstName);


        System.out.println(profile.getFirstName());
        System.out.println(profile.getLastName());
        Profile loadedProfile = DataLoader.loadProfile(profile.getFirstName(), profile.getLastName());
        System.out.println(loadedProfile);
    }
}
