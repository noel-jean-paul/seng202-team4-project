package seng202.team4.model.data.enums;

public enum WarningType {
    Brady, Tachy, Cardiovascular;

    @Override
    public String toString() {
        switch(this) {
            case Brady: return "Bradycardia";
            case Tachy: return "Tachycardia";
            case Cardiovascular: return "Cardiovascular Mortality";
            default: return null;
        }
    }
}
