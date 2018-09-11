package seng202.team4.model.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ProfileFields;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DataUpdaterTest {

    @Before
    public void setUp() throws SQLException {
        // Initialise the database connection
        DataAccesser.initialiseConnection();

        // Remove all data from the database
        DataTestHelper.clearDatabase();
    }

    @After
    public void tearDown() throws Exception {
        // Remove all data from the database
        DataTestHelper.clearDatabase();

        // Close the database connection
        DataAccesser.connection.close();
    }

//    @Test
//    public void updateProfile() throws SQLException {
//        // Insert a new profile
//        Profile profile = new Profile("Noel", "Bisson", "1998-03-06", 85.0,
//                1.83);
//        DataStorer.insertProfile(profile);
//
//        // Define new values
//        String firstName = "Jeremy";
//        String lastName = "Renner";
//        String dob = "1997-05-27";
//        double height = 1.74;
//        int weight = 62;
//
//        // Update profile in database
//        List<Update> updates = new ArrayList<>(Arrays.asList(
//                new Update("firstName", firstName),
//                new Update("lastName", lastName),
//                new Update("dateOfBirth", dob),
//                new Update("height", Double.toString(height)),
//                new Update("weight", Integer.toString(weight))));
//        DataUpdater.updateProfile(profile, updates);
//
//        // Update external profile
//        profile.setFirstName(firstName);
//        profile.setLastName(lastName);
//        profile.setDateOfBirth(LocalDate.parse(dob));
//        profile.setHeight(height);
//        profile.setWeight(weight);
//
//        // Load the profile and check
//        Profile loadedProfile = DataLoader.loadProfile(profile.getFirstName(), profile.getLastName());
//        assertEquals(profile, loadedProfile);
//    }

    @Test
    public void updateActivity() {
    }

    @Test
    public void updateGoal() {
    }

    @Test
    public void updateDataRow() {
    }
}