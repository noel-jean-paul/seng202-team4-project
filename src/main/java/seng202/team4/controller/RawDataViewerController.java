package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import seng202.team4.model.data.Activity;
import seng202.team4.model.data.DataRow;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;


/**
 * Controller for the Activity Pop up screen.
 */
public class RawDataViewerController extends Controller {

    @FXML
    private AnchorPane popupPane;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private TableColumn<?, ?> timeColumn;

    @FXML
    private TableColumn<?, ?> heartRateColumn;

    @FXML
    private TableColumn<?, ?> latitudeColumn;

    @FXML
    private TableColumn<?, ?> longitudeColumn;

    @FXML
    private TableColumn<?, ?> elevationColumn;

    @FXML
    private Text dataTableTitleText;

    private Activity activity;

    /**
     * Constructor for the ActivityPopUpScreenController.
     *
     * @param applicationStateManager The ApplicationStateManager of the application.
     */
    public RawDataViewerController(ApplicationStateManager applicationStateManager, Activity activity) {
        super(applicationStateManager);
        this.activity = activity;
    }

    @FXML
    public void initialize() {

    }

    @FXML
    void closePopUp() {
        applicationStateManager.closePopUP(popupPane);
    }

}
