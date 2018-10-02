package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class HealthWarningDetectedPopup extends Controller {

    @FXML
    private GridPane popupWarningDetected;

    @FXML
    private Button cancelButton;

    @FXML
    void closePopup() {
        applicationStateManager.closePopUP(popupWarningDetected);
    }

    public HealthWarningDetectedPopup(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

}
