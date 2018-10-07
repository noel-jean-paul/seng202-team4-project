package seng202.team4.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import seng202.team4.App;
import seng202.team4.controller.profile.ProfileListItemController;
import seng202.team4.model.data.Keys.ProfileKey;

/** The item that is displayed in a profile list/ */
public class ProfileListItem extends AnchorPane {

    /** Background of a selected profile. */
    private final Background selectedBackground = new Background( new BackgroundFill( Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY ) );

    /** Background of an unselected profile. */
    private final Background unselectedBackground = new Background( new BackgroundFill( Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY ) );

    /** ProfileKey of the profile that is being displayed. */
    private ProfileKey profileKey;


    /**
     * Constructor for the ProfileListItem.
     *
     * @param controller The controller of ProfileListItem.
     * @param profileKey The ProfileKey of the profile that is being displayed.
     */
    public ProfileListItem(ProfileListItemController controller, ProfileKey profileKey) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("view/ProfileListItem.fxml"));
        loader.setControllerFactory(c -> {return controller;});
        loader.setRoot(this);
        try {
            loader.load();
        } catch (java.io.IOException e) {
            System.out.println(String.format("Error: Could not load %s", loader.getLocation()));
        }
        controller.setNameText(String.format("%s %s", profileKey.getFirstName(), profileKey.getLastName()));
        this.profileKey = profileKey;

        this.setBackground(unselectedBackground);
    }


    /** Select the ProfileListItem item by applying the selected background. */
    public void select() {
        this.setBackground(selectedBackground);
    }


    /** Deselects the ProfileListItem item by applying the deselected background. */
    public  void deselect() {
        this.setBackground(unselectedBackground);
    }


    /**
     * Gets the profile key of the ProfileListItem.
     *
     * @return Gets the ProfileKey of the profile that the ProfileListItem represents.
     */
    public ProfileKey getProfileKey() {
        return profileKey;
    }

}
