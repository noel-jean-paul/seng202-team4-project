package seng202.team4.model.data.utilities;

import java.util.Objects;

/**
 * Class for storing key for a profile for communication about profiles using their
 * key instead of the object itself between the database classes and the front end.
 */
public class ProfileKey implements Comparable<ProfileKey> {
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
    public int compareTo(ProfileKey o) {
        int c;
        int d;
        if (this == o) {    // Same object
            return 0;
        } else if ((c = this.getFirstName().compareTo(o.getFirstName())) != 0) {
            return c;
        } else if ((d = this.getLastName().compareTo(o.getLastName())) != 0) {
            return d;
        } else {
            return 0;
        }
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

    public static void main(String[] args) {
        ProfileKey key1 = new ProfileKey("b", "c");
        ProfileKey key2 = new ProfileKey("a", "a");
        System.out.println(key1.compareTo(key2));
    }
}

