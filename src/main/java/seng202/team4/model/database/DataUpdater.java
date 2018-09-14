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

    /** Update the field of an activity
     *
     * @param activity the activity to be updated
     * @param field the field of the activity to be updated
     * @param value the new value for the field
     * @throws SQLException if an error occurred regarding the database
     */
    public static void updateActivity(Activity activity, String field, String value)
            throws SQLException {
        update = "update activity set " + field + " = (?) where " +
                "name = (?) and " +
                "activityDate = (?) and " +
                "firstName = (?) and " +
                "lastName = (?)";

        statement = connection.prepareStatement(update);
        // Set wildcards (indexed from 1)
        statement.setString(1, value);
        statement.setString(2, activity.getName());
        statement.setString(3, activity.getDate().toString());
        statement.setString(4, activity.getOwner().getFirstName());
        statement.setString(5, activity.getOwner().getLastName());

        statement.executeUpdate();

        // Cleanup
        statement.close();
    }

    /** Update a field of a goal
     *
     * @param goal the goal to be updated
     * @param field the field to be updated
     * @param value the new value for the field
     * @throws SQLException if an error occurred regarding the database
     */
    public static void updateGoal(Goal goal, String field, String value) throws SQLException {
        update = "update goal set " + field + " = (?) where " +
                "goalNumber = (?) and " +
                "firstName = (?) and " +
                "lastName = (?)";

        statement = connection.prepareStatement(update);
        // Set wildcards (indexed from 1)
        statement.setString(1, value);
        statement.setDouble(2, goal.getNumber());
        statement.setString(3, goal.getOwner().getFirstName());
        statement.setString(4, goal.getOwner().getLastName());

        statement.executeUpdate();

        // Cleanup
        statement.close();
    }
}
