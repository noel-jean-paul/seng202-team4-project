//package seng202.team4.controller;
//
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//import org.junit.*;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//public class ScreenStateManagerTest {
//
//    ApplicationStateManager applicationStateManager;
//
//     //Commented out because they do not pass on the deployment server.
//
//    @Before
//    public void setUp() {
//        applicationStateManager = new ApplicationStateManager(new Scene(new Group(), 100, 100), new Stage());
//    }
//
//    @After
//    public void tearDown() {
//        applicationStateManager = null;
//    }
//
//    @Test
//    public void addScreenTest() {
//        Pane testPane1 = new Pane();
//        applicationStateManager.addScreen("testPane", testPane1);
//
//        assertTrue(applicationStateManager.containsScreen("testPane"));
//    }
//
//    @Test
//    public void removeScreenTest() {
//        Pane testPane = new Pane();
//        applicationStateManager.addScreen("testPane", testPane);
//
//        applicationStateManager.removeScreen("testPane");
//        assertFalse(applicationStateManager.containsScreen("testPane"));
//    }
//
//    @Test
//    public void containsScreenByNameTest() {
//        Pane testPane = new Pane();
//        assertFalse(applicationStateManager.containsScreen("testPane"));
//
//        applicationStateManager.addScreen("testPane", testPane);
//        assertTrue(applicationStateManager.containsScreen("testPane"));
//
//    }
//
//    @Test
//    public void containsScreenByScreenTest() {
//        Pane testPane = new Pane();
//        assertFalse(applicationStateManager.containsScreen(testPane));
//
//        applicationStateManager.addScreen("testPane", testPane);
//        assertTrue(applicationStateManager.containsScreen(testPane));
//
//    }
//}
