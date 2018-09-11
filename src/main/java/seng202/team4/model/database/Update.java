package seng202.team4.model.database;

public class Update {
    /** Class for storing
     *
     */
    private String attribute;   // name of the field of the object to be updated
    private String value;       // new value for the field

    public Update(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
