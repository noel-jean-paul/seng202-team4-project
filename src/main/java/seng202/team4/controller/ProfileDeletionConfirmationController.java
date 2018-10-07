package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import seng202.team4.App;
import seng202.team4.GuiUtilities;
import seng202.team4.model.database.DataStorer;

import java.sql.SQLException;

/** Controller class for the profile deletion confirmation box */
public class ProfileDeletionConfirmationController extends Controller {

    /** The overall pane for the confirmation window */
    @FXML
    private AnchorPane rootPane;

    /** The background for the confirmation window */
    @FXML
    private Rectangle popUpRectangle;

    /**
     * Creates a new Profile deletion controller with the given ApplicationStateManager.
     * @param applicationStateManager The ApplicationStateManager of the app.
     */
    public ProfileDeletionConfirmationController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /** Initialises the confirmation window after it's been populated */
    @FXML
    public void initialize() {
        Image backgroundImage = new Image(App.class.getResource("view/blue_cascade.jpg").toExternalForm());
        popUpRectangle.setFill(new ImagePattern(backgroundImage));
    }

    /**
     * Called if user clicks the 'yes' button.
     * Deletes the profile, then closes the popup.
     */
    @FXML
    void yesClicked() {
        try {
            DataStorer.deleteProfile(applicationStateManager.getCurrentProfile());
            ((LoginController) applicationStateManager.getScreenController("LoginScreen")).updateProfileList();
            applicationStateManager.switchToScreen("LoginScreen");
        } catch (SQLException e) {
            GuiUtilities.displayErrorMessage("Failed to delete profile.", "");
        }
        applicationStateManager.closePopUP(rootPane);

    }

    /**
     * Called if user clicks the 'no' button.
     * closes the popup.
     */
    @FXML
    void noClicked() {
        applicationStateManager.closePopUP(rootPane);
    }

}
