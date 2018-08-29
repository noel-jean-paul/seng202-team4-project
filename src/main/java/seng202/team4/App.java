package seng202.team4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng202.team4.controller.Controller;
import seng202.team4.controller.CreateProfileController;
import seng202.team4.controller.LoginController;
import seng202.team4.controller.ScreenStateManager;
import seng202.team4.model.database.DataLoader;
import seng202.team4.model.database.DataStorer;

import static javafx.application.Application.launch;

/**
 * The App class is the main class of the program. It initializes many of the different screens that application will
 * use.
 */
public class App extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene baseScene = new Scene(new Group(), 600, 400);
        ScreenStateManager screenManager = new ScreenStateManager(baseScene);

        Pane loginScreen = initializePane("LoginScreen.fxml", new LoginController(screenManager));

        Pane createProfileScreen = initializePane("CreateProfileScreen.fxml", new CreateProfileController(screenManager));

        screenManager.addScreen("LoginScreen", loginScreen);
        screenManager.addScreen("CreateProfileScreen", createProfileScreen);
        screenManager.switchToScreen("LoginScreen");

        primaryStage.setTitle("Step by Step");
        primaryStage.setScene(baseScene);
        primaryStage.show();
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
        loader.setControllerFactory(c -> {return controller;});
        Pane pane = loader.load();
        return pane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
