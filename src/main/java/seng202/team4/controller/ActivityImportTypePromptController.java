package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import seng202.team4.Utilities;

import java.io.File;
import java.sql.SQLException;

/**
 * Controller for the activity import type prompt. This handles the selection of how the user wants to add activities.
 */
public class ActivityImportTypePromptController extends Controller {

    /** ActivityTabController of the activity tab. */
    private ActivityTabController activityTabController;

    /** The main AnchorPane of the prompt. */
    @FXML
    private AnchorPane popupPrompt;


    /**
     * Constructor for the ActivityImportTypePromptController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public ActivityImportTypePromptController(ApplicationStateManager applicationStateManager, ActivityTabController activityTabController) {
        super(applicationStateManager);
        this.activityTabController = activityTabController;
    }


    /** The method that is called when the user clicks the cancel button. */
    @FXML
    public void cancel() {
        closePopup();
    }


    /** Closes the prompt pop up. */
    private void closePopup () {
        applicationStateManager.closePopUP(popupPrompt);
    }


    /** The method that is called when the users click the import file button.
     *  The user is then able to select a file using the FileChooser and the
     *  screen changes to the preview file import screen.
     */
    @FXML
    public void importActivityFromFile() throws SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV Data File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(applicationStateManager.getPrimaryStage());
        if (file != null) {
            closePopup();
            ImportActivitiesPreviewScreenController previewContoller = new ImportActivitiesPreviewScreenController(applicationStateManager, activityTabController);
            Pane importPreviewPane = Utilities.loadPane("ImportActivitesPreviewScreen.fxml", previewContoller);

            applicationStateManager.addScreen("ImportActivitesPreviewScreen", importPreviewPane);
            applicationStateManager.switchToScreen("ImportActivitesPreviewScreen");

            previewContoller.loadActivities(file);
        }
    }

}
