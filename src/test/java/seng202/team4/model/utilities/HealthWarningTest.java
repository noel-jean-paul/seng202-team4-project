package seng202.team4.model.utilities;

import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.WarningType;

import java.time.Month;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class HealthWarningTest {

    static Profile testProfile;

    @BeforeClass
    public static void setUpProfile() {
        testProfile = new Profile("Jane", "Doe", 1998, Month.MAY, 18, 78.6, 182);
        ArrayList<Activity> activityList = new ArrayList<>();

        ArrayList<DataRow> noIssueWalking = new ArrayList<>(); // Walking activity with no issues
        noIssueWalking.add(new DataRow(0, "2015-04-10", "23:42:28", 69, 30.2553368, -97.83891084, 301));
        noIssueWalking.add(new DataRow(0, "2015-04-10","23:43:05",87,30.25499189,-97.83913958,285));
        noIssueWalking.add(new DataRow(0, "2015-04-10","23:43:15",132,30.25469617,-97.83931962,302));
        noIssueWalking.add(new DataRow(0, "2015-04-10","23:43:34",156,30.2541537,-97.83977501,249));

        ArrayList<DataRow> noIssuesRunning = new ArrayList<>(); // Running activity with no issues
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:00:42", 157, 30.245576, -97.823843, 220.3));
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:02:24", 156, 30.246356, -97.823326, 220.3));
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:03:28", 153, 30.246539, -97.821931, 209.7));
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:04:15", 149, 30.247105, -97.821064, 207.8));
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:06:45", 147, 30.247719, -97.820641, 205.4));
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:08:42", 143, 30.248482, -97.820708, 207.3));
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:09:41", 143, 30.24915, -97.820722, 206.8));


        activityList.add(new Activity("Walk in the woods", noIssueWalking));
        activityList.add(new Activity("Run around the block", noIssuesRunning));

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
        Activity testedActivity = testProfile.getActivityList().get(0);
        HealthWarning tachyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Tachy, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        HealthWarning bradyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Brady, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        HealthWarning cardioWarning = new HealthWarning(testedActivity, testProfile, WarningType.Cardiovascular, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        boolean hasHealthRisks = (tachyWarning.isHealthRisk() || bradyWarning.isHealthRisk() || cardioWarning.isHealthRisk());
        assertFalse(hasHealthRisks);
    }

    @Test
    public void noWarningsRunning() {
        Activity testedActivity = testProfile.getActivityList().get(1);
        HealthWarning tachyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Tachy, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        HealthWarning bradyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Brady, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        HealthWarning cardioWarning = new HealthWarning(testedActivity, testProfile, WarningType.Cardiovascular, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        boolean hasHealthRisks = (tachyWarning.isHealthRisk() || bradyWarning.isHealthRisk() || cardioWarning.isHealthRisk());
        assertFalse(hasHealthRisks);
    }

    @Test
    public void noWarningsOther() {

    }
}