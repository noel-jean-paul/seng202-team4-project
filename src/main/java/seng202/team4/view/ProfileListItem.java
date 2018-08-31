package seng202.team4.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import seng202.team4.App;
import seng202.team4.controller.ProfileListItemController;
import seng202.team4.model.data.ProfileKey;

public class ProfileListItem extends AnchorPane {

    private final Background selectedBackground = new Background( new BackgroundFill( Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY ) );
    private final Background unselectedBackground = new Background( new BackgroundFill( Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY ) );

    private ProfileKey profileKey;

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
    }

    public void select() {
        this.setBackground(selectedBackground);
    }

    public  void deselect() {
        this.setBackground(unselectedBackground);
    }

    public ProfileKey getProfileKey() {
        return profileKey;
    }

}
