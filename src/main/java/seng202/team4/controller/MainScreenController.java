package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import seng202.team4.GuiUtilities;

/** Controller for the main screen of the App. */
public class MainScreenController extends Controller {

    /** The AnchorPane of the main screen. */
    @FXML
    private AnchorPane activityPane;

    /** The AnchorPane of the home screen. */
    @FXML
    private AnchorPane homePane;

    /** The AnchorPane of the goals screen */
    @FXML
    private AnchorPane goalsPane;

    /** The AnchorPane of the goals screen */
    @FXML
    private AnchorPane healthPane;

    /** The TabPane of the main screen. */
    @FXML
    private TabPane tabPane;

    /** The ActivityTabController of the activity tab. */
    private ActivityTabController activityTabController;

    /** The HomeTabController of the home tab. */
    private HomeTabController homeTabController;

    /** The GoalsTabController of the goals tab. */
    private GoalsTabController goalsTabController;

    /** The HealthTabController of the health tab. */
    private HealthTabController healthTabController;



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
import javafx.scene.text.Text;
        set1.getData().add(new XYChart.Data("Walk in woods", 1.5));
//        set1.getData().add(new XYChart.Data("fun run", 2));
//        set1.getData().add(new XYChart.Data("Run through town", 12));
//        set1.getData().add(new XYChart.Data("Marathon", 42));
        distanceBarGraph.getData().addAll(set1);s the different tabs of the main screen.
     */
    @FXML
    public void initialize() {
        Pane pane = new Pane();
        activityTabController = new ActivityTabController(applicationStateManager);
        pane = GuiUtilities.loadPane("ActivityTab.fxml", activityTabController);
        activityPane.getChildren().setAll(pane);

        // TODO: Figure out how to get these tabs to fit to parent, so they can expand
        Pane home = new Pane();
        homeTabController = new HomeTabController(applicationStateManager);
        home = GuiUtilities.loadPane("HomeTab.fxml", homeTabController);
        homePane.getChildren().setAll(home);

        Pane goals = new Pane();
        goalsTabController = new GoalsTabController(applicationStateManager);
        goals = GuiUtilities.loadPane("GoalsTab.fxml", goalsTabController);
        goalsPane.getChildren().setAll(goals);

        Pane health = new Pane();
        healthTabController = new HealthTabController(applicationStateManager);
        health = GuiUtilities.loadPane("HealthTab.fxml", healthTabController);
        healthPane.getChildren().setAll(health);

    }

    /**
     * Action performed when the user clicks the switch profile menu button.
     * Takes the user to the login is screen where they may switch their profile.
     */
    @FXML
    public void switchProfile() {
        ((LoginController) applicationStateManager.getScreenController("LoginScreen")).updateProfileList();
        applicationStateManager.switchToScreen("LoginScreen");
    }

    /**
     * Takes the user to the view profile screen.
     * Is called when the user clicks the 'view profile menu option'.
     */
    @FXML
    public void viewProfile() {
        ProfileScreenController profileScreenController = new ProfileScreenController(applicationStateManager);
        Pane profileScreen = GuiUtilities.loadPane("ProfileScreen.fxml", profileScreenController);
        applicationStateManager.addScreen("ProfileScreen", profileScreen, profileScreenController);
        applicationStateManager.switchToScreen("ProfileScreen");
        profileScreenController.updateInformation();
    }

    /**
     * Called when the activity tab is clicked.
     * Updates the activity table in the activity tab.
     */
    @FXML
    void activityTabSelected() {
        activityTabController.updateTable();
    }

    /**
     * Called when the home tab is clicked.
     * Loads data into the graphs of the home tab.
     */
    @FXML
    void homeTabSelected() {
        if (applicationStateManager.getCurrentProfile() != null) {
            homeTabController.loadData();
        }
    }

    /**
     * Called when the health tab is clicked.
     * Updates the health tab.
     */
    @FXML
    void healthTabSelected() {
        healthTabController.updateTable();
        healthTabController.setLabels();
    }

    /**
     * Resets the main screen. sets selected tab to home tab and reloads graph data in home tab.
     */
    public void reset() {
        tabPane.getSelectionModel().selectFirst();
        if (applicationStateManager.getCurrentProfile() != null) {
            homeTabController.loadData();
        }
        activityTabController.reset();
    }

}