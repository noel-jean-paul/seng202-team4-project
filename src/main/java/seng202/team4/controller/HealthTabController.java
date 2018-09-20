package seng202.team4.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
        currentUrl = warning.getUrl();
        engine.load(currentUrl);
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
        currentUrl = "https://www.google.co.nz/";
        webBrowser.setZoom(0.9);
        engine = webBrowser.getEngine();
        engine.load(currentUrl);
    }

    /**
     *
     */
    public void updateTable() {
        ObservableList<HealthWarning> warningList = FXCollections.observableArrayList(applicationStateManager.getCurrentProfile().getWarningList());
        dateColumn.setCellValueFactory(new PropertyValueFactory<HealthWarning,LocalDate>("warningDate"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<HealthWarning,String>("typeString"));
        descColumn.setCellValueFactory(new PropertyValueFactory<HealthWarning,String>("description"));

        healthWarningTable.setItems(warningList);

        ScrollBar scrollBarHorizontal = (ScrollBar) healthWarningTable.lookup(".scroll-bar:hotizontal");
        scrollBarHorizontal.setVisible(false);
    }

    /**
     *
     */
    public void setLabels() {
        System.out.println("Let's do it");
        ageLabel.setText(String.format("%d", (applicationStateManager.getCurrentProfile().getAge())));
        System.out.println(applicationStateManager.getCurrentProfile().getAge());
        System.out.println(applicationStateManager.getCurrentProfile().getWeight());
        System.out.println(applicationStateManager.getCurrentProfile().getBmi());
        weightLabel.setText(String.format("%.1f", applicationStateManager.getCurrentProfile().getWeight()) + "kg");
        bmiLabel.setText(String.format("%.2f", applicationStateManager.getCurrentProfile().getBmi()));
    }
}
