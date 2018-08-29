package seng202.team4.model.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract public class DataTestHelper extends DataAccesser {

    /** Removes all data from the database
     *
     * @throws SQLException if an error occurs regarding the database
     */
    public static void clearDatabase() throws SQLException {
        // Delete all profiles from the database
        String select = "delete from profile";
        PreparedStatement statement = connection.prepareStatement(select);
        statement.executeUpdate();

        // Delete all activities from the database
        select = "delete from activity";
        statement = connection.prepareStatement(select);
        statement.executeUpdate();

        // Delete all goals from the database
        select = "delete from goal";
        statement = connection.prepareStatement(select);
        statement.executeUpdate();

        // Delete all dataRows from the database
        select = "delete from dataRow";
        statement = connection.prepareStatement(select);
        statement.executeUpdate();
    }
}
