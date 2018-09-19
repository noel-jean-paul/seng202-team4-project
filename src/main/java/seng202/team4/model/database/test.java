package seng202.team4.model.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class test extends DataAccesser {
    public static void main(String[] args) throws SQLException {
        DataAccesser.initialiseMainConnection();
        // Delete all activities from the database
        String select = "delete from activity";
        PreparedStatement statement = connection.prepareStatement(select);
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
