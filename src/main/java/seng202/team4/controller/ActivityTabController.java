package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import seng202.team4.Utilities;

public class ActivityTabController extends Controller {

    public ActivityTabController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }


    @FXML
    public void addActivities() {
        Pane popUp = Utilities.loadPane("ActivityImportTypePrompt.fxml", new ActivityImportTypePromptController(applicationStateManager));
        applicationStateManager.displayPopUp(popUp);
    }
}
