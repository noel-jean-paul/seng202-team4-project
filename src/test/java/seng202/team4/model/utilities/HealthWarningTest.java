package seng202.team4.model.utilities;

import org.junit.BeforeClass;
import org.junit.Test;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.WarningType;

import java.time.Month;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

        ArrayList<DataRow> noIssuesRunning = new ArrayList<>(); // Running activity with no issues
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:00:42", 157, 30.245576, -97.823843, 220.3));
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:02:24", 156, 30.246356, -97.823326, 220.3));
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:03:28", 153, 30.246539, -97.821931, 209.7));
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:04:15", 149, 30.247105, -97.821064, 207.8));
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:06:45", 147, 30.247719, -97.820641, 205.4));
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:08:42", 143, 30.248482, -97.820708, 207.3));
        noIssuesRunning.add(new DataRow(0, "2015-04-12", "22:09:41", 143, 30.24915, -97.820722, 206.8));

        ArrayList<DataRow> noIssuesOther = new ArrayList<>();
        noIssuesOther.add(new DataRow(0,"2015-01-01", "14:49:18", 155, 30.27140097, -97.83250907, 181.8));
        noIssuesOther.add(new DataRow(0,"2015-01-01", "14:49:31", 160, 30.2713112, -97.83239139, 181.8));
        noIssuesOther.add(new DataRow(0,"2015-01-01", "14:50:42", 161, 30.27114474, -97.83244051, 182.3));
        noIssuesOther.add(new DataRow(0,"2015-01-01", "14:50:50", 165, 30.27101104, -97.83245644, 180.9));
        noIssuesOther.add(new DataRow(0,"2015-01-01", "14:50:55", 168, 30.27094273, -97.83243976, 180.4));
        noIssuesOther.add(new DataRow(0,"2015-01-01", "14:51:05", 168, 30.2707476, -97.83242081, 175.1));

        ArrayList<DataRow> runningTachy = new ArrayList<>();
        runningTachy.add(new DataRow(0, "2015-04-21", "23:08:58", 171, 30.260248, -97.826809, 206.8));
        runningTachy.add(new DataRow(0, "2015-04-21", "23:09:14", 175, 30.260321, -97.826938, 204.4));
        runningTachy.add(new DataRow(0, "2015-04-21", "23:09:35", 179, 30.260298, -97.827100, 206.3));
        runningTachy.add(new DataRow(0, "2015-04-21", "23:09:55", 188, 30.260317, -97.827157, 206.8));
        runningTachy.add(new DataRow(0, "2015-04-21", "23:10:17", 198, 30.260384, -97.827376, 210.7));
        runningTachy.add(new DataRow(0, "2015-04-21", "23:10:29", 201, 30.260435, -97.827472, 211.6));
        runningTachy.add(new DataRow(0, "2015-04-21", "23:10:47", 195, 30.260415, -97.827658, 211.1));

        ArrayList<DataRow> runningBrady = new ArrayList<>();
        runningBrady.add(new DataRow(0, "2015-04-21", "23:08:58", 49, 30.260248, -97.826809, 206.8));
        runningBrady.add(new DataRow(0, "2015-04-21", "23:09:14", 56, 30.260321, -97.826938, 204.4));
        runningBrady.add(new DataRow(0, "2015-04-21", "23:09:35", 59, 30.260298, -97.827100, 206.3));
        runningBrady.add(new DataRow(0, "2015-04-21", "23:09:55", 68, 30.260317, -97.827157, 206.8));
        runningBrady.add(new DataRow(0, "2015-04-21", "23:10:17", 72, 30.260384, -97.827376, 210.7));
        runningBrady.add(new DataRow(0, "2015-04-21", "23:10:29", 64, 30.260435, -97.827472, 211.6));
        runningBrady.add(new DataRow(0, "2015-04-21", "23:10:47", 76, 30.260415, -97.827658, 211.1));

        ArrayList<DataRow> walkingTachy = new ArrayList<>();
        walkingTachy.add(new DataRow(0, "2015-04-12", "22:11:18", 160, 30.249804, -97.820530, 199.6));
        walkingTachy.add(new DataRow(0, "2015-04-12", "22:22:10", 175, 30.248700, -97.820208, 208.3));
        walkingTachy.add(new DataRow(0, "2015-04-12", "22:30:38", 186, 30.248519, -97.819302, 221.2));
        walkingTachy.add(new DataRow(0, "2015-04-12", "22:31:11", 197, 30.248078, -97.818726, 222.7));
        walkingTachy.add(new DataRow(0, "2015-04-12", "22:33:40", 201, 30.247373, -97.817461, 218.8));
        walkingTachy.add(new DataRow(0, "2015-04-12", "22:34:17", 195, 30.246768, -97.817091, 220.3));
        walkingTachy.add(new DataRow(0, "2015-04-12", "22:33:40", 185, 30.247373, -97.817461, 218.8));
        walkingTachy.add(new DataRow(0, "2015-04-12", "22:36:48", 184, 30.246036, -97.818323, 219.3));

        ArrayList<DataRow> walkingBrady = new ArrayList<>();
        walkingBrady.add(new DataRow(0, "2015-04-12", "22:11:18", 51, 30.249804, -97.820530, 199.6));
        walkingBrady.add(new DataRow(0, "2015-04-12", "22:22:10", 49, 30.248700, -97.820208, 208.3));
        walkingBrady.add(new DataRow(0, "2015-04-12", "22:30:38", 45, 30.248519, -97.819302, 221.2));
        walkingBrady.add(new DataRow(0, "2015-04-12", "22:31:11", 69, 30.248078, -97.818726, 222.7));
        walkingBrady.add(new DataRow(0, "2015-04-12", "22:33:40", 53, 30.247373, -97.817461, 218.8));
        walkingBrady.add(new DataRow(0, "2015-04-12", "22:34:17", 63, 30.246768, -97.817091, 220.3));
        walkingBrady.add(new DataRow(0, "2015-04-12", "22:33:40", 58, 30.247373, -97.817461, 218.8));
        walkingBrady.add(new DataRow(0, "2015-04-12", "22:36:48", 61, 30.246036, -97.818323, 219.3));



        activityList.add(new Activity("Walk in the woods", noIssueWalking));
        activityList.add(new Activity("Run around the block", noIssuesRunning));
        activityList.add(new Activity("Exercise with friends", noIssuesOther));
        activityList.add(new Activity("Longer Run", runningTachy));
        activityList.add(new Activity("Shorter Run", runningBrady));
        activityList.add(new Activity("Walk up the mountains", walkingTachy));
        activityList.add(new Activity("Hike up the mountains", walkingBrady));


        testProfile.addAllActivities(activityList);
    }

    @Test
    public void tachycardiaRunning() {
        Activity testedActivity = testProfile.getActivityList().get(5);
        HealthWarning tachyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Tachy, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        boolean hasHealthRisks = tachyWarning.isHealthRisk();
        assertTrue(hasHealthRisks);
    }

    @Test
    public void  tachycardiaWalking() {
        Activity testedActivity = testProfile.getActivityList().get(3);
        HealthWarning tachyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Tachy, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        boolean hasHealthRisks = tachyWarning.isHealthRisk();
        assertTrue(hasHealthRisks);
    }

    @Test
    public void bradycardiaRunning() {
        Activity testedActivity = testProfile.getActivityList().get(6);
        HealthWarning tachyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Brady, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        boolean hasHealthRisks = tachyWarning.isHealthRisk();
        assertTrue(hasHealthRisks);
    }

    @Test
    public void bradycardiaWalking() {
        Activity testedActivity = testProfile.getActivityList().get(4);
        HealthWarning tachyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Brady, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        boolean hasHealthRisks = tachyWarning.isHealthRisk();
        assertTrue(hasHealthRisks);
    }

    @Test
    public void noWarningsWalking() { // Walk in the woods is in position 1 after sorting.
        Activity testedActivity = testProfile.getActivityList().get(1);
        HealthWarning tachyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Tachy, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        HealthWarning bradyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Brady, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        boolean hasHealthRisks = (tachyWarning.isHealthRisk() || bradyWarning.isHealthRisk());
        assertFalse(hasHealthRisks);
    }

    @Test
    public void noWarningsRunning() { // Run around the block is in position 2 after sorting.
        Activity testedActivity = testProfile.getActivityList().get(2);
        HealthWarning tachyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Tachy, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        HealthWarning bradyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Brady, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        boolean hasHealthRisks = (tachyWarning.isHealthRisk() || bradyWarning.isHealthRisk());
        assertFalse(hasHealthRisks);
    }

    @Test
    public void noWarningsOther() {
        Activity testedActivity = testProfile.getActivityList().get(0); // Exercise with friends is in position 0 after sorting.
        HealthWarning tachyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Tachy, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        HealthWarning bradyWarning = new HealthWarning(testedActivity, testProfile, WarningType.Brady, testedActivity.getAvgHeartRate(), testedActivity.getMinHeartRate(), testedActivity.getMaxHeartRate());
        boolean hasHealthRisks = (tachyWarning.isHealthRisk() || bradyWarning.isHealthRisk());
        assertFalse(hasHealthRisks);
    }
}