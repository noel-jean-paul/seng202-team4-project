package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seng202.team4.model.data.Profile;
import seng202.team4.model.database.DataStorer;

import java.sql.SQLException;
import java.time.LocalDate;

public class ProfileScreenController extends Controller {

    @FXML
    private Text firstNameText;

    @FXML
    private Text heightText;

    @FXML
    private Text lastNameText;

    @FXML
    private Text weightText;

    @FXML
    private Text yearText;

    @FXML
    private Text monthText;

    @FXML
    private Text dayText;

    @FXML
    private Text errorText;

    @FXML
    private VBox firstNameVbox;

    @FXML
    private VBox lastNameVbox;

    @FXML
    private VBox heightVbox;

    @FXML
    private VBox weightVbox;

    @FXML
    private VBox dayVbox;

    @FXML
    private VBox monthVbox;

    @FXML
    private VBox yearVbox;

    @FXML
    private Button editCancelButton;

    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private TextField heightTextField;
    private TextField weightTextField;
    private TextField dayTextField;
    private TextField monthTextField;
    private TextField yearTextField;

    private boolean isEditing = false;

    public ProfileScreenController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    public void initialize() {
        firstNameTextField = new TextField();
        lastNameTextField = new TextField();
        heightTextField = new TextField();
        weightTextField = new TextField();
        dayTextField = new TextField();
        monthTextField = new TextField();
        yearTextField = new TextField();

        firstNameTextField.setAlignment(Pos.CENTER);
        lastNameTextField.setAlignment(Pos.CENTER);
        heightTextField.setAlignment(Pos.CENTER);
        weightTextField.setAlignment(Pos.CENTER);
        dayTextField.setAlignment(Pos.CENTER);
        monthTextField.setAlignment(Pos.CENTER);
        yearTextField.setAlignment(Pos.CENTER);
    }

    @FXML
    public void done() {
        if (isEditing) {
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String weightString = weightTextField.getText();
            String heightString = heightTextField.getText();

            String dayString = dayTextField.getText();
            String monthString = monthTextField.getText();
            String yearString = yearTextField.getText();

            String dateString = String.format("%4s-%2s-%2s", yearString, monthString, dayString).replace(' ', '0');

            // Try to parse the date string to check that it is in a valid format.
            LocalDate dateOfBirth = null;
            boolean isValidDateFormat = false;
            try {
                dateOfBirth = LocalDate.parse(dateString);
                isValidDateFormat = true;
            } catch (Exception e) {
                isValidDateFormat = false;
            }

            double weight = 0.0;
            boolean isValidWeightDoubleString = false;
            try {
                weight = Double.parseDouble(weightString);
                isValidWeightDoubleString = true;
            } catch (Exception e) {
                isValidWeightDoubleString = false;
            }

            // Try to parse the height string to check that it is in a valid format for a double.
            double height = 0.0;
            boolean isValidHeightDoubleString = false;
            try {
                height = Double.parseDouble(heightString);
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
                Profile profile = applicationStateManager.getCurrentProfile();

                try {
                    profile.setFirstName(firstName);
                    profile.setLastName(lastName);
                    profile.setWeight(weight);
                    profile.setHeight(height);
                    profile.setDateOfBirth(dateString);

                    updateInformation();
                    changeValuesToText();
                    editCancelButton.setText("Edit");
                    isEditing = false;
                    errorText.setText("");
                } catch (java.sql.SQLException e) {
                    applicationStateManager.displayErrorMessage("Error encountered editing profile.", e.getMessage());
                }
            }

        } else {
            applicationStateManager.switchToScreen("MainScreen");
        }
    }

    @FXML void editProfile() {
        if (!isEditing){
            firstNameTextField.setText(applicationStateManager.getCurrentProfile().getFirstName());
            lastNameTextField.setText(applicationStateManager.getCurrentProfile().getLastName());
            heightTextField.setText(Double.toString(applicationStateManager.getCurrentProfile().getHeight()));
            weightTextField.setText(Double.toString(applicationStateManager.getCurrentProfile().getWeight()));
            dayTextField.setText(Integer.toString(applicationStateManager.getCurrentProfile().getDateOfBirth().getDayOfMonth()));
            monthTextField.setText(Integer.toString(applicationStateManager.getCurrentProfile().getDateOfBirth().getMonthValue()));
            yearTextField.setText(Integer.toString(applicationStateManager.getCurrentProfile().getDateOfBirth().getYear()));

            firstNameVbox.getChildren().setAll(firstNameTextField);
            lastNameVbox.getChildren().setAll(lastNameTextField);
            heightVbox.getChildren().setAll(heightTextField);
            weightVbox.getChildren().setAll(weightTextField);
            dayVbox.getChildren().setAll(dayTextField);
            monthVbox.getChildren().setAll(monthTextField);
            yearVbox.getChildren().setAll(yearTextField);

            editCancelButton.setText("Cancel");
            isEditing = true;
        } else  {
            editCancelButton.setText("Edit");
            isEditing = false;
            updateInformation();
            changeValuesToText();
            errorText.setText("");
        }

    }

    public void updateInformation() {
        firstNameText.setText(applicationStateManager.getCurrentProfile().getFirstName());
        lastNameText.setText(applicationStateManager.getCurrentProfile().getLastName());
        heightText.setText(Double.toString(applicationStateManager.getCurrentProfile().getHeight()));
        weightText.setText(Double.toString(applicationStateManager.getCurrentProfile().getWeight()));
        dayText.setText(Integer.toString(applicationStateManager.getCurrentProfile().getDateOfBirth().getDayOfMonth()));
        monthText.setText(Integer.toString(applicationStateManager.getCurrentProfile().getDateOfBirth().getMonthValue()));
        yearText.setText(Integer.toString(applicationStateManager.getCurrentProfile().getDateOfBirth().getYear()));
    }

    private void changeValuesToText() {
        firstNameVbox.getChildren().setAll(firstNameText);
        lastNameVbox.getChildren().setAll(lastNameText);
        heightVbox.getChildren().setAll(heightText);
        weightVbox.getChildren().setAll(weightText);
        dayVbox.getChildren().setAll(dayText);
        monthVbox.getChildren().setAll(monthText);
        yearVbox.getChildren().setAll(yearText);
    }

    public void deleteProfile() {

        try {
            DataStorer.deleteProfile(applicationStateManager.getCurrentProfile());
            ((LoginController) applicationStateManager.getScreenController("LoginScreen")).updateProfileList();
            applicationStateManager.switchToScreen("LoginScreen");
        } catch (SQLException e) {
            applicationStateManager.displayErrorMessage("Failed to delete profile.", "");
        }
    }


}
