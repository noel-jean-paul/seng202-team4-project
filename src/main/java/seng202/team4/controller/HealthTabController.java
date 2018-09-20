package seng202.team4.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HealthTabController extends Controller {

    @FXML
    private TableView<?> healthWarningTable;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> descColumn;

    @FXML
    private WebView webBrowser;

    @FXML
    private Button viewInfoButton;

    @FXML
    private Button returnButton;


    @FXML
    private TextArea healthTabTextBox;

    @FXML
    void loadInformation() {
    }


    @FXML
    void webViewReturn() {

    }

    private WebEngine engine;

    public HealthTabController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }

    @FXML
    public void initialize() {
        engine = webBrowser.getEngine();
        engine.load("https://www.google.co.nz");
    }
}
