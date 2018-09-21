package seng202.team4.model.database;

import seng202.team4.model.data.*;
import seng202.team4.model.data.enums.*;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.List;


abstract public class DataStorer extends DataAccesser {
    /* Class to handle inserting and deleting of objects from the database */

    /** Add a profile to the database.
     *  If the combination of profile firstName and lastName is not unique, the profile will not be added.
     *  It is assumed that all profile fields are correctly formatted.
     *
     * @param profile the profile to be added
     * @throws SQLException if an error occurred regarding the database
     */
    public static void insertProfile(Profile profile) throws SQLException {
        assert profile != null;

        String insert = "insert into profile(firstName, lastName, dateOfBirth, height, weight) values (?, ? , ?, ?, ?)";
        statement = connection.prepareStatement(insert);
        // set the wildcards (indexed from 1)
        statement.setString(1, profile.getFirstName());
        statement.setString(2, profile.getLastName());
        statement.setString(3, String.valueOf(profile.getDateOfBirth()));
        statement.setString(4, String.valueOf(profile.getHeight()));
        statement.setString(5, String.valueOf(profile.getWeight()));

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

        String insert = "insert into activity(name, activityDate, description, type, startTime, duration, distance, " +
                "caloriesBurned, firstName, lastName) values (?, ? , ?, ?, ?, ?, ?, ?, ?, ?)";
        statement = connection.prepareStatement(insert);
        // set the wildcards (indexed from 1)
        statement.setString(1, activity.getName());
        statement.setString(2, String.valueOf(activity.getDate()));
        statement.setString(3, activity.getDescription());
        statement.setString(4, String.valueOf(activity.getType()));
        statement.setString(5, String.valueOf(activity.getStartTime()));
        statement.setString(6, String.valueOf(activity.getDuration()));
        statement.setString(7, String.valueOf(activity.getDistance()));
        statement.setString(8, String.valueOf(activity.getCaloriesBurned()));
        statement.setString(9, activityOwner.getFirstName());
        statement.setString(10, activityOwner.getLastName());

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

        String insert = "insert into goal(goalNumber, progress, description, type, creationDate, expiryDate, completionDate, " +
                "goalDuration, goalDistance, firstName, lastName) values (?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        statement = connection.prepareStatement(insert);
        // set the wildcards (indexed from 1)
        statement.setString(1, String.valueOf(goal.getNumber()));
        statement.setString(2, String.valueOf(goal.getProgress()));
        statement.setString(3, goal.getDescription());
        statement.setString(4, String.valueOf(goal.getType()));
        statement.setString(5, String.valueOf(goal.getCreationDate()));
        statement.setString(6, String.valueOf(goal.getExpiryDate()));
        statement.setString(7, String.valueOf(goal.getCompletionDate()));
        statement.setString(8, String.valueOf(goal.getGoalDuration()));
        statement.setString(9, String.valueOf(goal.getGoalDistance()));
        statement.setString(10, goalOwner.getFirstName());
        statement.setString(11, goalOwner.getLastName());

        statement.executeUpdate();

        statement.close();
    }

    /** Create the statement for inserting a dataRow
     *
     * @param dataRow the dataRow being inserted
     * @return a PreparedStatement for inserting the dataRow
     * @throws SQLException if an error occurred regarding the database
     */
    private static PreparedStatement initInsertDataRowStatement(DataRow dataRow) throws SQLException {
        assert dataRow != null;

        String insert = "insert into dataRow (rowNumber, rowDate, time, heartRate, latitude, longitude, elevation, " +
                "name, activityDate, firstName, lastName) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insert);

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

        return statement;
    }

    /** Add a dataRow to the database
     *  If the combination of dataRow number and activty name/date is not unique, the dataRow will not be added.
     *  It is assumed that all dataRow fields are correctly formatted.
     *
     * @param dataRow the data row to be added
     * @throws SQLException if an error occurred regarding the database
     */
    public static void insertDataRow(DataRow dataRow) throws SQLException {
        statement = initInsertDataRowStatement(dataRow);
        statement.executeUpdate();
        statement.close();
    }


    /** Insert a list of dataRows into the database using a transaciton
     *
     * @param rows the list of dataRows to be inserted
     * @throws SQLException if an error occurred regarding the database
     */
    public static void insertDataRowTransaction(List<DataRow> rows) throws SQLException {

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
        for (Activity activity : profile.getActivityList()) {
            deleteActivity(activity, profile);
        }

        // Delete all goals belonging to a profile
        for (Goal goal: profile.getGoalList()) {
            deleteGoal(goal, profile);
        }

    }

    /** Delete an activity from the database along with its associated data rows
     *
     * @param activity the activity to be deleted
     * @param profile the profile which the activity belongs to
     * @throws SQLException if an error occurred regarding the database
     */
    public static void deleteActivity(Activity activity, Profile profile) throws SQLException {
        assert activity != null;

        // Delete Activity
        String select = "delete from activity where " +
                "name = (?) " +
                "and activityDate = (?)" +
                "and firstName = (?)" +
                "and lastName = (?)";
        statement = connection.prepareStatement(select);
        statement.setString(1, activity.getName());
        statement.setString(2, String.valueOf(activity.getDate()));
        statement.setString(3, profile.getFirstName());
        statement.setString(4, profile.getLastName());

        statement.executeUpdate();
        statement.close();

        // Delete all dataRows belonging to the activity
        for (DataRow row : activity.getRawData()) {
            System.out.println("deleting row");
            deleteDataRow(row, activity);
        }
    }

    /** Delete a goal from the database
     *
     * @param goal the goal to be deleted
     * @param profile the profile which the goal belongs to
     * @throws SQLException if an error occurred regarding the database
     */
    public static void deleteGoal(Goal goal, Profile profile) throws SQLException {
        assert goal != null;

        //Delete Goal
        String select = "delete from goal where " +
                "goalNumber = (?) " +
                "and firstName = (?) " +
                "and lastName = (?)";
        statement = connection.prepareStatement(select);
        statement.setString(1, String.valueOf(goal.getNumber()));
        statement.setString(2, profile.getFirstName());
        statement.setString(3, profile.getLastName());

        statement.executeUpdate();
        statement.close();
    }

    /** Delete a dataRow from the database
     *
     * @param row the dataRow to be deleted
     * @param activity the activity which the dataRow belongs to
     * @throws SQLException if an error occurred regarding the database
     */
    public static void deleteDataRow(DataRow row, Activity activity) throws SQLException {
        assert row != null;

        // Delete dataRow
        String select = "delete from dataRow where " +
                "rowNumber = (?) " +
                "and name = (?) " +
                "and activityDate = (?)";
        statement = connection.prepareStatement(select);
        statement.setString(1, String.valueOf(row.getNumber()));
        statement.setString(2, activity.getName());
        statement.setString(3, String.valueOf(activity.getDate()));

        statement.executeUpdate();
        statement.close();
    }


    public static void main(String[] args) throws SQLException {
        DataAccesser.initialiseMainConnection();
        DataAccesser.clearDatabase();


//        // Delete all dataRows from the database
//        String select = "delete from dataRow";
//        PreparedStatement statement = connection.prepareStatement(select);
//        statement.executeUpdate();

        Profile profile = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        insertProfile(profile);

        Activity activity = new Activity("Run in the park", "2018-08-29", "", ActivityType.Run,
                "12:15:01", "PT40M", 5.13, 18);
        insertActivity(activity, profile);

//        Goal goal = new Goal(1, 55, GoalType.Walk, "2018-03-20", "2020-01-01",
//                "2019-01-15", "Go for a walk", 2.00, 0);
//
//        insertGoal(goal, profile);
//
        DataRow row = new DataRow(1, "2018-07-18", "14:02:20", 182, -87.01902489,
                178.4352, 203);
//
        activity.addDataRow(row);
    }
}
