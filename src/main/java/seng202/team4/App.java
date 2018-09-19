package seng202.team4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng202.team4.controller.*;
import seng202.team4.model.database.DataAccesser;

import static javafx.application.Application.launch;

/**
 * The App class is the main class of the program. It initializes many of the different screens that application will
 * use.
 */
public class App extends Application {


    @Override
    public void start(Stage primaryStage) {

        try {
            DataAccesser.initialiseConnection();
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
