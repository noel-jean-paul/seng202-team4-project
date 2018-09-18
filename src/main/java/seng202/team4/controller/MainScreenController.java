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
    private GoalsTabController goalsTabController;

    /** The AnchorPane of the main screen. */
    @FXML
    private AnchorPane activityPane;

    /** The AnchorPane of the home screen. */
    @FXML
    private AnchorPane homePane;

    /** The AnchorPane of the goals screen */
    @FXML
    private AnchorPane goalsPane;



    /**
     * Constructor of the MainScreenController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public MainScreenController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);

    }

    /**
     * Creates, loads and initialize        //List<Activity> activityList = applicationStateManager.getCurrentProfile().getActivityList();
        XYChart.Series set1 = new XYChart.Series<>();

//        for (int i = 0; i < 5; i++) {
//            set1.getData().add(new XYChart.Data(activityList.get(i).getName(), activityList.get(i).getDistance()));
//        }
        set1.getData().add(new XYChart.Data("Walk in woods", 1.5));
//        set1.getData().add(new XYChart.Data("fun run", 2));
//        set1.getData().add(new XYChart.Data("Run through town", 12));
//        set1.getData().add(new XYChart.Data("Marathon", 42));
        distanceBarGraph.getData().addAll(set1);s the different tabs of the main screen.
     */
    @FXML
    public void initialize() {
        Pane Pane = new Pane();
        activityTabController = new ActivityTabController(applicationStateManager);
        Pane = Utilities.loadPane("ActivityTab.fxml", activityTabController);
        activityPane.getChildren().setAll(Pane);

        // TODO: Figure out how to get these tabs to fit to parent, so they can expand
        Pane home = new Pane();
        homeTabController = new HomeTabController(applicationStateManager);
        home = Utilities.loadPane("HomeTab.fxml", homeTabController);
        homePane.getChildren().setAll(home);

        Pane goals = new Pane();
        goalsTabController = new GoalsTabController(applicationStateManager);
        goals = Utilities.loadPane("GoalsTab.fxml", goalsTabController);
        goalsPane.getChildren().setAll(goals);
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


    @FXML
    void homeTabSelected() {
        //homeTabController.loadData();
    }

}