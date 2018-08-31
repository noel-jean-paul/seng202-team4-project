package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import seng202.team4.Utilities;

import java.io.File;

public class ActivityImportTypePromptController extends Controller {

    public ActivityImportTypePromptController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    private AnchorPane popupPrompt;

    @FXML
    public void cancel() {
        closePopup();
    }

    private void closePopup () {
        applicationStateManager.closePopUP(popupPrompt);
    }

    @FXML
    public void importActivityFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV Data File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(applicationStateManager.getPrimaryStage());
        if (file != null) {
            closePopup();
            ImportActivitiesPreviewScreenController previewContoller = new ImportActivitiesPreviewScreenController(applicationStateManager);
            Pane importPreviewPane = Utilities.loadPane("ImportActivitesPreviewScreen.fxml", previewContoller);

            applicationStateManager.addScreen("ImportActivitesPreviewScreen", importPreviewPane);
            applicationStateManager.switchToScreen("ImportActivitesPreviewScreen");

            previewContoller.loadActivities(file);
        }
    }

}
