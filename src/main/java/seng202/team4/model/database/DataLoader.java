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
        statement = connection.prepareStatement(select);

        // set the wildcards (indexed from 1)
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        set = statement.executeQuery();

        Profile profile = null;
        // If a profile has been found, build the object
        if (set.next()) {
            profile = new Profile(
                    set.getString("firstName"),
                    set.getString("lastName"),
                    set.getString("dateOfBirth"),
                    set.getDouble("weight"),
                    set.getDouble("height"),
                    set.getString("pictureURL"));

            loadProfileActivities(profile);
            loadProfileGoals(profile);
        }

        statement.close();
        set.close();

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
        statement = connection.prepareStatement(select);

        // Set the wildcards (indexed from 1)
        statement.setString(1, profile.getFirstName());
        statement.setString(2, profile.getLastName());

        ResultSet set = statement.executeQuery();

        // Parse the result set into a list - ResultSet cursor starts 1 before the first row
        while (set.next()) {
            Activity activity = new Activity(
                    set.getString("name"),
                    set.getString("activityDate"),
                    ActivityType.valueOf(set.getString("type")),
                    set.getString("startTime"),
                    set.getString("duration"),
                    set.getDouble("distance"),
                    set.getDouble("caloriesBurned")
                    );
            // Set the owner of the activity as the profile
            activity.setOwner(profile);

            loadActivityDataRows(activity);
            activities.add(activity);
        }
        // Add all activities to the activity list (without reinserting into the database)
        profile.addAllActivities(activities);

        // Cleanup
        statement.close();
        set.close();
    }

    /** Load all goals belonging to a profile from the database into that profile's goal list
     *
     * @param profile the profile owning the goals (must be in the database already)
     */
    private static void loadProfileGoals(Profile profile) throws SQLException {
        //Initialise list
        List<Goal> currentGoals = new ArrayList<>();
        List<Goal> pastGoals = new ArrayList<>();

        // Select all activities for the profile
        String select = "SELECT * FROM goal where firstName = (?) and lastName = (?)";
        statement = connection.prepareStatement(select);

        // Set the wildcards (indexed from 1)
        statement.setString(1, profile.getFirstName());
        statement.setString(2, profile.getLastName());

        set = statement.executeQuery();

        // Parse the result set into a list - ResultSet cursor starts 1 before the first row
        while (set.next()) {
            Goal goal = new Goal(
                    set.getInt("goalNumber"),
                    set.getInt("progress"),
                    GoalType.valueOf(set.getString("type")),
                    set.getString("creationDate"),
                    set.getString("expiryDate"),
                    set.getString("completionDate"),
                    set.getDouble("goalDuration"),
                    set.getDouble("goalDistance"),
                    Boolean.valueOf(set.getString("current"))
                    );

            // Add the goal to the relevant list based on whether it is current or not
            if (goal.isCurrent()) {
                currentGoals.add(goal);
            } else {
                pastGoals.add(goal);
            }
            // Set the owner of the activity as the profile
            goal.setOwner(profile);
        }
        // Add all goals to the goal lists (without reinserting into the database)
        profile.addAllCurrentGoals(currentGoals);
        profile.addAllPastGoals(pastGoals);

        // Cleanup
        statement.close();
        set.close();
    }

    /** Load all dataRows belonging to an activity from the database into the activity
     *
     * @param activity the activity owning the dataRows (must be in database already)
     */
    private static void loadActivityDataRows(Activity activity) throws SQLException {
        //Initialise list
        List<DataRow> rows = new ArrayList<>();

        // Select all activities for the profile
        String select = "SELECT * FROM dataRow where name = (?) and activityDate = (?) and firstName = (?) " +
                "and lastName = (?)";
        statement = connection.prepareStatement(select);

        // Set the wildcards (indexed from 1)
        statement.setString(1, activity.getName());
        statement.setString(2, String.valueOf(activity.getDate()));
        statement.setString(3, String.valueOf(activity.getOwner().getFirstName()));
        statement.setString(4, String.valueOf(activity.getOwner().getLastName()));

        set = statement.executeQuery();

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

            // Set the owner of the DataRow
            row.setOwner(activity);
        }
        // Add all activities to the activity list (without reinserting into the database)
        activity.addAllDataRows(rows);

        // Cleanup
        statement.close();
        set.close();
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
        statement = connection.prepareStatement(select);
        set = statement.executeQuery();

        // Parse the result set into a list of ProfileKeys - ResultSet cursor starts 1 before the first row
        while (set.next()) {
            ProfileKey key = new ProfileKey(set.getString("firstName"),
                    set.getString("lastName"));
            profileKeys.add(key);
        }
        // Sort the list by its natural ordering
        java.util.Collections.sort(profileKeys);

        // Cleanup
        statement.close();
        set.close();

        return profileKeys;
    }
}
