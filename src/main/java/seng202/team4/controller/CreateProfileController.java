package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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

    /** Creates a new CreateProfileController with the given ScreenStateManager. */
    public CreateProfileController(ScreenStateManager screenStateManager) {
        super(screenStateManager);
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
    }
}
