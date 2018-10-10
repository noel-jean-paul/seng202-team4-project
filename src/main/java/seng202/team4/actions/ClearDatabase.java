package seng202.team4.actions;

import seng202.team4.model.database.DataAccesser;

import java.sql.SQLException;

@SuppressWarnings("ALL")
public class ClearDatabase {
    public static void main() throws SQLException {
        DataAccesser.initialiseMainConnection();
        DataAccesser.clearDatabase();
    }
}

