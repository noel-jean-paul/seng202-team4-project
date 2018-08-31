package seng202.team4.model.data;
import seng202.team4.model.database.DataStorer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class Profile {
    /**
     * A class profile which holds the attributes for each user
     */

    public static final int MAX_NAME_SIZE = 20;
    public static final int MIN_NAME_SIZE = 2;
    public static final double MAX_WEIGHT = 250;
    public static final double MAX_HEIGHT = 3.0;
    public static final LocalDate MIN_DOB = LocalDate.parse("1900-01-01");

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private double weight;
    private double height;
    private List<Activity> activityList;    // sorted collection - use addActivity to update
    private List<Goal> goalList;    // sorted collection - use addGoal to update

    /**
     *Constructor for profile class taking in the date of birth in string format
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
        goalList = new ArrayList<>();
        activityList = new ArrayList<>();
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
        goalList = new ArrayList<>();
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
                Objects.equals(getDateOfBirth(), profile.getDateOfBirth());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFirstName(), getLastName(), getDateOfBirth(), getWeight(), getHeight());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public List<Goal> getGoalList() {
        return goalList;
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

    /**
     * Checks that the given name is valid for a profile name.
     *
     * @param name The name to check.
     * @return true if the name is valid, false otherwise.
     */
    public static boolean isValidName(String name) {
        return !(name.length() < MIN_NAME_SIZE || name.length() > MAX_NAME_SIZE);
    }

    /**
     * Checks that a weight value is valid for a profile.
     *
     * @param weight The weight value to be checked.
     * @return true if the weight is valid, false otherwise.
     */
    public static boolean isValidWeight(double weight) {
        return (weight > 0 && weight <= MAX_WEIGHT);
    }

    /**
     * Checks that a height value is valid for a profile.
     *
     * @param height the height value to be checked.
     * @return true if the height is valid, false otherwise.
     */
    public static boolean isValidHeight(double height) {
        return (height > 0 && height <= MAX_HEIGHT);
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

    /** Add an activity to the activity list in correct date position and save the activity in the database.
     *
     * @param activity the activity to be added
     */
    public void addActivity(Activity activity) throws SQLException {
        activityList.add(activity);
        java.util.Collections.sort(activityList);
        DataStorer.insertActivity(activity, this);
    }

    /** Adds all activities of the specified collection to the profile activityList and sorts the activityList
     *  Intended for use by DataLoader
     *
     * @param activities the collection to be added
     */
    public void addAllActivities(Collection<Activity> activities) {
        activityList.addAll(activities);
        java.util.Collections.sort(activityList);
    }

    /** Adds all goals of the specified collection to the goalList and sorts the goalList
     *  Intended for use by DataLoader
     *
     * @param goals the collection to be added
     */
    public void addAllGoals(Collection<Goal> goals) {
        goalList.addAll(goals);
        java.util.Collections.sort(goalList);
    }
}
