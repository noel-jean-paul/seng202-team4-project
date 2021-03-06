package seng202.team4.controller.healthtab;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import seng202.team4.App;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.Controller;

/** Controller class for the health warning detected popup */
public class HealthWarningDetectedPopup extends Controller {

    /** The pane which contains all of the popup window's elements */
    @FXML
    private GridPane popupWarningDetected;

    /** The button used to close the popup window */
    @FXML
    private Button cancelButton;

    /** Closes the popupWindow when the cancel button is pressed */
    @FXML
    void closePopup() {
        applicationStateManager.closePopUP(popupWarningDetected);
    }

    /** The background of the popup window */
    @FXML
    private Rectangle popUpRectangle;

    /**
     * The constructor for the popup window
     *
     * @param applicationStateManager the application state manager for the program.
     */
    public HealthWarningDetectedPopup(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /** Initialises the background of the popup after all elements have been inserted */
    @FXML
    public void initialize() {
        Image backgroundImage = new Image(App.class.getResource("view/blue_cascade.jpg").toExternalForm());
        popUpRectangle.setFill(new ImagePattern(backgroundImage));
    }

}
