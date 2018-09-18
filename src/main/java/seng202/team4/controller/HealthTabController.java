package seng202.team4.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.enums.ActivityType;
import seng202.team4.model.database.DataLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller for the Home Tab.
 */
public class HealthTabController extends Controller {

    @FXML
    private TableView<?> healthWarningTable;

    @FXML
    private Button viewInfoButton;

    @FXML
    private TextArea healthTabTextBox;

    @FXML
    void loadInformation() {

    }

    /**
     * Constructor for the HomeTabController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public HealthTabController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }


    /**
     * Initializes the home tab.
     */
    @FXML
    public void initialize() {

    }


}