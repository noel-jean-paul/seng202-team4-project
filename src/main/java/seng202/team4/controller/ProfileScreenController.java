package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
    private VBox firstNameVbox;

    @FXML
    private VBox lastNameVbox;

    @FXML
    private VBox heightVbox;

    @FXML
    private VBox weightVbox;

    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private TextField heightTextField;
    private TextField weightTextField;

    private boolean isEditeding = false;

    public ProfileScreenController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    public void initialize() {
        firstNameTextField = new TextField();
        lastNameTextField = new TextField();
        heightTextField = new TextField();
        weightTextField = new TextField();
    }

    @FXML
    public void done() {
        applicationStateManager.switchToScreen("MainScreen");
    }

    @FXML void editProfile() {
        if (!isEditeding){
            firstNameTextField.setText(applicationStateManager.getCurrentProfile().getFirstName());
            lastNameTextField.setText(applicationStateManager.getCurrentProfile().getLastName());
            heightTextField.setText(Double.toString(applicationStateManager.getCurrentProfile().getHeight()));
            weightTextField.setText(Double.toString(applicationStateManager.getCurrentProfile().getWeight()));

            firstNameVbox.getChildren().setAll(firstNameTextField);
            lastNameVbox.getChildren().setAll(lastNameTextField);
            heightVbox.getChildren().setAll(heightTextField);
            weightVbox.getChildren().setAll(weightTextField);

            isEditeding = true;
        } else  {

            firstNameVbox.getChildren().setAll(firstNameText);
            lastNameVbox.getChildren().setAll(lastNameText);
            heightVbox.getChildren().setAll(heightText);
            weightVbox.getChildren().setAll(weightText);

            isEditeding = false;
        }

    }

    public void updateInformation() {
        firstNameText.setText(applicationStateManager.getCurrentProfile().getFirstName());
        lastNameText.setText(applicationStateManager.getCurrentProfile().getLastName());
        heightText.setText(Double.toString(applicationStateManager.getCurrentProfile().getHeight()));
        weightText.setText(Double.toString(applicationStateManager.getCurrentProfile().getWeight()));
    }


}
