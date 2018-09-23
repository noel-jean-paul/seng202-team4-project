package seng202.team4;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng202.team4.controller.*;
import seng202.team4.model.database.DataAccesser;

import java.sql.SQLException;

import static javafx.application.Application.launch;

/**
 * The App class is the main class of the program. It initializes many of the different screens that application will
 * use.
 */
public class App extends Application {


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

        try {
            DataAccesser.initialiseMainConnection();
        } catch (java.sql.SQLException e) {
            System.out.println("Error: Could not establish connection with the data base.");
            System.out.print(e.getMessage());
            System.exit(1);
        }


        Scene baseScene = new Scene(new Group(), 600, 400);

        ApplicationStateManager applicationStateManager = new ApplicationStateManager(baseScene, primaryStage);

        LoginController loginController = new LoginController(applicationStateManager);
        Pane loginScreen = Utilities.loadPane("LoginScreen.fxml", loginController);

        CreateProfileController createProfileController = new CreateProfileController(applicationStateManager);
        Pane createProfileScreen = Utilities.loadPane("CreateProfileScreen.fxml", createProfileController);

        MainScreenController mainScreenController = new MainScreenController(applicationStateManager);
        Pane mainScreen = Utilities.loadPane("MainScreen.fxml", mainScreenController);



        applicationStateManager.addScreen("LoginScreen", loginScreen, loginController);
        applicationStateManager.addScreen("CreateProfileScreen", createProfileScreen, createProfileController);
        applicationStateManager.addScreen("MainScreen", mainScreen, mainScreenController);
        applicationStateManager.switchToScreen("LoginScreen");

        primaryStage.setTitle("Step by Step");
        primaryStage.setScene(baseScene);
        primaryStage.show();
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
