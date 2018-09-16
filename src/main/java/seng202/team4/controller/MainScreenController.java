package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ProgressBar;
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
    private HomeTabController homeTabController;

    /** The AnchorPane of the main screen. */
    @FXML
    private AnchorPane activityPane;

    /** The AnchorPane of the home screen. */
    @FXML
    private AnchorPane homePane;



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

        Pane home = new Pane();
        homeTabController = new HomeTabController(applicationStateManager);
        home = Utilities.loadPane("HomeTab.fxml", homeTabController);
        homePane.getChildren().setAll(home);


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
    public void viewProfile() {
        ProfileScreenController profileScreenController = new ProfileScreenController(applicationStateManager);
        Pane profileScreen = Utilities.loadPane("ProfileScreen.fxml", profileScreenController);
        applicationStateManager.addScreen("ProfileScreen", profileScreen);
        applicationStateManager.switchToScreen("ProfileScreen");
        profileScreenController.updateInformation();
    }


    @FXML
    void activityTabSelected() {
        activityTabController.updateTable();
    }

    /**
     * @// TODO: 16/09/18 make something happen when tab selected if necessary 
     */
    @FXML
    public void homeTabSelected() {

    }

}