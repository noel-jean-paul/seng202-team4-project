package seng202.team4.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team4.GuiUtilities;
import seng202.team4.model.data.enums.WarningType;
import seng202.team4.model.utilities.HealthWarning;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

/**
 * Controller for the health tab.
 */
public class HealthTabController extends Controller {

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
    private Button backButton;

    @FXML
    private Button forwardButton;

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
    private GridPane imagePane;

    @FXML
    void loadInformation() {
        HealthWarning warning = (HealthWarning) healthWarningTable.getSelectionModel().getSelectedItem();
        if (warning != null) {
            WarningDescriptionPopUpController warningPopUp = new WarningDescriptionPopUpController(applicationStateManager);
            Pane popUp = GuiUtilities.loadPane("HealthPopUpScreen.fxml", warningPopUp);
            setUpPopUpLabels(warningPopUp, warning);
            applicationStateManager.displayPopUp(popUp);
        }
    }

    /**
     * Populates all of the labels in the popUp window.
     * @param warningPopUp the popUp window controller being populated.
     * @param warning the warning who's values are being user to populate the PopUp.
     */
    private void setUpPopUpLabels(WarningDescriptionPopUpController warningPopUp, HealthWarning warning) {
        String heartRateRange = setRateRange(warning);
        warningPopUp.setPopUpTitle(warning.getTypeString());
        warningPopUp.setActivityNameLabel(warning.getActivity().getName());
        warningPopUp.setAverageLabel(warning.getAvgHeartRate());
        warningPopUp.setMinLabel(warning.getMinHeartRate());
        warningPopUp.setMaxLabel(warning.getMaxHeartRate());
        warningPopUp.setDescriptionText(warning.getType());
        warningPopUp.setHeartRateRecommendation(heartRateRange);
        warningPopUp.setRecommendedLabel(getRecommendedHeartRate(warning));
    }

    /**
     * Retrieves the recommended heart rate according to the warning being displayed.
     * @param warning the warning being displayed.
     * @return the recommended heart rate which the user was in excess of.
     */
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

    /**
     * Looking at the warning type of the given health warning, gives a string correlating which bound the user
     * was in excess of - Maximum, Minimum.
     * @param warning the warning being displayed.
     * @return the range bound in violation.
     */
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

    /**
     * When the button is pressed, the warning's URL is used to perform a google search on the warning in the webView
     */
    @FXML
    void webSearch() {
        HealthWarning warning = (HealthWarning) healthWarningTable.getSelectionModel().getSelectedItem();
        if (warning != null) {
            currentUrl = warning.getUrl();
            engine.load(currentUrl);
        }
    }

    /**
     * Returns the webView to the most recent warning URL, if none was given, returns the webView back to google.com
     */
    @FXML
    void webViewReturn() {
        engine.load(currentUrl);
    }


    @FXML
    void webViewForward() {
        Platform.runLater(() -> {
            engine.executeScript("history.forward()");
        });
    }

    @FXML
    void webViewBack() {
        Platform.runLater(() -> {
            engine.executeScript("history.back()");
        });
    }

    private WebEngine engine;
    private String currentUrl;
    private boolean internetConnected;


    /**
     * The constructor for the health tab.
     * @param applicationStateManager the application state manager of the application.
     */
    HealthTabController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    /**
     * Initialises the HealthTab to its initial state.
     */
    @FXML
    public void initialize() {
        healthWarningTable.setPlaceholder(new Text("No warnings have been detected."));
        healthWarningTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        webBrowser.setZoom(0.8);
        currentUrl = "https://www.google.co.nz/";
        engine = webBrowser.getEngine();
        imagePane.setStyle("-fx-background-color: white");

        boolean internetConnected = verifyInternetConnection();
        if (internetConnected) {
            engine.load(currentUrl);
        }
    }

    private String getBMIStatusString() {
        double BMI = applicationStateManager.getCurrentProfile().getBmi();
        String status;
        if (BMI < 18.5) {
            status = "Underweight";
        } else if (18.5 <= BMI && BMI < 25) {
            status = "Normal Weight";
        } else if (25 <= BMI && BMI < 30) {
            status = "Overweight";
        } else {
            status = "Obese";
        }
        return  status;
    }

    private boolean verifyInternetConnection() {
        try{
            Runtime run=Runtime.getRuntime();
            Process proc = run.exec("ping www.google.com");
            boolean connected = proc.waitFor(2, TimeUnit.SECONDS);
            return connected;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Populates the table and sets the web view back to Google.com when the health tab is selected.
     */
    public void reloadTab() {
        ObservableList<HealthWarning> warningList = FXCollections.observableArrayList(
                applicationStateManager.getCurrentProfile().getWarningList());
        dateColumn.setCellValueFactory(new PropertyValueFactory<HealthWarning,LocalDate>("warningDate"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<HealthWarning,String>("typeString"));
        descColumn.setCellValueFactory(new PropertyValueFactory<HealthWarning,String>("description"));

        healthWarningTable.setItems(warningList);

        ScrollBar scrollBarHorizontal = (ScrollBar) healthWarningTable.lookup(".scroll-bar:hotizontal");
        scrollBarHorizontal.setVisible(false);


        currentUrl = "https://www.google.com/";
        engine.load(currentUrl);
        //noInternetPane.toFront();
    }

    /**
     * Sets the top metric labels in the health tab.
     */
    public void setLabels() {
        ageLabel.setText(String.format("%d", (applicationStateManager.getCurrentProfile().getAge())));
        weightLabel.setText(String.format("%.1f", applicationStateManager.getCurrentProfile().getWeight()) + "kg");
        bmiLabel.setText(String.format("%.2f    (%s)", applicationStateManager.getCurrentProfile().getBmi(), getBMIStatusString()));
    }
}
