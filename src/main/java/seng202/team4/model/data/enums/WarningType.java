package seng202.team4.model.data.enums;

/** Enum values for the different health warning types */
public enum WarningType {
    Brady, Tachy;

    @Override
    /**
     * New toString method for when the string Representation of the warning type is needed.
     * @return the string representation of the enum value.
     */
    public String toString() {
        switch(this) {
            case Brady: return "Bradycardia";
            case Tachy: return "Tachycardia";
            default: return null;
        }
    }
}
