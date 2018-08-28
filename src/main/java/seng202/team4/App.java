package seng202.team4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println(getClass().getResource("LoginScreen.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("view/LoginScreen.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
