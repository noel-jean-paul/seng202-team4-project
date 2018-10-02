package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

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

    /** Scroll pane used to scroll through warnings. */
    @FXML
    private ScrollPane scrollPane;

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
        Label label = new Label(warning);
        label.setTextFill(Color.RED);
        label.setFont(Font.font(15));
        label.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.JUSTIFY);
        warningsVbox.getChildren().add(label);
    }

}
