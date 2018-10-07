package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import seng202.team4.App;
import seng202.team4.GuiUtilities;

import java.io.File;
import java.io.IOException;

/**
 * Controller for the activity import type prompt. This handles the selection of how the user wants to add activities.
 */
public class ActivityImportTypePromptController extends Controller {

    /** ActivityTabController of the activity tab. */
    private ActivityTabController activityTabController;

    /** The main AnchorPane of the prompt. */
    @FXML
    private AnchorPane popupPrompt;

    @FXML
    private Rectangle popUpRectangle;



    /**
     * Constructor for the ActivityImportTypePromptController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public ActivityImportTypePromptController(ApplicationStateManager applicationStateManager, ActivityTabController activityTabController) {
        super(applicationStateManager);
        this.activityTabController = activityTabController;
    }

    @FXML
    public void initialize() {
        Image backgroundImage = new Image(App.class.getResource("view/blue_cascade.jpg").toExternalForm());
        popUpRectangle.setFill(new ImagePattern(backgroundImage));
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


    /**
     * The method that is called when the users click the import file button.
     * The user is then able to select a file using the FileChooser and the
     * screen changes to the preview file import screen.
     */
    @FXML
    public void importActivityFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV Data File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(applicationStateManager.getPrimaryStage());  //Gets the user to select a file.
        if (file != null) {
            closePopup();   // Closes the popup

            ImportActivitiesPreviewScreenController previewContoller = new ImportActivitiesPreviewScreenController(applicationStateManager, activityTabController);
            Pane importPreviewPane = GuiUtilities.loadPane("ImportActivitesPreviewScreen.fxml", previewContoller);

            // Switches to the ActivityImportPreviewScreen.
            applicationStateManager.addScreen("ImportActivitesPreviewScreen", importPreviewPane, previewContoller);
            applicationStateManager.switchToScreen("ImportActivitesPreviewScreen");

            // Tries to load all the activities from the selected file.
            try {
                previewContoller.loadActivities(file);
            } catch (IOException e) {
                GuiUtilities.displayErrorMessage("Failed to load file.", "");
            }
        }
    }

    /**
     * Displays the popup to add an activity manually.
     * Is called when the user clicks the 'Add manually' button.
     */
    @FXML
    public void addManualActivity() {
        Pane manualEntryPopup = GuiUtilities.loadPane("ManualActivityPopup.fxml", new AddManualActivityController(applicationStateManager, activityTabController));
        applicationStateManager.displayPopUp(manualEntryPopup);
        closePopup();
    }

}
