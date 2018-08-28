package seng202.team4.controller;

/** Base controller class that all other controllers will extend. */
public class Controller {
    /** The ScreenStateManager of the App */
    protected ScreenStateManager screenStateManager;

    /** Creates a new controller with the given ScreenStateManager */
    public Controller(ScreenStateManager screenStateManager) {
        this.screenStateManager = screenStateManager;
    }
}
