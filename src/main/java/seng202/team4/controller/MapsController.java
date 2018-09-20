package seng202.team4.controller;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.collections.FXCollections;
import seng202.team4.App;
import seng202.team4.model.data.*;

import java.util.ArrayList;

public class MapsController extends Controller {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private WebView activityMap;

    private WebEngine webEngine;


    public MapsController(ApplicationStateManager applicationStateManager) {
        super(applicationStateManager);
    }


    @FXML
    public void initialize() {
        //Initialization code goes here (called just after the pane has been loaded)
//        ObservableList<Activity> activityList = FXCollections.observableArrayList();
//        currentUser = applicationStateManager.getCurrentProfile();
//        activities.addAll(currentUser.getActivityList());
//        System.out.println(activities.size());
//        for (Activity activity : activities) {
//            activityList.add(activity);
//        }
//        activityCombo.setItems(activityList);
//        activityCombo.getSelectionModel().select(0);
        webEngine = activityMap.getEngine();
        webEngine.load(App.class.getResource("view/map.html").toExternalForm());
    }

    /**
     * Generates a path/route
     * @param activity the activity for which a polyline is to be calculate on the map
     * @return a route(list of positions with lat/long as doubles)
     */
    private Route generateRoute(Activity activity) {
        ArrayList<Position> positionList = new ArrayList<Position>();
        for (DataRow data : activity.getRawData()) {
            positionList.add(new Position((data.getLatitude()), (data.getLongitude())));
        }
        Route newRoute = new Route((positionList));
        return newRoute;
    }

    public void initMap(Activity activity) {
        Route newRoute = generateRoute(activity);
        displayRoute(newRoute);
    }

    private void displayRoute(Route newRoute) {
        String scriptToExecute = "displayRoute(" + newRoute.toJSONArray() + ");";
        webEngine.executeScript(scriptToExecute);
    }

    @FXML
    public void close() {
        applicationStateManager.closePopUP(mainPane);
    }
}
