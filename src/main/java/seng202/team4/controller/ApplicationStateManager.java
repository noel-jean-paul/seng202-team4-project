package seng202.team4.controller;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import seng202.team4.model.data.Profile;

import java.util.HashMap;

/**
 * The ApplicationStateManager class stores values and screens of the application.
 * It can be used to change between and keep track of the different screens of the application.
 */
public class ApplicationStateManager {

    /** A HashMap that stores the screens (Pane) with a particular screen key. */
    private HashMap<String, Pane> paneMap = new HashMap<String, Pane>();

    /** A HasMap that stores the controllers of the screens. */
    private HashMap<String, Controller> controllerMap = new HashMap<String, Controller>();

    /** The root Scene that the screens of the application belong to. */
    private Scene rootScene;

    /** The primary Stage of the application. */
    private Stage primaryStage;

    /** The StackPane that contains the various layers of the application. */
    private StackPane stackPane = new StackPane();

    /** The currently loaded user profile. */
    private Profile currentUserProfile;

    /** Pane used to block clicks on buttons in the background of a popup. */
    private Pane glassPane;



    /**
     * Constructor of the ApplicationStateManager.
     *
     * @param root The root scene of the application.
     * @param stage The primary Stage of the application.
     */
    public ApplicationStateManager(Scene root, Stage stage) {
        this.rootScene = root;
        this.primaryStage = stage;
        this.rootScene.setRoot(stackPane);
        this.stackPane.getChildren().add(new Pane());

        this.glassPane = new Pane();
        glassPane.prefWidthProperty().bind(rootScene.widthProperty());
        glassPane.prefHeightProperty().bind(rootScene.heightProperty());
    }

    /**
     * Adds a screen to the ApplicationStateManager.
     *
     * @param name The name of the screen.
     * @param pane The screen to be added.
     */
    public void addScreen(String name, Pane pane, Controller controller) {
        paneMap.put(name, pane);
        controllerMap.put(name, controller);
    }

    /**
     * Switches to a particular screen of the ApplicationStateManager.
     *
     * @param name The name of the screen to switch to.
     */
    public void switchToScreen(String name) {
        stackPane.getChildren().set(0, paneMap.get(name));
        paneMap.get(name).prefWidthProperty().bind(stackPane.widthProperty());
        paneMap.get(name).prefHeightProperty().bind(stackPane.heightProperty());
    }

    /**
     * Displays a pop up over the current screen.
     *
     * @param popUp The pop up (pane) to be displayed
     */
    public void displayPopUp(Pane popUp) {

        stackPane.getChildren().remove(glassPane);
        stackPane.getChildren().add(glassPane);
        stackPane.getChildren().add(popUp);
    }

    /**
     * Closes a pop up that is currently being displayed.
     *
     * @param popUp The pop up to be closed.
     */
    public void closePopUP(Pane popUp) {
        if (stackPane.getChildren().indexOf(glassPane) < stackPane.getChildren().indexOf(popUp)) {
            stackPane.getChildren().remove(glassPane);
        }
        stackPane.getChildren().remove(popUp);

    }

    /**
     * Displays an error popup to the user.
     * If the error is critical then the App is closed.
     *
     * @param userMessage User friendly message to display.
     * @param detail More detailed error message to display.
     * @param isCritical Whether the error is critical.
     */
    public void displayErrorMessage(String userMessage, String detail, boolean isCritical) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(userMessage);
        alert.setContentText(detail);
        alert.showAndWait();
        if (isCritical) {
            System.exit(1);
        }
    }

    /**
     * Displays an error popup to the user.
     * Shortcut method that does not require the 'isCritical' flag to be supplied.
     *
     * @param userMessage User friendly message to display.
     * @param detail More detailed error message to display.
     */
    public void displayErrorMessage(String userMessage, String detail) {
        displayErrorMessage(userMessage, detail, false);
    }

    /**
     * Gets the Controller of the screen specified by name.
     *
     * @param screenName The name of the screen.
     * @return The Controller of the screen.
     */
    public Controller getScreenController(String screenName) {
        return controllerMap.get(screenName);
    }

    /**
     * Removes a screen from the ApplicationStateManager.
     *
     * @param name The name of the screen to be removed, if no screen of that name
     *             is found then the function has no effect.
     */
    public void removeScreen(String name) {
        paneMap.remove(name);
        controllerMap.remove(name);
    }

    /**
     * Checks whether the ApplicationStateManager contains a screen of the given name.
     *
     * @param name The name of the screen to be checked.
     * @return true if the ApplicationStateManager contains the screen. false otherwise.
     */
    public boolean containsScreen(String name) {
        return paneMap.containsKey(name);
    }

    /**
     * Checks whether the ApplicationStateManager contains the given screen.
     *
     * @param pane The screen to checked.
     * @return true if the ApplicationStateManager contains the screen. false otherwise.
     */
    public  boolean containsScreen(Pane pane) {
        return paneMap.containsValue(pane);
    }

    /**
     * Sets the current profile of the application.
     *
     * @param profile The new current profile.
     */
    public void setCurrentProfile(Profile profile) {
        this.currentUserProfile = profile;
    }

    public Profile getCurrentProfile() {
        return currentUserProfile;
    }

    /**
     * Gets the primary Stage of the application.
     *
     * @return The primary Stage of the application.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
