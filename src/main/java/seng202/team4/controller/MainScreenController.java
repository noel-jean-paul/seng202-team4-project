package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController extends Controller implements Initializable {

    @FXML
    private AnchorPane activityPane;

    public MainScreenController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/ActivityTab.fxml"));
        loader.setControllerFactory(c -> {return new Controller(applicationStateManager);});


        Pane pane = new AnchorPane();
        try {
            loader.setLocation(new URL("file:/C:/Users/Matthew%20Toohey/Documents/UNI/2018/SENG202/local_repo/Seng202_Team4/target/classes/seng202/team4/view/ActivityTab.fxml"));
            System.out.println(loader.getLocation());
            pane = loader.load();
        } catch (java.io.IOException e) {
            System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!");
        }

        System.out.println(pane);
        activityPane.getChildren().setAll(pane);
    }
}