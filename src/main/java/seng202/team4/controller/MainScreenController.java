package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import seng202.team4.Utilities;
import seng202.team4.model.data.Activity;

import java.net.URL;
import java.util.ResourceBundle;

/** Controller for the main screen of the App. */
public class MainScreenController extends Controller {

    private ActivityTabController activityTabController;

    /** The AnchorPane of the main screen. */
    @FXML
    private AnchorPane activityPane;


    /**
     * Constructor of the MainScreenController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public MainScreenController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);

    }

    /**
     * Creates, loads and initializes the different tabs of the main screen.
     */
    @FXML
    public void initialize() {
        Pane Pane = new Pane();
        activityTabController = new ActivityTabController(applicationStateManager);
        Pane = Utilities.loadPane("ActivityTab.fxml", activityTabController);
        activityPane.getChildren().setAll(Pane);
    }

    /**
     * Action performed when the user clicks the switch profile menu button.
     * Takes the user to the login is screen where they may switch their profile.
     */
    @FXML
    public void switchProfile() {
        applicationStateManager.switchToScreen("LoginScreen");
    }


    @FXML
    void activityTabSelected() {
        activityTabController.updateTable();
    }
}