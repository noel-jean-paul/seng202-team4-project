package seng202.team4;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng202.team4.actions.NoelSetup_6Goals;
import seng202.team4.controller.ApplicationStateManager;
import seng202.team4.controller.CreateProfileController;
import seng202.team4.controller.LoginController;
import seng202.team4.controller.MainScreenController;
import seng202.team4.model.database.DataAccesser;

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

        // !! does not work - would need to query the database to see if it has a connection open currently
//        // Check that the database is not already in use
//        if (!DataAccesser.checkNullConnection()) {
//            System.out.println("An instance of the app is aleady open");
//            // TODO: 21/09/18 Implement this by asking the database if it has any connections open?
//            try {
//                DataAccesser.closeDatabase();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//            System.exit(1);
//        }

        // Redirects System Out and System Err to ErrorLog.txt
        try {
            File errorLog = new File("ErrorLog.txt");
            PrintStream errorLogStream = new PrintStream(errorLog);
            System.setOut(errorLogStream);
            System.setErr(errorLogStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to open ErrorLog.txt, This is not critical.");
        }

        // Establishes connection with the data base.
        try {
            DataAccesser.initialiseMainConnection();
        } catch (java.sql.SQLException e) {
            System.out.println("Error: Could not establish connection with the data base.");
            e.printStackTrace();
            System.exit(1);
        }

        // Creates base scene.
        Scene baseScene = new Scene(new Group(), 660, 460);

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

    /** Main method of the program. */
    public static void main(String[] args) throws SQLException {
        //NoelSetup_6Goals.main(new String[1]);
        launch(args); }
}
