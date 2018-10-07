package seng202.team4.model.utilities;

import seng202.team4.model.data.Activity;
import seng202.team4.model.data.Profile;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.data.enums.WarningType;

import java.time.LocalDate;
import java.util.Objects;

public class HealthWarning implements Comparable<HealthWarning> {
    /** The average heart rate of the activity */
    private int avgHeartRate;

    /** The minimum heart rate of the activity */
    private int minHeartRate;

    /** The maximum heart rate of the activity */
    private int maxHeartRate;

    /** The description of the health warning */
    private String description;

    /** The URL for the health warning to be used by the web view if selected */
    private String url;

    /** The activity the warning is associated with */
    private Activity activity;

    /** The user's profile */
    private Profile user;

    /** The type of the warning */
    private WarningType type;

    /** The date which the warning was created */
    private LocalDate warningDate;

    /** Whether or not the warning indicates a genuine health risk - In other words, if the warning is valid */
    private boolean healthRisk;

    /** The constructor for the health warning */
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
    }

    /** Compare to another warning based on date
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

    /**
     * Checks to see if two warnings are equal, that is, if they hold the exact same data.
     * @param o the warning that is being compared against this one.
     * @return True if the warnings are equal, False if they are distinct.
     */
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

    /**
     * Generates the hash code for the health warning based on its values.
     * @return the hash code allocated to the warning.
     */
    @Override
    public int hashCode() {

        return Objects.hash(getAvgHeartRate(), getMinHeartRate(), getMaxHeartRate(), getDescription(), getUrl(),
                getType(), getTypeString(), getWarningDate(), isHealthRisk());
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

    /**
     * Getter for the healthRisk attribute.
     * @return a boolean indicating if the warning is a health risk or not
     */
    public boolean isHealthRisk() {
        return healthRisk;
    }

    /**
     * Getter for the average heart rate of the activity.
     * @return the average speed of the user.
     */
    public int getAvgHeartRate() {
        return avgHeartRate;
    }

    /**
     * Getter for the string representation of the average heart rate of the user.
     * @return the string representation of the average heart rate.
     */
    public String getAvgHeartRateString() {
        return Integer.toString(avgHeartRate);
    }

    /**
     * Getter method for the minimum heart rate achieved over the activity.
     * @return the minimum heart rate of the user.
     */
    public int getMinHeartRate() {
        return avgHeartRate;
    }

    /**
     * Getter method for the string representation of the minimum heart rate achieved over the activity
     * @return the string representation of the minimum heart rate.
     */
    public String getMinHeartRateString() {
        return Integer.toString(minHeartRate);
    }

    /**
     * Getter for the maximum heart rate over the activity.
     * @return the maximum heart rate achieved over the activity.
     */
    public int getMaxHeartRate() {
        return maxHeartRate;
    }

    /**
     * Getter for the textual representation of the maximum heart rate over the activity.
     * @return the textual representation of the maximum heart rate.
     */
    public String getMaxHeartRateString() {
        return Integer.toString(maxHeartRate);
    }


    /**
     * Gets the date of when the warning was created.
     * @return the date of creation.
     */
    public LocalDate getWarningDate() {
        return warningDate;
    }

    /**
     * Gets the type of the health warning - Tachycardia or Bradycardia
     * @return the warning type.
     */
    public WarningType getType() {
        return type;
    }

    /**
     * Gets the textual representation of the health warning's type.
     * @return the textual representation of the warning type.
     */
    public String getTypeString() {
        return type.toString();
    }

    /**
     * Gets the description of the health warning.
     * @return the warning description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the URL to be used by the web view to search for the health warning.
     * @return the warning URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the activity which the warning was created from.
     * @return the activity of issue.
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Gets the owner which holds the health warnings.
     * @return the warning's owner profile.
     */
    public Profile getOwner() {
        return user;
    }

    /**
     * Gets the owner of the health warning to the current user profile.
     * @param owner the owner to be set.
     */
    public void setOwner(Profile owner) {
        this.user = owner;
    }
}
