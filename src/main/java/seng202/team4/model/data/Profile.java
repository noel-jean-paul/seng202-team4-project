package seng202.team4.model.data;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


public class Profile {
    /**
     * A class profile which holds the attributes for each user
     */
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
    public double calculate_bmi() {
        return (this.weight/(this.height * this.height));
    }

    public int calculateAge() {
        return ((LocalDate.now()).getYear() - dateOfBirth.getYear());
    }
}
