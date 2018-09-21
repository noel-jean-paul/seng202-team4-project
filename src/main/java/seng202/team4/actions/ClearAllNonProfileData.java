package seng202.team4.actions;

import seng202.team4.model.database.DataAccesser;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClearAllNonProfileData {
    public static void main(String[] args) throws SQLException {
        DataAccesser.clearNonProfileData();
    }
}
