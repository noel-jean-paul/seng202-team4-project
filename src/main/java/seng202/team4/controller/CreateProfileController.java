package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import seng202.team4.model.data.Profile;
import seng202.team4.model.database.DataStorer;

import java.time.LocalDate;

/**
 * Controller for the create profile screen.
 */
public class CreateProfileController extends Controller {

    /** TextField for the first name. */
    @FXML
    private TextField firstNameField;

    /** TextField for the last name. */
    @FXML
    private TextField lastNameField;

    /** TextField for the day of the date of birth. */
    @FXML
    private TextField dayField;

    /** TextField for the month of the date of birth. */
    @FXML
    private TextField monthField;

    /** TextField for the year of the date of birth. */
    @FXML
    private TextField yearField;

    /** TextField for the weight. */
    @FXML
    private TextField weightField;

    /** TextField for the height. */
    @FXML
    private TextField heightField;

    /** The Text that displays an error message to the user. */
    @FXML
    private Text errorText;


    /** Creates a new CreateProfileController with the given ApplicationStateManager. */
    public CreateProfileController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }


    /**
     * Action performed when the user clicks the create profile button.
     * Creates a new profile with the provided information in the TextFields.
     * If any of this information is incorrect then the user is displayed an
     * error message. Otherwise the new profile is stored in the database and
     * the application changes to the main screen.
     *
     */
    public void createProfile() {
        // Get string values from the text fields.
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String dateString = String.format("%4s-%2s-%2s", yearField.getText(), monthField.getText(), dayField.getText()).replace(' ', '0');

        // Try to parse the date string to check that it is in a valid format.
        LocalDate dateOfBirth = null;
        boolean isValidDateFormat = false;
        try {
            dateOfBirth = LocalDate.parse(dateString);
            isValidDateFormat = true;
        } catch (Exception e) {
            isValidDateFormat = false;
        }

        // Try to parse the weight string to check that it is in a valid format for a double.
        double weight = 0.0;
        boolean isValidWeightDoubleString = false;
        try {
            weight = Double.parseDouble(weightField.getText());
            isValidWeightDoubleString = true;
        } catch (Exception e) {
            isValidWeightDoubleString = false;
        }

        // Try to parse the height string to check that it is in a valid format for a double.
        double height = 0.0;
        boolean isValidHeightDoubleString = false;
        try {
            height = Double.parseDouble(heightField.getText());
            isValidHeightDoubleString = true;
        } catch (Exception e) {
            isValidHeightDoubleString = false;
        }


        // Check that the given values are valid. If they are not, the user is displayed an appropriate error message.
        if (!Profile.isValidName(firstName)) {
            errorText.setText(String.format("First name must be between %s and %s characters.", Profile.MIN_NAME_SIZE, Profile.MAX_NAME_SIZE));
        } else if (!Profile.isUniqueName(firstName, lastName)) {
            errorText.setText(String.format("User '%s %s' already exists.", firstName, lastName));
        } else if (!Profile.isValidName(lastName)){
            errorText.setText(String.format("Last name must be between %s and %s characters.", Profile.MIN_NAME_SIZE, Profile.MAX_NAME_SIZE));
        } else if (!isValidDateFormat) {
            errorText.setText("Date should be in the form dd/mm/yyyy.");
        } else if (!Profile.isValidDateOfBirth(dateOfBirth)) {
            errorText.setText(String.format("Date should be between %s and %s.", Profile.MIN_DOB, LocalDate.now()));
        } else if (!isValidWeightDoubleString || !Profile.isValidWeight(weight)) {
            errorText.setText(String.format("Weight must be a number between %s and %s", 0, Profile.MAX_WEIGHT));
        } else if (!isValidHeightDoubleString || !Profile.isValidHeight(height)) {
            errorText.setText(String.format("Height must be a number between %s and %s", 0, Profile.MAX_HEIGHT));
        } else {
            //Creates a new profile with the values provided by the user.
            Profile profile = new Profile(firstName, lastName, dateString, weight, height);
            applicationStateManager.setCurrentProfile(profile);    //Sets the current profile to the new profile.
            System.out.println("profile Created!");
            try {
                DataStorer.insertProfile(profile);
                applicationStateManager.switchToScreen("MainScreen");   //Changes to main screen.
            } catch (java.sql.SQLException e) {
                applicationStateManager.displayErrorMessage("An error occurred storing the profile from the database.", e.getMessage());
                System.out.println("Error storing new profile in the data base.");
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void cancel() {
        applicationStateManager.switchToScreen("LoginScreen");
        reset();

    }

    public void reset() {
        firstNameField.setText("");
        lastNameField.setText("");
        dayField.setText("");
        monthField.setText("");
        yearField.setText("");
        weightField.setText("");
        heightField.setText("");
    }
}
