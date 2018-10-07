package seng202.team4.controller.activityimport;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import seng202.team4.App;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.Controller;

/**
 * Controller class for the activity import warning pop up.
 */
public class ActivityImportWarningPopUpController extends Controller {

    /**
     * Creates a new ActivityImportWarningPopUpController with the given ApplicationStateManager.
     *
     * @param applicationStateManager The ApplicationStateManager of the app.
     */
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

    /** Rectangle of the pop up. */
    @FXML
    private Rectangle popUpRectangle;

    /** Initializes the ActivitiesImportWarningPopUP pop up. */
    @FXML
    public void initialize() {
        Image backgroundImage = new Image(App.class.getResource("view/blue_cascade.jpg").toExternalForm());
        popUpRectangle.setFill(new ImagePattern(backgroundImage));
    }

    /**
     * Closes the pop up.
     * Is called when the user clicks the close button.
     */
    @FXML
    public void close() {
        applicationStateManager.closePopUP(mainPane);
    }

    /**
     * Adds a new warning to the pop up.
     *
     * @param warning The warning to add as a String.
     */
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
