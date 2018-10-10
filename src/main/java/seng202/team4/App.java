package seng202.team4;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng202.team4.actions.DemoSetup;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.LoginController;
import seng202.team4.controller.MainScreenController;
import seng202.team4.controller.profile.CreateProfileController;
import seng202.team4.model.data.Profile;
import seng202.team4.model.database.DataAccesser;
import seng202.team4.model.database.DataStorer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

/**
 * The App class is the main class of the program. It initializes many of the different screens that application will
 * use.
 */
public class App extends Application {

    /** Starts the app/ */
    @Override
    public void start(Stage primaryStage) {
        // Redirects System Out and System Err to ErrorLog.txt
        try {
            File errorLog = new File("ErrorLog.txt");
            PrintStream errorLogStream = new PrintStream(errorLog);
            System.setOut(errorLogStream);
            System.setErr(errorLogStream);
        } catch (IOException e) {
            e.printStackTrace();
            GuiUtilities.displayErrorMessage("Failed to open ErrorLog.txt", "This is not critical.");
        }

        // Check if the database file exists
        File f = new File(DataAccesser.getDbFileName());
        if (!(f.exists() && !f.isDirectory())) {
            String errorMessage = String.format("File '%s' could not be located at root.", DataAccesser.getDbFileName());
            // Print the error message to the error log
            System.out.println(errorMessage);
            // File not found - inform the user and terminate the app
            GuiUtilities.displayErrorMessage("Database file could not be found.",
                    "", true);
        }

        // Establishes connection with the database.
        try {
            DataAccesser.initialiseMainConnection();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            GuiUtilities.displayErrorMessage("Error encountered when connecting to the database", "",
                       true);
        }

        // Creates base scene.
        Scene baseScene = new Scene(new Group(), 900, 600);

        // Creates application state manager.
        ApplicationStateManager applicationStateManager = new ApplicationStateManager(baseScene, primaryStage);


        //Creates and adds several important screens to the application state manager.

        LoginController loginController = new LoginController(applicationStateManager);
        Pane loginScreen = GuiUtilities.loadPane("LoginScreen.fxml", loginController);

        CreateProfileController createProfileController = new CreateProfileController(applicationStateManager);
        Pane createProfileScreen = GuiUtilities.loadPane("CreateProfileScreen.fxml", createProfileController);

        MainScreenController mainScreenController = new MainScreenController(applicationStateManager);
        Pane mainScreen = GuiUtilities.loadPane("MainScreen.fxml", mainScreenController);

        applicationStateManager.addScreen("LoginScreen", loginScreen, loginController);
        applicationStateManager.addScreen("CreateProfileScreen", createProfileScreen, createProfileController);
        applicationStateManager.addScreen("MainScreen", mainScreen, mainScreenController);
        applicationStateManager.switchToScreen("LoginScreen");

        // Sets parameters of the window the app runs in.
        primaryStage.setTitle("Step by Step");
        primaryStage.getIcons().add(new Image(App.class.getResource("images/footprints.png").toString()));
        primaryStage.setScene(baseScene);
        baseScene.getStylesheets().add(App.class.getResource("view/TabViewStyle.css").toExternalForm());
        primaryStage.show();
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());

        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.centerOnScreen();
    }

    /**
     * Main method of the program.
     * There should be now arguments given to the app and any that are will be ignored.
     *
     * @param args The arguments to run the app with.
     */
    public static void main(String[] args) throws SQLException {

        launch(args); }
}
