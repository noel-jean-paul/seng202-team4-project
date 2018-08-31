package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;

public class ActivityImportTypePromptController extends Controller {

    public ActivityImportTypePromptController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    private AnchorPane popupPrompt;

    @FXML
    public void closePopup() {
        applicationStateManager.closePopUP(popupPrompt);
    }

    @FXML
    public void importActivityFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV Data File");
        File file = fileChooser.showOpenDialog(applicationStateManager.getPrimaryStage());
    }

}
