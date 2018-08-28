package seng202.team4.model.database;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class dataStorerTest {
    private Connection connection;


    @Before
    public void setUp() throws SQLException {
        String url = "jdbc:sqlite:fitness_tracker.sqlite";
        connection = DriverManager.getConnection(url);
    }

    @Test
    public void insertProfile() {


    }
}