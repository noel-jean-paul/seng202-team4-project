package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Controller class for the activity import warning pop up.
 */
public class ActivityImportWarningPopUpController extends Controller {

    /** Creates a new ActivityImportWarningPopUpController with the given ApplicationStateManager. */
    ActivityImportWarningPopUpController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /**  The root pane of the popup. */
    @FXML
    private AnchorPane mainPane;

    /** VBox that will contain any warning text. */
    @FXML
    private VBox warningsVbox;

    /**
     * Closes the pop up.
     * Is called when the user clicks the close button.
     */
    @FXML
    public void close() {
        applicationStateManager.closePopUP(mainPane);
    }

    /** Adds a new warning to the pop up. */
    public void addWarning(String warning) {
        Text text = new Text(warning);
        warningsVbox.getChildren().add(text);
    }

}
