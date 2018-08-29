package seng202.team4.model.database;

import seng202.team4.model.data.*;
import seng202.team4.model.data.enums.*;

import java.sql.*;


public class dataStorer {

    private static Connection connection = null;

    /** Initialise the connection the database at the root of the project. */
    public static void initialiseConnection() {
        String url = "jdbc:sqlite:fitness_tracker.sqlite";
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

//    /** Count the number of rows in the database with the given firstName and lastName
//     *
//     * @param firstName a non-null firstName to match to profiles in the database
//     * @param lastName   a non-null lastName to match to profiles in the database
//     * @return the number of rows in the database with the given firstName and lastName as an Integer
//     * @throws SQLException if an error occurred regarding the database
//     */
//    public static int countRows(String firstName, String lastName) throws SQLException {
//        // Execute a count query
//        String select = "select count(*) from profile where firstName = (?) and lastName = (?)";
//        PreparedStatement statement = connection.prepareStatement(select);
//        // set the wildcards (indexed from 1)
//        statement.setString(1, firstName);
//        statement.setString(1, lastName);
//        ResultSet count = statement.executeQuery();
//        // return the count stored in the result set
//        return count.getInt(1);
//    }

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
        PreparedStatement statement = connection.prepareStatement(insert);
        // set the wildcards (indexed from 1)
        statement.setString(1, profile.getFirstName());
        statement.setString(2, profile.getLastName());
        statement.setString(3, String.valueOf(profile.getDateOfBirth()));
        statement.setString(4, String.valueOf(profile.getHeight()));
        statement.setString(5, String.valueOf(profile.getWeight()));

        statement.executeUpdate();
    }

    /** Add an activity to the database.
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
        PreparedStatement statement = connection.prepareStatement(insert);
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
    }

    /** Add an goal to the database.
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
        PreparedStatement statement = connection.prepareStatement(insert);
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
    }

    /** Add a dataRow to the database.
     *  If the combination of dataRow number and activty name/date is not unique, the dataRow will not be added.
     *  It is assumed that all dataRow fields are correctly formatted.
     *
     * @param dataRow the data row to be added
     * @param activity the activity which the data row belongs to. Assumed to be in the database already.
     * @throws SQLException if an error occurred regarding the database
     */
    public static void insertDataRow(DataRow dataRow, Activity activity) throws SQLException {
        assert dataRow != null;

        String insert = "insert into dataRow(rowNumber, rowDate, time, heartRate, latitude, longitude, altitude, " +
                "name, activityDate) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insert);
        // set the wildcards (indexed from 1)
        statement.setString(1, String.valueOf(dataRow.getNumber()));
        statement.setString(2, String.valueOf(dataRow.getDate()));
        statement.setString(3, String.valueOf(dataRow.getTime()));
        statement.setString(4, String.valueOf(dataRow.getHeartRate()));
        statement.setString(5, String.valueOf(dataRow.getLatitude()));
        statement.setString(6, String.valueOf(dataRow.getLongitude()));
        statement.setString(7, String.valueOf(dataRow.getAltitude()));
        statement.setString(8, activity.getName());
        statement.setString(9, String.valueOf(activity.getDate()));

        statement.executeUpdate();
    }

    public static void main(String[] args) throws SQLException {
        initialiseConnection();
        Profile profile = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
                1.83);
        insertProfile(profile);

        Activity activity = new Activity("Run in the park", "2018-08-29", "", ActivityType.Run,
                "12:15:01", "00:40:00", 5.13, 18, 7.7);

        insertActivity(activity, profile);

        Goal goal = new Goal(1, 55, GoalType.Walk, "2018-03-20", "2020-01-01",
                "2019-01-15", "Go for a walk", 2.00, 0);

        insertGoal(goal, profile);

        DataRow row = new DataRow(1, "2018-07-18", "14:02:20", 182, -87.01902489,
                178.4352, 203);

        insertDataRow(row, activity);
    }



}
