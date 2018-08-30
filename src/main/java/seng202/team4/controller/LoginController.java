package seng202.team4.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import seng202.team4.Utilities;

import java.net.URL;
import java.util.ResourceBundle;

/** Controller for the login screen. */
public class LoginController extends Controller implements Initializable {

    @FXML
    private VBox profileListVbox;

    /** Creates a new LoginController with the given ApplicationStateManager. */
    public LoginController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Pane profileItemPane = Utilities.loadPane("ProfileListItem.fxml", new profileListItemController(applicationStateManager));

        profileListVbox.getChildren().add(profileItemPane);
        profileListVbox.getChildren().add(Utilities.loadPane("ProfileListItem.fxml", new profileListItemController(applicationStateManager)));
        profileListVbox.getChildren().add(Utilities.loadPane("ProfileListItem.fxml", new profileListItemController(applicationStateManager)));
        profileListVbox.getChildren().add(Utilities.loadPane("ProfileListItem.fxml", new profileListItemController(applicationStateManager)));
    }

    /**
     * Action that is performed when the user clicks the create new profile button.
     * This causes the App to change to the create profile screen.
     */
    public void createProfile() {
        System.out.println("You want to create a profile");
        applicationStateManager.switchToScreen("CreateProfileScreen");
    }


}
