package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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
