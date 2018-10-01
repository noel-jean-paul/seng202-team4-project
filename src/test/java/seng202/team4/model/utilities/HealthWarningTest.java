package seng202.team4.model.utilities;

import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.Profile;

import java.time.Month;
import java.util.ArrayList;

public class HealthWarningTest {

    private static Profile testProfile;

    @BeforeClass
    public static void setUpProfile() {
        testProfile = new Profile("Jane", "Doe", 1998, Month.MAY, 18, 78.6, 182);
        ArrayList<Activity> activityList = new ArrayList<>();

        ArrayList<DataRow> noIssueWalking = new ArrayList<>(); // Walking activity with no issues
        noIssueWalking.add(new DataRow(0, "2015-04-10", "23:42:28", 69, 30.2553368, -97.83891084, 301));
        noIssueWalking.add(new DataRow(0, "2015-04-10","23:43:05",87,30.25499189,-97.83913958,285));
        noIssueWalking.add(new DataRow(0, "2015-04-10","23:43:15",132,30.25469617,-97.83931962,302));
        noIssueWalking.add(new DataRow(0, "2015-04-10","23:43:34",156,30.2541537,-97.83977501,249));

        activityList.add(new Activity("Walk in the woods", noIssueWalking));

        testProfile.addAllActivities(activityList);
    }

    @Test
    public void tachycardiaRunning() {
    }

    @Test
    public void  tachycardiaWalking() {

    }

    @Test
    public void bradycardiaRunning() {

    }

    @Test
    public void bradycardiaWalking() {

    }

    @Test
    public void cardiovascular() {

    }

    @Test
    public void noWarningsWalking() {

    }

    @Test
    public void noWarningsRunning() {

    }

    @Test
    public void noWarningsOther() {

    }
}