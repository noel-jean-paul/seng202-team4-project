package seng202.team4.model.database;

import seng202.team4.model.data.*;
import seng202.team4.model.data.enums.GoalType;

import java.sql.*;

import java.util.List;


abstract public class DataStorer extends DataAccesser {
    /* Class to handle inserting and deleting of objects from the database */

    private static String dataRowInsertSQL = "insert into dataRow (rowNumber, rowDate, time, heartRate, latitude, " +
            "longitude, elevation, name, activityDate, firstName, lastName) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /** Add a profile to the database.
     *  If the combination of profile firstName and lastName is not unique, the profile will not be added.
     *  It is assumed that all profile fields are correctly formatted.
     *
     * @param profile the profile to be added
     * @throws SQLException if an error occurred regarding the database
     */
    public static void insertProfile(Profile profile) throws SQLException {
        assert profile != null;

        String insert = "insert into profile(firstName, lastName, dateOfBirth, height, weight, pictureURL) " +
                "values (?, ? , ?, ?, ?, ?)";
        statement = connection.prepareStatement(insert);
        // set the wildcards (indexed from 1)
        statement.setString(1, profile.getFirstName());
        statement.setString(2, profile.getLastName());
        statement.setString(3, String.valueOf(profile.getDateOfBirth()));
        statement.setString(4, String.valueOf(profile.getHeight()));
        statement.setString(5, String.valueOf(profile.getWeight()));
        statement.setString(6, profile.getPictureURL());

        statement.executeUpdate();

        statement.close();
    }

    /** Add an activity to the database
     *  If the combination of activity name/date and profile first/last name is not unique, the activity will not be added.
     *  It is assumed that all object fields are correctly formatted.
     *
     * @param activity the activity to be added
     * @param activityOwner the profile which the activity belongs to. Assumed to be in the database already.
     * @throws SQLException if an error occurred regarding the database
     */

    public static void insertActivity(Activity activity, Profile activityOwner) throws SQLException {
        assert activity != null;

        String insert = "insert into activity(name, activityDate, type, startTime, duration, distance, " +
                "caloriesBurned, firstName, lastName) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        statement = connection.prepareStatement(insert);
        // set the wildcards (indexed from 1)
        statement.setString(1, activity.getName());
        statement.setString(2, String.valueOf(activity.getDate()));
        statement.setString(3, String.valueOf(activity.getType()));
        statement.setString(4, String.valueOf(activity.getStartTime()));
        statement.setString(5, String.valueOf(activity.getDuration()));
        statement.setString(6, String.valueOf(activity.getDistance()));
        statement.setString(7, String.valueOf(activity.getCaloriesBurned()));
        statement.setString(8, activityOwner.getFirstName());
        statement.setString(9, activityOwner.getLastName());

        statement.executeUpdate();

        statement.close();
    }

    /** Add an goal to the database
     *  If the combination of goal name/date and profile first/last name is not unique, the goal will not be added.
     *  It is assumed that all object fields are correctly formatted.
     *
     * @param goal the goal to be added
     * @param goalOwner the profile who the goal belongs to. Assumed to be in the database already.
     * @throws SQLException if an error occurred regarding the database
     */

    public static void insertGoal(Goal goal, Profile goalOwner) throws SQLException {
        assert goal != null;

        String insert = "insert into goal(goalNumber, progress, type, creationDate, expiryDate, completionDate, " +
                "goalDuration, goalDistance, current, firstName, lastName) values (?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        statement = connection.prepareStatement(insert);
        // set the wildcards (indexed from 1)
        statement.setString(1, String.valueOf(goal.getNumber()));
        statement.setString(2, String.valueOf(goal.getProgress()));
        statement.setString(3, String.valueOf(goal.getType()));
        statement.setString(4, String.valueOf(goal.getCreationDate()));
        statement.setString(5, String.valueOf(goal.getExpiryDate()));
        statement.setString(6, String.valueOf(goal.getCompletionDate()));
        statement.setString(7, String.valueOf(goal.getGoalDuration()));
        statement.setString(8, String.valueOf(goal.getGoalDistance()));
        statement.setString(9, String.valueOf(goal.isCurrent()));
        statement.setString(10, goalOwner.getFirstName());
        statement.setString(11, goalOwner.getLastName());

        statement.executeUpdate();

        statement.close();
    }

    /** Create the statement for inserting a dataRow
     *
     * @param statement the PreparedStatement whose wildcards are being set
     * @param dataRow the dataRow being inserted
     * @throws SQLException if an error occurred regarding the database
     */
    private static void setDataRowInsertStatement(PreparedStatement statement, DataRow dataRow) throws SQLException {
        // set the wildcards (indexed from 1)
        statement.setString(1, String.valueOf(dataRow.getNumber()));
        statement.setString(2, String.valueOf(dataRow.getDate()));
        statement.setString(3, String.valueOf(dataRow.getTime()));
        statement.setString(4, String.valueOf(dataRow.getHeartRate()));
        statement.setString(5, String.valueOf(dataRow.getLatitude()));
        statement.setString(6, String.valueOf(dataRow.getLongitude()));
        statement.setString(7, String.valueOf(dataRow.getElevation()));
        statement.setString(8, dataRow.getOwner().getName());
        statement.setString(9, String.valueOf(dataRow.getOwner().getDate()));
        statement.setString(10, dataRow.getOwner().getOwner().getFirstName());
        statement.setString(11, dataRow.getOwner().getOwner().getLastName());
    }

    /** Add a dataRow to the database
     *  If the combination of dataRow number and activty name/date is not unique, the dataRow will not be added.
     *  It is assumed that all dataRow fields are correctly formatted.
     *
     * @param dataRow the data row to be added
     * @throws SQLException if an error occurred regarding the database
     */
    public static void insertDataRow(DataRow dataRow) throws SQLException {
        assert dataRow != null;

        statement = connection.prepareStatement(dataRowInsertSQL);
        DataStorer.setDataRowInsertStatement(statement, dataRow);
        statement.executeUpdate();
        statement.close();
    }

    /** Insert a list of dataRows into the database using a transaciton
     *
     * @param rows the list of dataRows to be inserted
     * @throws SQLException if an error occurred regarding the database
     */
    public static void insertDataRowTransaction(List<DataRow> rows) throws SQLException {
        // Set auto-commit mode to false
        connection.setAutoCommit(false);

        for (DataRow dataRow : rows) {
            assert dataRow != null;
            statement = connection.prepareStatement(dataRowInsertSQL);
            setDataRowInsertStatement(statement, dataRow);
            statement.executeUpdate();
            statement.close();
        }

        connection.commit();

        // Set auto-commit mode back to true
        connection.setAutoCommit(true);
    }

    //
    //  Delete Methods
    //

    /** Delete a profile from the database along with its associated activities and goals
     *
     * @param profile the profile to be deleted
     * @throws SQLException if an error occurred regarding the database
     */
    public static void deleteProfile(Profile profile) throws SQLException {
        assert profile != null;

        // Delete Profile
        String select = "delete from profile where " +
                "firstName = (?) " +
                "and lastName = (?)";
        statement = connection.prepareStatement(select);
        statement.setString(1, profile.getFirstName());
        statement.setString(2, profile.getLastName());


        statement.executeUpdate();
        statement.close();

        // Delete all activities belonging to the profile
        deleteActivities(profile.getActivityList());

        // Delete all goals belonging to a profile
        deleteGoals(profile.getGoalList());

    }

    /** Delete a list of activities from the database along with its associated data rows
     *
     * @param activities the activities to be deleted
     * @throws SQLException if an error occurred regarding the database
     */
    public static void deleteActivities(List<Activity> activities) throws SQLException {
        // Set auto-commit mode to false
        connection.setAutoCommit(false);

        String select = "DELETE FROM activity WHERE " +
                "name = (?) " +
                "AND activityDate = (?)" +
                "AND firstName = (?)" +
                "AND lastName = (?)";

        for (Activity activity : activities) {
            // Delete Activity
            statement = connection.prepareStatement(select);
            statement.setString(1, activity.getName());
            statement.setString(2, String.valueOf(activity.getDate()));
            statement.setString(3, activity.getOwner().getFirstName());
            statement.setString(4, activity.getOwner().getLastName());

            statement.executeUpdate();
            statement.close();

            // Delete all dataRows belonging to the activity
            deleteDataRows(activity.getRawData());

            // Reset auto-commit mode to false every iteration as it will be set true by deleteDataRows
            connection.setAutoCommit(false);
        }

        connection.commit();
        connection.setAutoCommit(true);
    }

    /** Delete a list of goals from the database
     *
     * @param goals the goals to be deleted
     * @throws SQLException if an error occurred regarding the database
     */
    public static void deleteGoals(List<Goal> goals) throws SQLException {
        // Set auto-commit mode to false
        connection.setAutoCommit(false);

        String select = "DELETE FROM goal WHERE " +
                "goalNumber = (?) " +
                "AND firstName = (?) " +
                "AND lastName = (?)";

        for (Goal goal : goals) {
            statement = connection.prepareStatement(select);
            statement.setString(1, String.valueOf(goal.getNumber()));
            statement.setString(2, goal.getOwner().getFirstName());
            statement.setString(3, goal.getOwner().getLastName());

            statement.executeUpdate();
            statement.close();
        }

        connection.commit();
        connection.setAutoCommit(true);

    }

    /** Delete a list of dataRows from the database
     *
     * @param dataRows the list of dataRows to be deleted
     * @throws SQLException if an error occurred regarding the database
     */
    public static void deleteDataRows(List<DataRow> dataRows) throws SQLException {
        // Set auto-commit mode to false
        connection.setAutoCommit(false);

        String select = "DELETE FROM dataRow " +
                "WHERE rowNumber = (?) " +
                "AND name = (?) " +
                "AND activityDate = (?)" +
                "AND firstName = (?)" +
                "AND lastName = (?)";

        for (DataRow row : dataRows) {
            statement = connection.prepareStatement(select);
            statement.setString(1, String.valueOf(row.getNumber()));
            statement.setString(2, row.getOwner().getName());
            statement.setString(3, String.valueOf(row.getOwner().getDate()));
            statement.setString(4, String.valueOf(row.getOwner().getOwner().getFirstName()));
            statement.setString(5, String.valueOf(row.getOwner().getOwner().getLastName()));

            statement.executeUpdate();
            statement.close();
        }

        connection.commit();
        connection.setAutoCommit(true);
    }
}
