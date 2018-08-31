package seng202.team4.model.database;

import javafx.util.Pair;
import seng202.team4.model.data.*;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.utilities.ProfileKey;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

abstract public class DataLoader extends DataAccesser {

    /** Return the Profile in the database matching the first/last names.
     *
     * @param firstName the firstName of the desired profile
     * @param lastName the lastName of the desired profile
     * @return the profile matching the names as a Profile object
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

        return new Profile(
                set.getString("firstName"),
                set.getString("lastName"),
                set.getString("dateOfBirth"),
                set.getDouble("weight"),
                set.getDouble("height"));
    }

//    /** Return the Activity in the database matching the name/date/profile
//     *
//     * @param name the name of the activty to be fetched as a String
//     * @param date the date of the activity to be fetched as a LocalDate
//     * @param activityOwner the profile owning the activity to be fetched
//     * @return the activity matching the name/date as an Activity object
//     * @throws SQLException if an error occurred regarding the database
//     */
////    public static Activity loadActivity(String name, LocalDate date, Profile activityOwner) throws SQLException {
////        // Select profile matching first name and last name
////        String select = "SELECT * FROM activity WHERE name = (?) AND activityDate = (?) " +
////                "AND firstName = (?) AND lastName = (?)";
////        PreparedStatement statement = connection.prepareStatement(select);
////
////        // set the wildcards (indexed from 1)
////        statement.setString(1, name);
////        statement.setString(2, String.toStringdate);
////        ResultSet set = statement.executeQuery();
////
////        return new Profile(
////                set.getString("firstName"),
////                set.getString("lastName"),
////                set.getString("dateOfBirth"),
////                set.getDouble("weight"),
////                set.getDouble("height"));
////    }


    /** Load all activities belonging to a profile from the database into that profile's activity list.
     *
     * @param profile the profile owning the activities (must be in the database already)
     */
    public static void loadProfileActivities(Profile profile) throws SQLException {
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
            activities.add(activity);
        }
        // Add all activities to the activity list
        profile.addAllActivities(activities);
    }

    /** Load all goals belonging to a profile from the database
     *
     * @param profile the profile owning the goals (must be in the database already)
     */
    public static void loadProfileGoals(Profile profile) {
        // TODO: 31/08/18
    }

    /** Load all dataRows belonging to an activity from the database
     *
     * @param activity the activity owning the dataRows (must be in database already)
     */
    public static void loadActivityDataRows(Activity activity) {
        // TODO: 31/08/18
    }

//    /** Return the goal in the database matching the number/profile
//     *
//     * @param goalNumber the number of the goal
//     * @param goalOwner the Profile which owns the goal
//     * @return the Goal object matching the parameters
//     * @throws SQLException if an error occurred regarding the database
//     */
//    public static Goal loadGoal(int goalNumber, Profile goalOwner) throws SQLException {
//        return null;
//    }
//
//    /** Return the DataRow matching the rowNumber and activity
//     *
//     * @param rowNumber the rowNumber of the row
//     * @param activity the Activity which the row belongs to
//     * @return the DataRow in the database matching the parameters
//     * @throws SQLException if an error occurred regarding the database
//     */
//    public static DataRow loadDataRow(int rowNumber, Activity activity) throws SQLException {
//
//        return null;
//    }


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

    
    public static List<String> fetchAllActivityKeys(Profile profile) {
        // TODO: 29/08/18  
        return null;
    }

}
