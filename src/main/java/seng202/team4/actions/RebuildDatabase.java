package seng202.team4.actions;

import seng202.team4.model.database.DataAccesser;

import java.sql.SQLException;

@SuppressWarnings("ALL")
public class RebuildDatabase {
    /** Rebuild the databases (drop tables and recreate them) */
    public static void main(String[] args) throws SQLException {
        DataAccesser.initialiseMainConnection();
        DataAccesser.initialiseTestConnection();
        DataAccesser.rebuildAllDatabases();
    }
}
