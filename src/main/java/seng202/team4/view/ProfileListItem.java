package seng202.team4.view;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import seng202.team4.App;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.Controller;
import seng202.team4.controller.ProfileListItemController;

public class ProfileListItem extends AnchorPane {

    private final Background focusBackground = new Background( new BackgroundFill( Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY ) );
    private final Background unfocusBackground = new Background( new BackgroundFill( Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY ) );

    public ProfileListItem(Controller controller) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("view/ProfileListItem.fxml"));
        loader.setControllerFactory(c -> {return controller;});
        loader.setRoot(this);
        try {
            loader.load();
        } catch (java.io.IOException e) {
            System.out.println(String.format("Error: Could not load %s", loader.getLocation()));
        }

        this.setOnMouseClicked(event -> {this.requestFocus();});
        this.backgroundProperty().bind( Bindings
                .when( this.focusedProperty() )
                .then( focusBackground )
                .otherwise( unfocusBackground )
        );
    }

}
