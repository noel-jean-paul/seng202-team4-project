package seng202.team4.model.database;

import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.Goal;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ProfileFields;

import java.sql.SQLException;
import java.util.Collection;
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
        // Update the profile
        update = "update profile set " + field + " = (?) where firstName = (?) and lastName = (?)";
        statement = connection.prepareStatement(update);
        // Set wildcards (indexed from 1)
        statement.setString(1, value);
        statement.setString(2, profile.getFirstName());
        statement.setString(3, profile.getLastName());

        statement.executeUpdate();

        // Propagate the updates if a primary key was updated
        if (field.equals(ProfileFields.firstName.toString()) || field.equals(ProfileFields.lastName.toString())) {
            // Update the activities
            updateActivities(profile.getActivityList(), field, value, true);

            // Update the goals
            updateGoals(profile.getCurrentGoals(), field, value);
        }

        // Cleanup
        statement.close();
    }

    /** Update the field of a collection of activities. Update the raw data of the activities if the propagateUpdate
     *   flag is set
     *
     * @param activities the activities to be updated
     * @param field the field of the activity to be updated
     * @param value the new value for the field
     * @param propagateUpdate boolean flag for whether the dataRows should also have the update applied to them
     * @throws SQLException if an error occurred regarding the database
     */
    public static void updateActivities(List<Activity> activities, String field, String value, boolean propagateUpdate)
            throws SQLException {
        // Set auto-commit mode to false
        connection.setAutoCommit(false);

        // Update the profile
        update = "update activity set " + field + " = (?) where " +
                "name = (?) and " +
                "activityDate = (?) and " +
                "firstName = (?) and " +
                "lastName = (?)";

        for (Activity activity : activities) {
            statement = connection.prepareStatement(update);
            // Set wildcards (indexed from 1)
            statement.setString(1, value);
            statement.setString(2, activity.getName());
            statement.setString(3, activity.getDate().toString());
            statement.setString(4, activity.getOwner().getFirstName());
            statement.setString(5, activity.getOwner().getLastName());

            statement.executeUpdate();
            statement.close();

            // Only update the dataRows if the update is intented to be propagated
            // (i.e. it modifys a component of the primary key of dataRow)
            if (propagateUpdate) {
                // Update the dataRows
                updateDataRows(activity.getRawData(), field, value);
            }

            // Reset auto-commit mode to false every iteration as it will be set true by deleteDataRows
            connection.setAutoCommit(false);
        }

        // Commit the updates
        connection.commit();

        // Cleanup
        connection.setAutoCommit(true);
    }

    /** Update a field of a list of goals
     *
     * @param goals the goals to be updated
     * @param field the field to be updated
     * @param value the new value for the field
     * @throws SQLException if an error occurred regarding the database
     */
    public static void updateGoals(List<Goal> goals, String field, String value) throws SQLException {
        // Set auto-commit mode to false
        connection.setAutoCommit(false);

        update = "update goal set " + field + " = (?) where " +
                "goalNumber = (?) and " +
                "firstName = (?) and " +
                "lastName = (?)";

        for (Goal goal : goals) {
            statement = connection.prepareStatement(update);
            // Set wildcards (indexed from 1)
            statement.setString(1, value);
            statement.setDouble(2, goal.getNumber());
            statement.setString(3, goal.getOwner().getFirstName());
            statement.setString(4, goal.getOwner().getLastName());

            statement.executeUpdate();
            statement.close();
        }

        // Commit the updates
        connection.commit();

        // Cleanup
        connection.setAutoCommit(true);
    }

    /** Update a field of a collection of DataRows
     *
     * @param rows the collection of dataRows to be updated
     * @param field the field to be updated
     * @param value the new value for the field
     * @throws SQLException if an error occurred regarding the database
     */
    public static void updateDataRows(Collection<DataRow> rows, String field, String value) throws SQLException {
        // Set auto-commit mode to false
        connection.setAutoCommit(false);

        // Using the class attribute update breaks 2 of the updateProfile tests. Not sure why
        String update = "update dataRow set " + field + " = (?) where " +
                "rowNumber = (?) and " +
                "name= (?) and " +
                "activityDate = (?) and " +
                "firstName = (?) and " +
                "lastName = (?)";

        for (DataRow row : rows) {
            statement = connection.prepareStatement(update);
            // Set wildcards (indexed from 1)
            statement.setString(1, value);
            statement.setDouble(2, row.getNumber());
            statement.setString(3, row.getOwner().getName());
            statement.setString(4, row.getOwner().getDate().toString());
            statement.setString(5, row.getOwner().getOwner().getFirstName());
            statement.setString(6, row.getOwner().getOwner().getLastName());

            statement.executeUpdate();
            statement.close();
        }

        // Commit the updates
        connection.commit();

        // Cleanup
        connection.setAutoCommit(true);
    }

}
