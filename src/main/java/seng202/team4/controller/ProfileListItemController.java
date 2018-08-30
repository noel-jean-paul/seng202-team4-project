package seng202.team4.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileListItemController extends Controller implements Initializable {

    private final Background focusBackground = new Background( new BackgroundFill( Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY ) );
    private final Background unfocusBackground = new Background( new BackgroundFill( Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY ) );

    @FXML
    private AnchorPane profileListItemPane;

    @FXML
    private Text nameText;

    public ProfileListItemController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        profileListItemPane.setOnMouseClicked(event -> {profileListItemPane.requestFocus();});
        profileListItemPane.backgroundProperty().bind( Bindings
                .when( profileListItemPane.focusedProperty() )
                .then( focusBackground )
                .otherwise( unfocusBackground )
        );
    }

    public void setNameText(String text) {
        nameText.setText(text);
    }

}
