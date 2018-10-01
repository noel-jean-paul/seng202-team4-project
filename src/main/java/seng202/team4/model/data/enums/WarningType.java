package seng202.team4.model.data.enums;

public enum WarningType {
    Brady, Tachy;

    @Override
    public String toString() {
        switch(this) {
            case Brady: return "Bradycardia";
            case Tachy: return "Tachycardia";
            default: return null;
        }
    }
}
