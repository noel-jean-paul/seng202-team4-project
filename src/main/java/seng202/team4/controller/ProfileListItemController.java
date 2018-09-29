package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import seng202.team4.GuiUtilities;

/** The Controller for the ProfileListItem. */
public class ProfileListItemController extends Controller {

    /** The AnchorPane of the ProfileListItem. */
    @FXML
    private AnchorPane profileListItemPane;

    /** The Text that displays the name of the profile. */
    @FXML
    private Text nameText;

    /** The ImageView of the users profile picture. */
    @FXML
    private ImageView profilePictureImageView;


    /**
     * Constructor of the ProfileListItemController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public ProfileListItemController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /** Sets the name of the name Text. */
    public void setNameText(String text) {
        nameText.setText(text);
    }

    /** Sets the image of the profile picture. */
    public void setProfilePicture(Image image) {
        profilePictureImageView.setImage(GuiUtilities.maskProfileImage(image));
    }
}
