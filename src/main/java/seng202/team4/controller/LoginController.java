package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import seng202.team4.App;
import seng202.team4.GuiUtilities;
import seng202.team4.controller.profile.ProfileListItemController;
import seng202.team4.model.data.keys.ProfileKey;
import seng202.team4.model.database.DataLoader;
import seng202.team4.view.ProfileListItem;

import java.net.URL;
import java.util.List;

/** Controller for the login screen. */
public class LoginController extends Controller {

    /** The VBox that holds the list of profiles. */
    @FXML
    private VBox profileListVbox;

    /** The ScrollPane that holds the profileListVbox. */
    @FXML
    private ScrollPane profileListScrollPane;

    /** The currently selected ProfileListItem. */
    private ProfileListItem selectedProfileItem = null;

    /**
     * Creates a new LoginController with the given ApplicationStateManager.
     *
     * @param applicationStateManager The ApplicationStateManager of the app.
     */
    public LoginController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /** Initializes the LoginController by updating the profile list. */
    @FXML
    public void initialize() {
        updateProfileList();
    }

    /** Updates the list of profiles displayed by loading all the profiles from the database. */
    public void updateProfileList() {
        profileListVbox.getChildren().clear();
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

            // Load the users profile picture
            URL profileImageUrl = null;
            try {
                profileImageUrl = App.class.getResource(DataLoader.loadProfile(profileKey.getFirstName(), profileKey.getLastName()).getPictureURL());
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }

            if (profileImageUrl != null) {
                controller.setProfilePicture(new Image(profileImageUrl.toString()));
            }

            // Set function to be called when the list item is clicked on
            profileListItem.setOnMouseClicked(event -> {changeSelectedProfile(profileListItem);});
            profileListVbox.getChildren().add(profileListItem);

            profileListItem.prefWidthProperty().bind(profileListScrollPane.widthProperty());
        }
    }

    /**
     * Changes the selected ProfileListItem.
     *
     * @param profileListItem The new selected profileListItem.
     */
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
        reset();
    }

    /**
     * Action that is performed when the user clicks the login button.
     * The user is logged into the currently selected profile and
     * the application changes to the main screen.
     */
    @FXML
    public void login() {
        if (selectedProfileItem != null) {
            ProfileKey profileKey = selectedProfileItem.getProfileKey();
            try {
                applicationStateManager.setCurrentProfile(DataLoader.loadProfile(profileKey.getFirstName(), profileKey.getLastName()));
                applicationStateManager.switchToScreen("MainScreen");
                ((MainScreenController) applicationStateManager.getScreenController("MainScreen")).reset();

                applicationStateManager.getCurrentProfile().findWarnings();

                reset();

                System.out.println(String.format("%s %s has logged in!", profileKey.getFirstName(), profileKey.getLastName()));
            } catch (java.sql.SQLException e) {
                GuiUtilities.displayErrorMessage("An error occurred loading the profile from the database.", e.getMessage());
                System.out.println("Error: Failed to load profile.");
            }
        }
    }

    /**
     * Resets the login screen by deselected the currently selected activity.
     */
    private void reset() {
        if (selectedProfileItem != null) {
            selectedProfileItem.deselect();
        }
        selectedProfileItem = null;
    }


}
