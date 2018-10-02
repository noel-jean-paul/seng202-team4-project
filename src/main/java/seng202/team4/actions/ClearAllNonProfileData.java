package seng202.team4.actions;

import seng202.team4.model.database.DataAccesser;

import java.sql.SQLException;

public class ClearAllNonProfileData {
    public static void main(String[] args) throws SQLException {
        DataAccesser.initialiseMainConnection();
        DataAccesser.clearNonProfileData();
    }
}
