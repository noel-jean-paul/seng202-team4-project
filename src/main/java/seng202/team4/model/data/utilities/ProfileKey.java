package seng202.team4.model.data.utilities;

/**
 * Class for storing key for a profile for communication about profiles using their
 * key instead of the object itself between the database classes and the front end.
 */
public class ProfileKey {
    private String firstName;
    private String lastName;

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
