package seng202.team4.model.utilities;

import seng202.team4.model.data.Activity;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.WarningType;

import java.time.LocalDate;

public class HealthWarning {
    private int avgHeartRate;
    private int minHeartRate;
    private int maxHeartRate;
    private String description;
    private String url;
    private Activity activity;
    private Profile user;
    private WarningType type;
    private String typeString;
    private LocalDate warningDate;
    private boolean healthRisk;

    public HealthWarning(Activity activity, Profile user, WarningType type, int avgHearRate, int minHeartRate, int maxHeartRate) {
        this.activity = activity;
        this.user = user;
        this.avgHeartRate = avgHearRate;
        this.minHeartRate = minHeartRate;
        this.maxHeartRate = maxHeartRate;
        this.type = type;
        this.warningDate = activity.getDate();
        this.healthRisk = checkRisk();
        this.url = setUpURL();
        this.typeString = calculateTypeString();
    }
    // TODO JavaDoc - Kenny

    private String calculateTypeString() {
        String typeStr;
        if (type == WarningType.Tachy) {
            typeStr = "Tachycardia";
        } else if (type == WarningType.Brady) {
            typeStr = "Bradycaria";
        } else {
            typeStr = "Cardiovascular Mortality";
        }
        return typeStr;
    }

    /**
     * @return
     */
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

    // TODO - Create modular approach to description setting

    /**
     * @return
     */
    private boolean runningRisk() {
        boolean atRisk = false;
        if (type == WarningType.Tachy) {
            atRisk = tachyRisk();
            description = "Heart rate in excess of recommended maximum.";
        } else if (type == WarningType.Brady) {
            atRisk = bradyRisk();
            description = "Heart rate under recommended minimum.";
        }
        return atRisk;
    }

    /**
     * @return
     */
    private boolean walkingRisk() {
        boolean atRisk;
        if (type == WarningType.Tachy) {
            atRisk = tachyRisk();
            description = "Heart rate in excess of recommended maximum.";
        } else if (type == WarningType.Brady) {
            atRisk = bradyRisk();
            description = "Heart rate under recommended minimum.";
        } else {
            atRisk = cardioRisk();
            description = "Resting heart rate in excess of recommend maximum";
        }
        return atRisk;
    }

    /**
     * @return
     */
    private boolean tachyRisk() {
        if (maxHeartRate > (220 - user.getAge())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return
     */
    private boolean bradyRisk() {
        if (user.getAge() >= 18 && minHeartRate < 50) {
            return true;
        } else if (user.getAge() < 18 && minHeartRate < 60) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return
     */
    private boolean cardioRisk() {
        if (minHeartRate > 83) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return
     */
    private String setUpURL() {
        String warningURL;
        if (type == WarningType.Brady) {
            warningURL = "https://www.google.com/search?q=tachycardia&ie=utf-8";
        } else if (type == WarningType.Tachy) {
            warningURL = "https://www.google.com/search?q=bradycardia&ie=utf-8";
        } else {
            warningURL = "https://www.google.com/search?q=cardiovascular+mortality&ie=utf-8";
        }
        return warningURL;
    }

    /**
     * @return
     */
    public boolean isHealthRisk() {
        return healthRisk;
    }

    /**
     * @return
     */
    public int getAvgHeartRate() {
        return avgHeartRate;
    }

    /**
     * @return
     */
    public String getAvgHeartRateString() {
        return Integer.toString(avgHeartRate);
    }

    /**
     * @return
     */
    public int getMinHeartRate() {
        return avgHeartRate;
    }

    /**
     * @return
     */
    public String getMinHeartRateString() {
        return Integer.toString(minHeartRate);
    }

    /**
     * @return
     */
    public int getMaxHeartRate() {
        return maxHeartRate;
    }

    /**
     * @return
     */
    public String getMaxHeartRateString() {
        return Integer.toString(maxHeartRate);
    }


    public LocalDate getWarningDate() {
        return warningDate;
    }

    public WarningType geType() {
        return type;
    }

    public String getTypeString() {
        return typeString;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
