package seng202.team4.model.utilities;

import seng202.team4.model.data.Activity;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.WarningType;

import java.time.LocalDate;
import java.util.Objects;

public class HealthWarning implements Comparable<HealthWarning> {
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

    /** Compare to another Goal based on goalNumber
     *  Consistent with equals as defined by Comparable
     *
     * @param o the HealthWaring to compare to
     * @return a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(HealthWarning o) {
        return getWarningDate().compareTo(o.getWarningDate()) * -1;     // Reverse order to descending
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthWarning that = (HealthWarning) o;
        return getAvgHeartRate() == that.getAvgHeartRate() &&
                getMinHeartRate() == that.getMinHeartRate() &&
                getMaxHeartRate() == that.getMaxHeartRate() &&
                isHealthRisk() == that.isHealthRisk() &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getUrl(), that.getUrl()) &&
                getType() == that.getType() &&
                Objects.equals(getTypeString(), that.getTypeString()) &&
                Objects.equals(getWarningDate(), that.getWarningDate()) &&
                getActivity().equals(that.getActivity()) &&
                getOwner().getFirstName().equals(that.getOwner().getFirstName()) &&
                getOwner().getLastName().equals(that.getOwner().getLastName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAvgHeartRate(), getMinHeartRate(), getMaxHeartRate(), getDescription(), getUrl(),
                getType(), getTypeString(), getWarningDate(), isHealthRisk());
    }

    // TODO JavaDoc - Kenny
    private String calculateTypeString() {
        String typeStr;
        if (type == WarningType.Tachy) {
            typeStr = "Tachycardia";
        } else if (type == WarningType.Brady) {
            typeStr = "Bradycardia";
        } else {
            typeStr = "Cardiovascular Mortality";
        }
        return typeStr;
    }

    /**
     * Checks the activity to see if a health risk was detected.
     * @return whether a risk was detected or not.
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
     * Checks all the relevant risks which are easily detectable while the user is performing a running activity.
     * @return whether a running risk was detected.
     */
    private boolean runningRisk() {
        boolean atRisk = false;
        if (type == WarningType.Tachy) {
            atRisk = tachyRisk();
            description = "Heart rate over recommended maximum.";
        } else if (type == WarningType.Brady) {
            atRisk = bradyRisk();
            description = "Heart rate under recommended minimum.";
        }
        return atRisk;
    }

    /**
     * Checks all the relevant risks which are easily detectable while the user is performing a walking activity.
     * @return whether a walking risk was detected.
     */
    private boolean walkingRisk() {
        boolean atRisk;
        if (type == WarningType.Tachy) {
            atRisk = tachyRisk();
            description = "Heart rate in over recommended maximum.";
        } else {
            atRisk = bradyRisk();
            description = "Heart rate under recommended minimum.";
        }
        return atRisk;
    }

    /**
     * Calculates the user's maximum heart rate for their age and evaluates their own heart rate against this max.
     * @return whether the user's heart rate was of their recommended maximum.
     */
    private boolean tachyRisk() {
        if (maxHeartRate > (220 - user.getAge())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculates the user's minimum heart rate for their age bracket and evaluates their own heart rate against this minimum.
     * @return whether the user's heart rate was under the minimum.
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
     * Sets the URL to search for the specific health warning depending on the type.
     * @return the web search URL of the warning.
     */
    private String setUpURL() {
        String warningURL;
        if (type == WarningType.Tachy) {
            warningURL = "https://www.google.com/search?q=tachycardia&ie=utf-8";
        } else {
            warningURL = "https://www.google.com/search?q=bradycardia&ie=utf-8";
        }
        return warningURL;
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


    public LocalDate getWarningDate() {
        return warningDate;
    }

    public WarningType getType() {
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

    public Activity getActivity() {
        return activity;
    }

    public Profile getOwner() {
        return user;
    }

    public void setOwner(Profile owner) {
        this.user = owner;
    }
}
