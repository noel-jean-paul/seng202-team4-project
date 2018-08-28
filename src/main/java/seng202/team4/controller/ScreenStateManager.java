package seng202.team4.controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

/**
 * The ScreenStateManager class stores screens associated with a particular root Scene.
 * It can be used to change between and keep track of the different screens of the App.
 */
public class ScreenStateManager {
    /** A HashMap that stores the screens (Pane) with a particular screen key. */
    private HashMap<String, Pane> paneMap = new HashMap<String, Pane>();

    /** The root Scene that the screens of the manager belong to. */
    private Scene rootScene;

    /** Creates a new ScreenStateManager for the given root Scene. */
    public ScreenStateManager(Scene root) {
        this.rootScene = root;
    }

    /**
     * Adds a screen to the ScreenStateManager.
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

}
