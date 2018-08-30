package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import seng202.team4.model.Profile;

import java.time.LocalDate;

public class CreateProfileController extends Controller {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField dayField;

    @FXML
    private TextField monthField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField heightField;

    @FXML
    private Text errorText;

    /** Creates a new CreateProfileController with the given ApplicationStateManager. */
    public CreateProfileController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /**
     * Action performed when the user clicks the create profile button.
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
            applicationStateManager.switchToScreen("MainScreen");   //Changes to main screen.
        }

    }
}
