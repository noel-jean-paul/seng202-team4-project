package seng202.team4.controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import seng202.team4.model.Profile;

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

    private Profile userProfile;

    /** Creates a new ApplicationStateManager for the given root Scene. */
    public ApplicationStateManager(Scene root) {
        this.rootScene = root;
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
        rootScene.setRoot(paneMap.get(name));
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

}
