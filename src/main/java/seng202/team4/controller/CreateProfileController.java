package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import seng202.team4.model.Profile;

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
        System.out.println("First Name: " + firstNameField.getText());
        System.out.println("Last Name: " + lastNameField.getText());
        System.out.println("Date of Birth: " + dayField.getText() + '/' + monthField.getText() + '/' + yearField.getText());
        System.out.println("Weight: " + weightField.getText());
        System.out.println("Height: " + heightField.getText());

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        if (firstName.length() < 2 || firstName.length() > 20) {    //Test of error checking.
            errorText.setText("First name must be between 2 and 20 characters.");
        }

        else {

            String dateString = String.format("%s-%s-%s", yearField.getText(), monthField.getText(), dayField.getText());

            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());

            Profile profile = new Profile(firstName, lastName, dateString, weight, height);

            applicationStateManager.setCurrentProfile(profile);
            System.out.println("profile Created!");
            applicationStateManager.switchToScreen("MainScreen");
        }

    }
}
