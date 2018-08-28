package seng202.team4.controller;

/** Controller for the login screen. */
public class LoginController extends Controller {

    /** Creates a new LoginController with the given ScreenStatemanager. */
    public LoginController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /**
     * Action that is performed when the user clicks the create new profile button.
     * This causes the App to change to the create profile screen.
     */
    public void createProfile() {
        System.out.println("You want to create a profile");
        applicationStateManager.switchToScreen("CreateProfileScreen");
    }


}
