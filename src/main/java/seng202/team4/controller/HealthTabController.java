package seng202.team4.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import jdk.nashorn.internal.runtime.regexp.joni.Warnings;
import seng202.team4.App;
import seng202.team4.Utilities;
import seng202.team4.model.data.enums.WarningType;
import seng202.team4.model.utilities.HealthWarning;

import java.time.LocalDate;

public class HealthTabController extends Controller {

    // TODO Implement - Kenny

    @FXML
    private TableView healthWarningTable;

    @FXML
    private TableColumn dateColumn;

    @FXML
    private TableColumn typeColumn;

    @FXML
    private TableColumn descColumn;

    @FXML
    private WebView webBrowser;

    @FXML
    private Button viewInfoButton;

    @FXML
    private Button webSearchButton;

    @FXML
    private Button returnButton;

    @FXML
    private Label ageLabel;

    @FXML
    private Label weightLabel;

    @FXML
    private Label bmiLabel;

    @FXML
    void loadInformation() {
        HealthWarning warning = (HealthWarning) healthWarningTable.getSelectionModel().getSelectedItem();
        if (warning != null) {
            WarningDescriptionPopUpController warningPopUp = new WarningDescriptionPopUpController(applicationStateManager);
            Pane popUp = Utilities.loadPane("HealthPopUpScreen.fxml", warningPopUp);
            setUpPopUpLabels(warningPopUp, warning);
            applicationStateManager.displayPopUp(popUp);
        }
    }

    private void setUpPopUpLabels(WarningDescriptionPopUpController warningPopUp, HealthWarning warning) {
        String heartRateRange = setRateRange(warning);
        warningPopUp.setPopUpTitle(warning.getTypeString());
        warningPopUp.setAverageLabel(warning.getAvgHeartRate());
        warningPopUp.setMinLabel(warning.getMinHeartRate());
        warningPopUp.setMaxLabel(warning.getMaxHeartRate());
        warningPopUp.setDescriptionText(warning.getType());
        warningPopUp.setHeartRateRecommendation(heartRateRange);
        warningPopUp.setRecommendedLabel(getRecommendedHeartRate(warning));
    }

    private int getRecommendedHeartRate(HealthWarning warning) {
        int rate;
        if (warning.getType() == WarningType.Tachy) {
            rate = 220 - applicationStateManager.getCurrentProfile().getAge();
        } else if (warning.getType() == WarningType.Brady) {
            if (applicationStateManager.getCurrentProfile().getAge() >= 18) {
                rate = 50;
            } else {
                rate = 60;
            }
        } else {
            rate = 90;
        }
        return rate;
    }

    private String setRateRange(HealthWarning warning) {
        String heartRateRange;
        if (warning.getType() == WarningType.Tachy) {
            heartRateRange = "Maximum";
        } else if (warning.getType() == WarningType.Brady) {
            heartRateRange = "Minimum";
        } else {
            heartRateRange = "Resting Maximum";
        }
        return heartRateRange;
    }

    @FXML
    void webSearch() {
        HealthWarning warning = (HealthWarning) healthWarningTable.getSelectionModel().getSelectedItem();
        if (warning != null) {
            currentUrl = warning.getUrl();
            engine.load(currentUrl);
        }
    }

    @FXML
    void webViewReturn() {
        engine.load(currentUrl);
    }

    private WebEngine engine;
    private String currentUrl;

    public HealthTabController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    public void initialize() {
        healthWarningTable.setPlaceholder(new Text("No warnings have been detected."));
        healthWarningTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        descColumn.prefWidthProperty().bind(healthWarningTable.widthProperty().divide( 4));



        currentUrl = "https://www.google.co.nz/";
        webBrowser.setZoom(0.9);
        engine = webBrowser.getEngine();
        engine.setUserStyleSheetLocation(App.class.getResource("view/webViewStyle.css").toExternalForm());
        engine.load(currentUrl);
    }

    /**
     *
     */
    public void reloadTab() {
        ObservableList<HealthWarning> warningList = FXCollections.observableArrayList(applicationStateManager.getCurrentProfile().getWarningList());
        dateColumn.setCellValueFactory(new PropertyValueFactory<HealthWarning,LocalDate>("warningDate"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<HealthWarning,String>("typeString"));
        descColumn.setCellValueFactory(new PropertyValueFactory<HealthWarning,String>("description"));

        healthWarningTable.setItems(warningList);

        ScrollBar scrollBarHorizontal = (ScrollBar) healthWarningTable.lookup(".scroll-bar:hotizontal");
        scrollBarHorizontal.setVisible(false);

        currentUrl = "https://www.google.com/";
        engine.load(currentUrl);
    }

    /**
     *
     */
    public void setLabels() {
        ageLabel.setText(String.format("%d", (applicationStateManager.getCurrentProfile().getAge())));
        weightLabel.setText(String.format("%.1f", applicationStateManager.getCurrentProfile().getWeight()) + "kg");
        bmiLabel.setText(String.format("%.2f", applicationStateManager.getCurrentProfile().getBmi()));
    }
}
