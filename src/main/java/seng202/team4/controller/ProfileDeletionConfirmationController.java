package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import seng202.team4.model.database.DataStorer;

import java.sql.SQLException;

/** Controller class for the profile deletion confirmation box */
public class ProfileDeletionConfirmationController extends Controller {

    @FXML
    private AnchorPane rootPane;

    /** Creates a new Profile deletion controller with the given ApplicationStateManager. */
    public ProfileDeletionConfirmationController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
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
            applicationStateManager.displayErrorMessage("Failed to delete profile.", "");
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
