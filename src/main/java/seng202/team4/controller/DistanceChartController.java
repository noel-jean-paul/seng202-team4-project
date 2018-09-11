package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import seng202.team4.model.data.Profile;
import seng202.team4.model.database.DataStorer;

import java.net.URL;
import java.util.ResourceBundle;


public class DistanceChartController extends Controller {
    @FXML
    private NumberAxis distanceYAxis;

    @FXML
    private BarChart<?, ?> distanceChart;

    @FXML
    private CategoryAxis activityNameXAxis;

    @FXML
    private Button button;

    @FXML
    public void displayData() {
        XYChart.Series set1 = new XYChart.Series<>();
        set1.getData().add(new XYChart.Data("Walk in the Woods", 50));
        distanceChart.getData().addAll(set1);
    }

    //Creates a new CreateProfileController with the given ApplicationStateManager.
    public DistanceChartController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }
}