package seng202.team4.model;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


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
    private List<Activity> activityList;
    private List<Goal> goalList;

    /**
     *Constructor for profile class taking in the date of birth in string format
     * @param firstName is the first name of the user in string format
     * @param lastName is the last name of the user in string format
     * @param stringDate is the date of birth in string format
     * @param weight is the weight of the user in double format
     * @param height is the height of the user in double format
     */
    public Profile(String firstName, String lastName, String stringDate, double weight, double height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = LocalDate.parse(stringDate);
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

    /**
     * Calculates the user's bmi based on their current information
     * @return a double as the user's bmi
     */
    public double calculate_bmi() {
        return (this.weight/(this.height * this.height));
    }

    public int calculateAge() {
        return ((LocalDate.now()).getYear() - dateOfBirth.getYear());
    }

    /**
     * Checks that the given name is valid for a profile name.
     *
     * @param name The name to check.
     * @return true if the name is valid, false otherwise.
     */
    public static boolean isValidName(String name) {
        return (name.length() >= MIN_NAME_SIZE && name.length() <= MAX_NAME_SIZE);
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

}
