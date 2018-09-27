package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/** Controller class for the calender view */
public class CalendarViewController extends Controller {

    /** The root Node of the calender view */
    @FXML
    private AnchorPane mainPane;

    public CalendarViewController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /** Initializes the calender view */
    @FXML
    public void initialize() {
        //TODO
    }
}
