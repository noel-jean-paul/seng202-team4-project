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

/** The Controller for the ProfileListItem. */
public class ProfileListItemController extends Controller {

    /** The AnchorPane of the ProfileListItem. */
    @FXML
    private AnchorPane profileListItemPane;

    /** The Text that displays the name of the profile. */
    @FXML
    private Text nameText;


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
}
