package seng202.team4.Model;
import seng202.team4.model.data.Goal;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
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
        this.goalList = new ArrayList<>();
        this.activityList = new ArrayList<>();
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
        this.goalList = new ArrayList<>();
        this.activityList = new ArrayList<>();
    }

    /**
     * Calculates the user's bmi based on their current information
     * @return the user's bmi as a double
     */
    public double calculate_bmi() {
        return (this.weight/(this.height * this.height));
    }

    /**
     * Calculates the age of the user based on their DoB and current Date
     * @return the user's age as an integer
     */
    public int calculateAge() {
        return ((LocalDate.now()).getYear() - dateOfBirth.getYear());
    }
}
