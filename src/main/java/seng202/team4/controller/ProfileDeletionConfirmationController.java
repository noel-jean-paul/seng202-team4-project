package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/** Controller class for the profile deletion confirmation box */
public class ProfileDeletionConfirmationController extends Controller {

    @FXML
    private AnchorPane rootPane;

    /** Creates a new Profile deletion controller with the given ApplicationStateManager. */
    public ProfileDeletionConfirmationController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }


    @FXML
    void yesClicked() {

    }

    @FXML
    void noClicked() {
        applicationStateManager.closePopUP(rootPane);
    }

}
