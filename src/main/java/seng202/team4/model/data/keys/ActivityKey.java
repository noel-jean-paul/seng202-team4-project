package seng202.team4.model.data.keys;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class that encapsulates the key object used to store Activities in the database.
 */
public class ActivityKey {
    private String name;    // name of the activity this object describes
    private LocalDate date; // date of the activity this object describes

    /** Constuctor for Activity key
     *
     * @param name the name to be set as name
     * @param date the date to set as the date
     */
    public ActivityKey(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    /** Check if this ActivityDate is equal to another object
     *
     * @param o the object to compare to
     * @return true if the objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityKey that = (ActivityKey) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDate(), that.getDate());
    }

    /** Compute and return the hash code of this object
     *
     * @return the hash code as an int
     */
    @Override
    public int hashCode() {

        return Objects.hash(getName(), getDate());
    }

    /** Getter for name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /** Setter for name
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for date
     *
     * @return the date of this ActivityKey
     */
    public LocalDate getDate() {
        return date;
    }

    /** Setter for date
     *
     * @param date the new date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
