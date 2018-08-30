package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import seng202.team4.Utilities;
import seng202.team4.model.data.utilities.ProfileKey;
import seng202.team4.model.database.DataLoader;
import seng202.team4.view.ProfileListItem;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/** Controller for the login screen. */
public class LoginController extends Controller {

    @FXML
    private VBox profileListVbox;

    @FXML
    private ScrollPane profileListScrollPane;

    /** Creates a new LoginController with the given ApplicationStateManager. */
    public LoginController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    public void initialize() {
        updateProfileList();
    }

    public void updateProfileList() {
        List<ProfileKey> profileKeys = null;
        try {
            profileKeys = DataLoader.fetchAllProfileKeys();
        } catch (java.sql.SQLException e) {
            System.out.println("Error loading profile names from the database.");
            System.exit(1);
        }

        for (ProfileKey profileKey: profileKeys) {
            ProfileListItemController controller = new ProfileListItemController(applicationStateManager);
            ProfileListItem profileListItem = new ProfileListItem(controller);
            profileListVbox.getChildren().add(profileListItem);

            controller.setNameText(String.format("%s %s", profileKey.getFirstName(), profileKey.getLastName()));
        }
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
