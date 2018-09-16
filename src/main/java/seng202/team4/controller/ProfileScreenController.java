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

    private boolean isfirstNameEdited = false;

    public ProfileScreenController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    public void done() {
        applicationStateManager.switchToScreen("MainScreen");
    }

    @FXML void editFirstName() {
        if (!isfirstNameEdited) {
            firstNameVbox.getChildren().setAll(new TextField(applicationStateManager.getCurrentProfile().getFirstName()));
        }

    }

    public void updateInformation() {
        firstNameText.setText(applicationStateManager.getCurrentProfile().getFirstName());
        lastNameText.setText(applicationStateManager.getCurrentProfile().getLastName());
        heightText.setText(Double.toString(applicationStateManager.getCurrentProfile().getHeight()));
        weightText.setText(Double.toString(applicationStateManager.getCurrentProfile().getWeight()));
    }


}
