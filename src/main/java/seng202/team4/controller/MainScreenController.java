package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import seng202.team4.Utilities;

import java.net.URL;
import java.util.ResourceBundle;

/** Controller for the main screen of the App. */
public class MainScreenController extends Controller implements Initializable {

    @FXML
    private AnchorPane activityPane;

    /** Creates a new LoginController with the given ApplicationStateManager. */
    public MainScreenController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);

    }

    /**
     * Creates loads and intializes the different tabs of the main screen.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Pane Pane = new Pane();
        Pane = Utilities.loadPane("ActivityTab.fxml", new Controller(applicationStateManager));
        activityPane.getChildren().setAll(Pane);
    }

    /**
     * Takes the user to the login is screen where they may switch their profile.
     */
    public void switchProfile() {
        applicationStateManager.switchToScreen("LoginScreen");
    }
}