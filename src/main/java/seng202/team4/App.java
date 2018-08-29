package seng202.team4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng202.team4.controller.*;

import static javafx.application.Application.launch;

/**
 * The App class is the main class of the program. It initializes many of the different screens that application will
 * use.
 */
public class App extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene baseScene = new Scene(new Group(), 600, 400);
        ApplicationStateManager applicationStateManager = new ApplicationStateManager(baseScene);

        Pane loginScreen = initializePane("LoginScreen.fxml", new LoginController(applicationStateManager));
        Pane createProfileScreen = initializePane("CreateProfileScreen.fxml", new CreateProfileController(applicationStateManager));
        Pane mainScreen = initializePane("MainScreen.fxml", new MainScreenController(applicationStateManager));


        applicationStateManager.addScreen("LoginScreen", loginScreen);
        applicationStateManager.addScreen("CreateProfileScreen", createProfileScreen);
        applicationStateManager.addScreen("MainScreen", mainScreen);
        applicationStateManager.switchToScreen("LoginScreen");

        primaryStage.setTitle("Step by Step");
        primaryStage.setScene(baseScene);
        primaryStage.show();
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
    }

    /**
     * Initializes and returns a JavaFX pane from the given arguments.
     *
     * @param fxmlFilename The name of the fxml file that describes the pane.
     * @param controller The controller class for the pane.
     * @return A newly created pane.
     * @throws Exception If the function fails to load the program.
     */
    private Pane initializePane(String fxmlFilename, Controller controller) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/" + fxmlFilename));
        System.out.println(loader.getLocation());
        loader.setControllerFactory(c -> {return controller;});
        Pane pane = loader.load();
        return pane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
