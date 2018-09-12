package seng202.team4.model.database;

import seng202.team4.model.data.*;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.GoalType;
import seng202.team4.model.data.ProfileKey;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

abstract public class DataLoader extends DataAccesser {
    /** Return the Profile in the database matching the first/last names.
     *
     * @param firstName the firstName of the desired profile
     * @param lastName the lastName of the desired profile
     * @return the profile matching the names as a Profile object or null if no such profile exists
     * @throws SQLException if an error occurred regarding the database
     */
    public static Profile loadProfile(String firstName, String lastName) throws SQLException {

        // Select profile matching first name and last name
        String select = "SELECT * FROM profile WHERE firstName = (?) AND lastName = (?)";
        PreparedStatement statement = connection.prepareStatement(select);

        // set the wildcards (indexed from 1)
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        ResultSet set = statement.executeQuery();

        Profile profile = null;
        // If a profile has been found, build the object
        if (set.next()) {
            profile = new Profile(
                    set.getString("firstName"),
                    set.getString("lastName"),
                    set.getString("dateOfBirth"),
                    set.getDouble("weight"),
                    set.getDouble("height"));

            loadProfileActivities(profile);
            loadProfileGoals(profile);
        }

        return profile;
    }

    /** Load all activities belonging to a profile from the database into that profile's activity list.
     *
     * @param profile the profile owning the activities (must be in the database already)
     */
    private static void loadProfileActivities(Profile profile) throws SQLException {
        //Initialise list
        List<Activity> activities = new ArrayList<>();

        // Select all activities for the profile
        String select = "SELECT * FROM activity where firstName = (?) and lastName = (?)";
        PreparedStatement statement = connection.prepareStatement(select);

        // Set the wildcards (indexed from 1)
        statement.setString(1, profile.getFirstName());
        statement.setString(2, profile.getLastName());

        ResultSet set = statement.executeQuery();

        // Parse the result set into a list - ResultSet cursor starts 1 before the first row
        while (set.next()) {
            Activity activity = new Activity(
                    set.getString("name"),
                    set.getString("activityDate"),
                    set.getString("description"),
                    ActivityType.valueOf(set.getString("type")),
                    set.getString("startTime"),
                    set.getString("duration"),
                    set.getDouble("distance"),
                    set.getInt("caloriesBurned")
                    );
            loadActivityDataRows(activity);
            activities.add(activity);
        }
        // Add all activities to the activity list
        profile.addAllActivities(activities);
    }

    /** Load all goals belonging to a profile from the database into that profile's goal list
     *
     * @param profile the profile owning the goals (must be in the database already)
     */
    private static void loadProfileGoals(Profile profile) throws SQLException {
        //Initialise list
        List<Goal> goals = new ArrayList<>();

        // Select all activities for the profile
        String select = "SELECT * FROM goal where firstName = (?) and lastName = (?)";
        PreparedStatement statement = connection.prepareStatement(select);

        // Set the wildcards (indexed from 1)
        statement.setString(1, profile.getFirstName());
        statement.setString(2, profile.getLastName());

        ResultSet set = statement.executeQuery();

        // Parse the result set into a list - ResultSet cursor starts 1 before the first row
        while (set.next()) {
            Goal goal = new Goal(
                    set.getInt("goalNumber"),
                    set.getDouble("progress"),
                    GoalType.valueOf(set.getString("type")),
                    set.getString("description"),
                    set.getString("creationDate"),
                    set.getString("expiryDate"),
                    set.getString("completionDate"),
                    set.getDouble("goalDuration"),
                    set.getDouble("goalDistance")
                    );
            goals.add(goal);
        }
        // Add all activities to the activity list
        profile.addAllGoals(goals);
    }

    /** Load all dataRows belonging to an activity from the database into the activity
     *
     * @param activity the activity owning the dataRows (must be in database already)
     */
    private static void loadActivityDataRows(Activity activity) throws SQLException {
        //Initialise list
        List<DataRow> rows = new ArrayList<>();

        // Select all activities for the profile
        String select = "SELECT * FROM dataRow where name = (?) and activityDate = (?)";
        PreparedStatement statement = connection.prepareStatement(select);

        // Set the wildcards (indexed from 1)
        statement.setString(1, activity.getName());
        statement.setString(2, String.valueOf(activity.getDate()));

        ResultSet set = statement.executeQuery();

        // Parse the result set into a list - ResultSet cursor starts 1 before the first row
        while (set.next()) {
            DataRow row = new DataRow(
                    set.getInt("rowNumber"),
                    set.getString("rowDate"),
                    set.getString("time"),
                    set.getInt("heartRate"),
                    set.getDouble("latitude"),
                    set.getDouble("longitude"),
                    set.getDouble("elevation")
            );
            rows.add(row);
        }
        // Add all activities to the activity list
        activity.addAllDataRows(rows);
    }

    /** Return a List of ProfileKeys - 1 key for each profile in the database. The list is sorted alphabetically
     *  by first name with last name as a secondary sorting attribute.
     *
     * @return a sorted list of ProfileKeys -> effectively (firstName, lastName) tuples
     * @throws SQLException if an error occurred regarding the database
     */
    public static List<ProfileKey> fetchAllProfileKeys() throws SQLException {
        //Initialise list
        List<ProfileKey> profileKeys = new ArrayList<>();

        // Select all profiles
        String select = "SELECT firstName, lastName FROM profile";
        PreparedStatement statement = connection.prepareStatement(select);
        ResultSet set = statement.executeQuery();

        // Parse the result set into a list of ProfileKeys - ResultSet cursor starts 1 before the first row
        while (set.next()) {
            ProfileKey key = new ProfileKey(set.getString("firstName"),
                    set.getString("lastName"));
            profileKeys.add(key);
        }
        // Sort the list by its natural ordering
        java.util.Collections.sort(profileKeys);

        return profileKeys;
    }
}
