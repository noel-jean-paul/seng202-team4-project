package seng202.team4.model.database;

import seng202.team4.model.data.*;

import java.sql.*;
import java.util.List;

public class dataLoader {

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

    /** Return the Profile in the database matching the first/last names.
     *
     * @param firstName the firstName of the desired profile
     * @param lastName the lastName of the desired profile
     * @return the profile matching the names as a Profile object
     * @throws SQLException if an error occurred regarding the database
     */
    public static Profile loadProfile(String firstName, String lastName) throws SQLException {
        // Select profile matching first name and last name
        String select = "select * from profile where firstName = (?) and lastName = (?)";
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

    /** Return a List of tuples of (firstName, lastName) for each profile in the database */
    public static List<String> fetchAllProfileNames() {
        // TODO: 28/08/18
        return null;
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
