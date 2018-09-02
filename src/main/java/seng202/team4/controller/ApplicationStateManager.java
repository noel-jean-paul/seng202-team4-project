package seng202.team4.controller;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
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

    /** The root Scene that the screens of the application belong to. */
    private Scene rootScene;

    private Stage primaryStage;

    private StackPane stackPane = new StackPane();

    private Profile userProfile;

    private final Background selectedBackground = new Background( new BackgroundFill( Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY ) );

    /** Creates a new ApplicationStateManager for the given root Scene. */
    public ApplicationStateManager(Scene root, Stage stage) {
        this.rootScene = root;
        this.primaryStage = stage;
        this.rootScene.setRoot(stackPane);
        this.stackPane.getChildren().add(new Pane());
        stackPane.setBackground(selectedBackground);
    }

    /**
     * Adds a screen to the ApplicationStateManager.
     *
     * @param name The name of the screen.
     * @param pane The screen to be added.
     */
    public void addScreen(String name, Pane pane) {
        paneMap.put(name, pane);
    }

    /**
     * Switch to a particular screen.
     *
     * @param name The name of the screen to switch to.
     */
    public void switchToScreen(String name) {
        stackPane.getChildren().set(0, paneMap.get(name));
        paneMap.get(name).prefWidthProperty().bind(stackPane.widthProperty());
        paneMap.get(name).prefHeightProperty().bind(stackPane.heightProperty());
    }


    public void displayPopUp(Pane popUp) {
        stackPane.getChildren().add(popUp);
    }

    public void closePopUP(Pane popUp) {
        stackPane.getChildren().remove(popUp);
    }

    /**
     * Removes a screen from the ApplicationStateManager.
     * @param name The name of the screen to be removed, if no screen of that name
     *             is found then the function has no effect.
     */
    public void removeScreen(String name) {
        paneMap.remove(name);
    }

    /**
     * Checks whether the ApplicationStateManager contains a screen of the given name.
     *
     * @param name The name of the screen to be checked.
     * @return
     */
    public boolean containsScreen(String name) {
        return paneMap.containsKey(name);
    }

    /**
     * Checks whether the ApplicationStateManager contains the given screen.
     *
     * @param pane The screen to checked.
     * @return
     */
    public  boolean containsScreen(Pane pane) {
        return paneMap.containsValue(pane);
    }

    /**
     * Sets the current profile of the application.
     *
     * @param profile
     */
    public void setCurrentProfile(Profile profile) {
        this.userProfile = profile;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
