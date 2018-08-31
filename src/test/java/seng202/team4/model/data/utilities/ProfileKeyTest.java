package seng202.team4.model.data.utilities;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProfileKeyTest {
    private static ProfileKey key1;
    private static ProfileKey key2;


    @Test
    public void compareTo_differentFirstName_checkComesBefore() {
        key1 = new ProfileKey("Alfred", "BatKeeper");   // Lower alphabetic first name
        key2 = new ProfileKey("Zeus", "A god");

        assert key1.compareTo(key2) < 0;
    }

    @Test
    public void compareTo_differentFirstName_checkComesAfter() {
        key1 = new ProfileKey("Alfred", "BatKeeper");   // Lower alphabetic first name
        key2 = new ProfileKey("Zeus", "A god");

        assert key2.compareTo(key1) > 0;
    }

    @Test
    public void compareTo_sametFirstName_differentLastName_checkComesBefore() {
        key1 = new ProfileKey("Alfred", "BatKeeper");   // Earlier aphabetic last name
        key2 = new ProfileKey("Alfred", "Son of Zeus");

        assert key1.compareTo(key2) < 0;
    }

    @Test
    public void compareTo_sametFirstName_differentLastName_checkComesAfter() {
        key1 = new ProfileKey("Alfred", "BatKeeper");   // Earlier aphabetic last name
        key2 = new ProfileKey("Alfred", "Son of Zeus");

        assert key2.compareTo(key1) > 0;
    }

    @Test
    public void compareTo_sametFirstName_sameLastName() {
        key1 = new ProfileKey("Alfred", "Son of Zeus");   // Same names
        key2 = new ProfileKey("Alfred", "Son of Zeus");

        assert key1.compareTo(key2) == 0;
    }

    @Test
    public void compareTo_sametFirstName_sameLastName_checkReverse() {
        key1 = new ProfileKey("Alfred", "Son of Zeus");   // Same names
        key2 = new ProfileKey("Alfred", "Son of Zeus");

        assert key2.compareTo(key1) == 0;
    }
}