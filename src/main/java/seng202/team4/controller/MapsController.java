package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MapsController extends Controller {

    @FXML
    private AnchorPane mainPane;

    public MapsController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }


    @FXML
    public void initialize() {
        //Initialization code goes here (called just after the pane has been loaded)
    }

}
