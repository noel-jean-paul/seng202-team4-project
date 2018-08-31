package seng202.team4.controller;

/** Base controller class that all other controllers will extend. */
public class Controller {
    /** The ApplicationStateManager of the App */
    protected ApplicationStateManager applicationStateManager;

    /** Creates a new controller with the given ApplicationStateManager */
    public Controller(ApplicationStateManager applicationStateManager) {
        this.applicationStateManager = applicationStateManager;

    }
}
