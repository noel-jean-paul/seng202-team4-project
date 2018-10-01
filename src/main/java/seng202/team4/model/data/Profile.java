package seng202.team4.model.data;

import seng202.team4.GuiUtilities;
import seng202.team4.model.data.enums.ProfileFields;
import seng202.team4.model.database.DataLoader;
import seng202.team4.model.database.DataStorer;
import seng202.team4.model.database.DataUpdater;
import seng202.team4.model.utilities.HealthWarning;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;


public class Profile {
    /**
     * A class profile which holds the attributes for each user
     */

    public static final int MAX_NAME_SIZE = 20;
    public static final int MIN_NAME_SIZE = 2;
    public static final double MAX_WEIGHT = 250;
    public static final double MAX_HEIGHT = 3.0;
    public static final double MIN_WEIGHT = 10;
    public static final double MIN_HEIGHT = 0.5;
    public static final String DEFAULT_URL = "/images/default-profile-icon.png";

    public static final LocalDate MIN_DOB = LocalDate.parse("1900-01-01");

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private double weight;
    private double height;
    private String pictureURL;
    private List<Activity> activityList;    // sorted collection - use addActivity to update
    private List<Goal> currentGoals;    // sorted collection - use addCurrentGoal to update
    private List<Goal> pastGoals;
    private List<HealthWarning> warningList;    // sorted collection - use addWarning to update

    /**
     *Constructor for profile class taking in the date of birth in string format. Sets pictureURL to the default
     * @param firstName is the first name of the user in string format
     * @param lastName is the last name of the user in string format
     * @param dateOfBirth is the date of birth in string format
     * @param weight is the weight of the user in double format
     * @param height is the height of the user in double format
     */
    public Profile(String firstName, String lastName, String dateOfBirth, double weight, double height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = LocalDate.parse(dateOfBirth);
        this.weight = weight;
        this.height = height;
        this.currentGoals = new ArrayList<>();
        this.pastGoals = new ArrayList<>();
        this.activityList = new ArrayList<>();
        this.warningList = new ArrayList<>();
        this.pictureURL = Profile.DEFAULT_URL;
    }

    /**
     *Constructor for profile class taking in the date of birth in string format and the picture url
     * @param firstName is the first name of the user in string format
     * @param lastName is the last name of the user in string format
     * @param dateOfBirth is the date of birth in string format
     * @param weight is the weight of the user in double format
     * @param height is the height of the user in double format
     */
    public Profile(String firstName, String lastName, String dateOfBirth, double weight, double height,
                   String pictureURL) {
        this(firstName, lastName, dateOfBirth, weight, height);
        this.pictureURL = pictureURL;

    }

    /**
     *Constructor for profile class taking in a year, month and day instead of a string
     * @param firstName is the first name of the user in string format
     * @param lastName is the last name of the user in string format
     * @param year is the year of birth in int format
     * @param month is the month of birth in month class format
     * @param day is the day of birth in int format
     * @param weight is the weight of the user in double format
     * @param height is the height of the user in double format
     */
    public Profile(String firstName, String lastName, int year, Month month, int day, double weight, double height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = LocalDate.of(year, month, day);
        this.weight = weight;
        this.height = height;
        currentGoals = new ArrayList<>();
        activityList = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Double.compare(profile.getWeight(), getWeight()) == 0 &&
                Double.compare(profile.getHeight(), getHeight()) == 0 &&
                Objects.equals(getFirstName(), profile.getFirstName()) &&
                Objects.equals(getLastName(), profile.getLastName()) &&
                Objects.equals(getDateOfBirth(), profile.getDateOfBirth()) &&
                Objects.equals(getPictureURL(), profile.getPictureURL()) &&
                Objects.equals(getActivityList(), profile.getActivityList()) &&
                Objects.equals(getCurrentGoals(), profile.getCurrentGoals()) &&
                Objects.equals(getWarningList(), profile.getWarningList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getDateOfBirth(), getWeight(), getHeight(), getActivityList(), getCurrentGoals());
    }

    public String getFirstName() {
        return firstName;
    }

    /** Set and store in database */
    public void setFirstName(String firstName) throws SQLException {
        DataUpdater.updateProfile(this, ProfileFields.firstName.toString(), firstName);
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /** Set and store in database */
    public void setLastName(String lastName) throws SQLException {
        DataUpdater.updateProfile(this, ProfileFields.lastName.toString(), lastName);
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /** Set and store in database */
    public void setDateOfBirth(String dateOfBirth) throws SQLException {
        this.dateOfBirth = LocalDate.parse(dateOfBirth);
        DataUpdater.updateProfile(this, ProfileFields.dateOfBirth.toString(), dateOfBirth);
    }

    public double getWeight() {
        return weight;
    }

    /** Set and store in database */
    public void setWeight(double weight) throws SQLException {
        this.weight = weight;
        DataUpdater.updateProfile(this, ProfileFields.weight.toString(), Double.toString(weight));
    }

    public double getHeight() {
        return height;
    }

    /** Set and store in database */
    public void setHeight(double height) throws SQLException {
        this.height = height;
        DataUpdater.updateProfile(this, ProfileFields.height.toString(), Double.toString(height));
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public List<Goal> getCurrentGoals() {
        return currentGoals;
    }

    public List<Goal> getPastGoals() {
        return pastGoals;
    }

    /**
     * Calculates the user's bmi based on their current information
     * @return a double as the user's bmi
     */
    public double getBmi() {
        return (this.weight/(this.height * this.height));
    }

    public int getAge() {
        return ((LocalDate.now()).getYear() - dateOfBirth.getYear());
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) throws SQLException {
        this.pictureURL = pictureURL;
        DataUpdater.updateProfile(this, ProfileFields.pictureURL.toString(), pictureURL);
    }

    /**
     * Checks that the given name is valid for a profile name.
     *
     * @param name The name to check.
     * @return true if the name is valid, false otherwise.
     */
    public static boolean isValidName(String name) {
        return name.length() >= MIN_NAME_SIZE && name.length() <= MAX_NAME_SIZE;
    }

    /**
     * Checks that the given name is a unique profile name.
     *
     * @param firstName The first name of the name to check.
     * @param lastName The last name of the name to check.
     * @return true if the name is unique, false otherwise.
     */
    public static boolean isUniqueName(String firstName, String lastName) {
        boolean isUnique = true;
        try {
            List<ProfileKey> profileKeys = DataLoader.fetchAllProfileKeys();
            ProfileKey newProfileKey = new ProfileKey(firstName, lastName);

            for (ProfileKey profileKey: profileKeys) {
                if (profileKey.equals(newProfileKey)) {
                    isUnique = false;
                }
            }
        } catch (java.sql.SQLException e) {
            GuiUtilities.displayErrorMessage("Error loading profile keys from the data base.", "");
            e.printStackTrace();
            System.out.println("Error loading profile keys from the data base.");
            isUnique = false;
        }

        return isUnique;
    }

    /**
     * Checks that a weight value is valid for a profile.
     *
     * @param weight The weight value to be checked.
     * @return true if the weight is valid, false otherwise.
     */
    public static boolean isValidWeight(double weight) {
        return (weight >= MIN_WEIGHT && weight <= MAX_WEIGHT);
    }

    /**
     * Checks that a height value is valid for a profile.
     *
     * @param height the height value to be checked.
     * @return true if the height is valid, false otherwise.
     */
    public static boolean isValidHeight(double height) {
        return (height >= MIN_HEIGHT && height <= MAX_HEIGHT);
    }

    /**
     * Checks that a date is a valid date of birth.
     *
     * @param date the date to be checked.
     * @return true if the date is valid, false otherwise.
     */
    public static boolean isValidDateOfBirth(LocalDate date) {
        return (date.compareTo(MIN_DOB) > 0 && date.compareTo(LocalDate.now()) < 0);
    }

    /** Add an activity to the activity list in correct date position and insert it into the database.
     *
     * @param activity the activity to be added
     */
    public void addActivity(Activity activity) throws SQLException {
        activityList.add(activity);
        java.util.Collections.sort(activityList);   // Keep the lsit ordered
        // Set this as the activity owner
        activity.setOwner(this);

        DataStorer.insertActivity(activity, this);
    }

    /** Adds all activities of the specified collection to the profile activityList and sorts the activityList
     *  Intended for use by DataLoader
     *  WARNING: DOES NOT STORE IN THE DATABASE OR SET OWNER
     *
     * @param activities the collection to be added
     */
    public void addAllActivities(Collection<Activity> activities) {
        activityList.addAll(activities);
        java.util.Collections.sort(activityList);
    }

    /** Add a goal to the current goal list in order and insert it into the database
     *
     * @param goal the Goal to be added
     */
    public void addCurrentGoal(Goal goal) throws SQLException {
        currentGoals.add(goal);
        goal.setCurrent(true);  // In case the goal was moved back from past goals
        java.util.Collections.sort(currentGoals);
        // Set this as the activity owner
        goal.setOwner(this);

        DataStorer.insertGoal(goal, this);
    }

    /** Add a goal to the past goal list in order and insert it into the database
     *
     * @param goal the Goal to be added
     */
    public void addPastGoal(Goal goal) throws SQLException {
        addPastGoal(goal, true);
    }

    /** Add a goal to the past goal list in order and insert it into the database if insert is true
     *
     * @param goal the Goal to be added
     * @param insert the flag determining whether the goal should be inserted into the database or not
     */
    private void addPastGoal(Goal goal, boolean insert) throws SQLException {
        pastGoals.add(goal);
        goal.setCurrent(false); // The goal is no longer current if it is in the past goals
        java.util.Collections.sort(currentGoals);
        // Set this as the activity owner
        goal.setOwner(this);

        if (insert) {
            DataStorer.insertGoal(goal, this);
        }
    }

    /** Adds all goals of the specified collection to the currentGoals and sorts the currentGoals
     *  Intended for use by DataLoader only
     *  WARNING: DOES NOT STORE IN THE DATABASE OR SET OWNER
     *
     * @param goals the collection to be added
     */
    public void addAllCurrentGoals(Collection<Goal> goals) {
        currentGoals.addAll(goals);
        java.util.Collections.sort(currentGoals);
    }

    /** Adds all goals of the specified collection to the pastGoals and sorts the pastGoals
     *  Intended for use by DataLoader only
     *  WARNING: DOES NOT STORE IN THE DATABASE OR SET OWNER
     *
     * @param goals the collection to be added
     */
    public void addAllPastGoals(Collection<Goal> goals) {
        pastGoals.addAll(goals);
        java.util.Collections.sort(pastGoals);
    }



    /** Remove the activity from the activityList and the database
     *
     * @param activity the activity to be removed
     */
    public void removeActivity(Activity activity) throws SQLException {
        activityList.remove(activity);
        DataStorer.deleteActivities(new ArrayList<>(Collections.singletonList(activity)));
    }

    /** Remove the goal from the currentGoals and the database
     *
     * @param goal the goal to be removed
     */
    public void removeCurrentGoal(Goal goal) throws SQLException {
        removeCurrentGoal(goal, true);
    }

    /** Remove the goal from the currentGoals and from the database if the delete flag is true
     *
     * @param goal the goal to be removed
     */
    private void removeCurrentGoal(Goal goal, boolean delete) throws SQLException {
        currentGoals.remove(goal);
        if (delete) {
            DataStorer.deleteGoals(new ArrayList<>(Collections.singletonList(goal)));
        }
    }

//    /** Remove the goal from the currentGoals and the database
//     *
//     * @param goal the goal to be removed
//     */
//    public void removePastGoal(Goal goal) throws SQLException {
//        removePastGoal(goal, true);
//    }
//
//    /** Remove the goal from the currentGoals and from the database if the delete flag is true
//     *
//     * @param goal the goal to be removed
//     */
//    private void removePastGoal(Goal goal, boolean delete) throws SQLException {
//        pastGoals.remove(goal);
//        if (delete) {
//            DataStorer.deleteGoals(new ArrayList<>(Collections.singletonList(goal)));
//        }
//    }

    /**
     * Adds a warning to the user's list of warnings in order and store the warning in the database.
     * @param warning the warning to be added.
     */
    public void addWarning(HealthWarning warning) {
        warningList.add(warning);
        Collections.sort(warningList);
    }

    /** Adds all healthWarnings of the specified collection to the warningList and sorts the warningList
     *  Intended for use by DataLoader only
     *  WARNING: DOES NOT STORE IN THE DATABASE OR SET OWNER
     *
     * @param warnings the collection to be added
     */
    public void addAllWarnings(List<HealthWarning> warnings) {
        warningList.addAll(warnings);
        Collections.sort(warningList);
    }



    /**
     * Gets the user's warning history.
     * @return the list of warnings.
     */
    public List<HealthWarning> getWarningList() {
        return warningList;
    }

    /**
     * Used when the profile has been loaded. Goes through all the activities stored by the user and tells the activity to
     * check for any health warnings it may have.
     */
    public void findWarnings() {
        for (Activity activity : activityList) {
            activity.addWarnings(false);
        }
    }

    /* Move any goals in currentGoals which have expired into pastGoal
     * Should be called before updateGoalsForProgress */
    public void updateGoalsForExpiry() throws SQLException {
        // Iterate over a copy of the list as we are modifying the currentGoals as we iterate over them
        List<Goal> currentGoalsCopy = new ArrayList<>(currentGoals);
        for (Goal goal: currentGoalsCopy) {
            if (goal.getExpiryDate().isBefore(LocalDate.now())) {
                removeCurrentGoal(goal, false);
                addPastGoal(goal, false);
            }
        }
    }

    /** Update goal progress for current goals with the activities in the collection.
     *  Should be called after updateGoalsForExpiry.
     *  Assumes that each goal is one of distance, duration or calories goal
     *
     * @param activites the list of activities to update the goals
     */
    public void updateGoalsForProgress(Collection<Activity> activites) {
        for (Goal goal: currentGoals) {
            for (Activity activity: activites) {
                // Check the activity is in the correct date range and of the correct type - compare enums by the string
                if (activity.getDate().isAfter(goal.getCreationDate())
                        && (activity.getType().toString().equals(goal.getType().toString()))) {
                    if (goal.isDistanceGoal()) {
                        // Increment progress
                        goal.incrementProgress((activity.getDistance() / goal.getGoalDistance()) * 100);
                    }
                    else if (goal.isDurationGoal()) {
                        // Convert from long go double to allow for non-integer division
                        Double activityDuration = Double.valueOf(Long.toString(activity.getDuration().toMinutes()));
                        Double goalDuration = Double.valueOf(Long.toString(goal.getGoalDuration().toMinutes()));
                        // Increment progress
                        goal.incrementProgress((activityDuration / goalDuration) * 100);
                    }
                }
            }
        }
    }

}
