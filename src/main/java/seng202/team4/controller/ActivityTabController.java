package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import seng202.team4.Utilities;

/**
 * Controller for the Activity Tab.
 */
public class ActivityTabController extends Controller {

    /**
     * Constructor for the ActivityTabController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public ActivityTabController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }


    /**
     * The method that is called when the user clicks the add activity button.
     * Displays a prompt to the user on how they would like to add activities.
     */
    @FXML
    public void addActivities() {
        Pane popUp = Utilities.loadPane("ActivityImportTypePrompt.fxml", new ActivityImportTypePromptController(applicationStateManager));
        applicationStateManager.displayPopUp(popUp);
    }
}
