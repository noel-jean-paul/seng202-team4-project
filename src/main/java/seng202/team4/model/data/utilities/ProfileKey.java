package seng202.team4.model.data.utilities;

import java.util.Objects;

/**
 * Class for storing key for a profile for communication about profiles using their
 * key instead of the object itself between the database classes and the front end.
 */
public class ProfileKey implements Comparable {
    private String firstName;
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileKey that = (ProfileKey) o;
        return Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFirstName(), getLastName());
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public ProfileKey(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
