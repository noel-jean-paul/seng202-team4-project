package seng202.team4.model.data.keys;

import java.util.Objects;

/**
 * Class for storing key for a profile for communication about profiles using their
 * key instead of the object itself between the database classes and the front end.
 */
public class ProfileKey implements Comparable<ProfileKey> {
    private String firstName;
    private String lastName;

    /**
     * Creates a new ProfileKey.
     *
     * @param firstName The first name component of the profile key.
     * @param lastName The last name component of the profile key.
     */
    public ProfileKey(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Checks whether this ProfileKey is equal to another.
     *
     * @param o The other ProfileKey to compare with.
     * @return true if the two ProfileKeys are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileKey that = (ProfileKey) o;
        return Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName());
    }

    /**
     * Generates a hash code for the ProfileKey.
     * @return The hash code fo the ProfileKey.
     */
    @Override
    public int hashCode() {

        return Objects.hash(getFirstName(), getLastName());
    }


    /** Compare a ProfileKey to another alphabetically. Primarily on firstName and on lastName to resolve ties.
     * Consistent with equals as defined by Comparable
     *
     * @param o the ProfileKey to compare to
     * @return a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(ProfileKey o) {
        int firstNameCompare;
        int lastNameCompare;
        if ((firstNameCompare = this.getFirstName().compareTo(o.getFirstName())) != 0) {
            return firstNameCompare * -1;
        } else if ((lastNameCompare = this.getLastName().compareTo(o.getLastName())) != 0) {
            return lastNameCompare * -1;
        } else {
            return 0;
        }
    }

    /**
     * Gets the first name component of the ProfileKey.
     * @return the first name component of the ProfileKey.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name component of the ProfileKey.
     * @return the last name component of the ProfileKey.
     */
    public String getLastName() {
        return lastName;
    }
}

