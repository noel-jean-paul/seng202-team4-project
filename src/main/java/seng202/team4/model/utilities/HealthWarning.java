package seng202.team4.model.utilities;

import seng202.team4.model.data.Activity;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.WarningType;

import java.time.Duration;

public class HealthWarning {
    private int avgHeartRate;
    private int minHeartRate;
    private int maxHeartRate;
    private String description;
    private Activity activity;
    private Profile user;
    private WarningType type;
    private boolean healthRisk;

    public HealthWarning(Activity activity, Profile user, WarningType type, int avgHearRate, int minHeartRate, int maxHeartRate) {
        this.activity = activity;
        this.user = user;
        this.avgHeartRate = avgHearRate;
        this.minHeartRate = minHeartRate;
        this.maxHeartRate = maxHeartRate;
        this.type = type;
        this.healthRisk = checkRisk();
    }

    private boolean checkRisk() {
        boolean atRisk;
        if (activity.getType() == ActivityType.Run) {
            atRisk = runningRisk();
        } else if (activity.getType() == ActivityType.Walk) {
            atRisk = walkingRisk();
        } else {
            if (activity.getAverageSpeed() > 7) {
                atRisk = runningRisk();
            } else {
                atRisk = walkingRisk();
            }
        }
        return atRisk;
    }

    private boolean runningRisk() {
        boolean atRisk = false;
        if (type == WarningType.Tachy) {
            atRisk = tachyRisk();
        } else if (type == WarningType.Brachy) {
            atRisk = brachyRisk();
        }
        return atRisk;
    }

    private boolean walkingRisk() {
        boolean atRisk;
        if (type == WarningType.Tachy) {
            atRisk = tachyRisk();
        } else if (type == WarningType.Brachy) {
            atRisk = brachyRisk();
        } else {
            atRisk = cardioRisk();
        }
        return atRisk;
    }

    private boolean tachyRisk() {
        if (maxHeartRate > (220 - user.getAge())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean brachyRisk() {
        if (user.getAge() >= 18 && minHeartRate < 50) {
            return true;
        } else if (user.getAge() < 18 && minHeartRate < 60) {
            return true;
        } else {
            return false;
        }
    }

    private boolean cardioRisk() {
        if (minHeartRate > 83) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isHealthRisk() {
        return healthRisk;
    }

    public int getAvgHeartRate() {
        return avgHeartRate;
    }

    public String getAvgHeartRateString() {
        return Integer.toString(avgHeartRate);
    }

    public int getMinHeartRate() {
        return avgHeartRate;
    }

    public String getMinHeartRateString() {
        return Integer.toString(minHeartRate);
    }

    public int getMaxHeartRate() {
        return maxHeartRate;
    }

    public String getMaxHeartRateString() {
        return Integer.toString(maxHeartRate);
    }


}
