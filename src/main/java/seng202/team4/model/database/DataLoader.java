package seng202.team4.model.database;

import javafx.util.Pair;
import seng202.team4.model.data.*;
import seng202.team4.model.data.utilities.ProfileKey;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    /** Return the Activity in the database matching the name/date/profile
     *
     * @param name the name of the activty to be fetched as a String
     * @param date the date of the activity to be fetched as a LocalDate
     * @param activityOwner the profile owning the activity to be fetched
     * @return the activity matching the name/date as an Activity object
     * @throws SQLException if an error occurred regarding the database
     */
    public static Activity loadActivity(String name, LocalDate date, Profile activityOwner) throws SQLException {
        return null;
    }

    /** Return the goal in the database matching the number/profile
     *
     * @param goalNumber the number of the goal
     * @param goalOwner the Profile which owns the goal
     * @return the Goal object matching the parameters
     * @throws SQLException if an error occurred regarding the database
     */
    public static Goal loadGoal(int goalNumber, Profile goalOwner) throws SQLException {
        return null;
    }

    /** Return the DataRow matching the rowNumber and activity
     *
     * @param rowNumber the rowNumber of the row
     * @param activity the Activity which the row belongs to
     * @return the DataRow in the database matching the parameters
     * @throws SQLException if an error occurred regarding the database
     */
    public static DataRow loadDataRow(int rowNumber, Activity activity) throws SQLException {

        return null;
    }


    /** Return a List of tuples of (firstName, lastName) for each profile in the database
     *
     *  */
    public static List<ProfileKey> fetchAllProfileKeys() {
        // TODO: 28/08/18
        return new ArrayList<>();
    }

    public static Profile loadProfile(int profileId) {
        // TODO: 28/08/18
        return null;
    }
    
    public static List<String> fetchAllActivityKeys(Profile profile) {
        // TODO: 29/08/18  
        return null;
    }

}
