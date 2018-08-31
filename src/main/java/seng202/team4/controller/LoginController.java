package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import seng202.team4.model.data.ProfileKey;
import seng202.team4.model.database.DataLoader;
import seng202.team4.view.ProfileListItem;

import java.util.List;

/** Controller for the login screen. */
public class LoginController extends Controller {

    @FXML
    private VBox profileListVbox;

    @FXML
    private ScrollPane profileListScrollPane;

    private ProfileListItem selectedProfileItem = null;

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
            ProfileListItem profileListItem = new ProfileListItem(controller, profileKey);
            profileListItem.setOnMouseClicked(event -> {changeSelectedProfile(profileListItem);});
            profileListVbox.getChildren().add(profileListItem);
        }
    }

    public void changeSelectedProfile(ProfileListItem profileListItem) {
        if (selectedProfileItem != null) {
            selectedProfileItem.deselect();
        }
        selectedProfileItem = profileListItem;
        selectedProfileItem.select();
    }

    /**
     * Action that is performed when the user clicks the create new profile button.
     * This causes the App to change to the create profile screen.
     */
    @FXML
    public void createProfile() {
        System.out.println("You want to create a profile");
        applicationStateManager.switchToScreen("CreateProfileScreen");
    }

    @FXML
    public void login() {
        ProfileKey profileKey = selectedProfileItem.getProfileKey();
        try {
            applicationStateManager.setCurrentProfile(DataLoader.loadProfile(profileKey.getFirstName(), profileKey.getLastName()));
            applicationStateManager.switchToScreen("MainScreen");
            System.out.println(String.format("%s %s has logged in!", profileKey.getFirstName(), profileKey.getLastName()));
        } catch (java.sql.SQLException e) {
            System.out.println("Error: Failed to load profile.");
        }
    }


}
