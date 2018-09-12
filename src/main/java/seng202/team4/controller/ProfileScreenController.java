package seng202.team4.controller;

import javafx.fxml.FXML;

public class ProfileScreenController extends Controller {
    public ProfileScreenController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    public void done() {
        applicationStateManager.switchToScreen("MainScreen");
    }
}
