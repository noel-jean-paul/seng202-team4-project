package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import seng202.team4.App;
import seng202.team4.GuiUtilities;
import seng202.team4.controller.GoalTab.GoalsTabController;

import java.net.URL;

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

    /** MenuButton that allows the user to change or view profile. */
    @FXML
    private MenuButton profileDropDown;

    /** ImageView for the users profile picture. */
    @FXML
    private ImageView profilePictureImageView;

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
     * Initializes the main screen and creates all of the tabs of the main screen.
     */
    @FXML
    public void initialize() {
        // Creates the activity tab.
        Pane pane = new Pane();
        activityTabController = new ActivityTabController(applicationStateManager);
        pane = GuiUtilities.loadPane("ActivityTab.fxml", activityTabController);
        activityPane.getChildren().setAll(pane);

        // Creates the home tab.
        Pane home = new Pane();
        homeTabController = new HomeTabController(applicationStateManager);
        home = GuiUtilities.loadPane("HomeTab.fxml", homeTabController);
        homePane.getChildren().setAll(home);

        // Creates the goals tab.
        Pane goals = new Pane();
        goalsTabController = new GoalsTabController(applicationStateManager);
        //TODO: Implement a proper goals tab.
        goals = GuiUtilities.loadPane("GoalsTab.fxml", goalsTabController);
        goalsPane.getChildren().setAll(goals);

        // Creates the health tab.
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
     * Called when the goal tab is clicked.
     * Displays the goal table in the goal tab.
     */
    @FXML
    void goalTabSelected() {
        goalsTabController.displayGoalTable();
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
        healthTabController.reloadTab();
        healthTabController.setLabels();
    }

    /**
     * Resets the main screen. sets selected tab to home tab and reloads graph data in home tab.
     */
    public void reset() {
        tabPane.getSelectionModel().selectFirst();
        if (applicationStateManager.getCurrentProfile() != null) {
            homeTabController.loadData();
            updateProfileButton();

        }
        // Reset the activity and goals tabs
        activityTabController.reset();
        goalsTabController.reset();
    }

    /**
     * Updates the profile picture and name of the profile button.
     */
    public void updateProfileButton() {
        profileDropDown.setText(applicationStateManager.getCurrentProfile().getFirstName());

        // Load the users profile picture
        URL profileImageUrl = App.class.getResource(applicationStateManager.getCurrentProfile().getPictureURL());
        if (profileImageUrl != null) {
            profilePictureImageView.setImage(GuiUtilities.maskProfileImage(new Image(profileImageUrl.toString())));
        }
    }

}