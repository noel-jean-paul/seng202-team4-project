package seng202.team4.controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScreenStateManagerTest {
    ScreenStateManager screenStateManager;

    // Commented out because they do not pass on the deployment server.

//    @Before
//    public void setUp() {
//        screenStateManager = new ScreenStateManager(new Scene(new Group(), 100, 100));
//    }
//
//    @After
//    public void tearDown() {
//        screenStateManager = null;
//    }
//
//    @Test
//    public void addScreenTest() {
//        Pane testPane1 = new Pane();
//        screenStateManager.addScreen("testPane", testPane1);
//
//        assertTrue(screenStateManager.containsScreen("testPane"));
//    }
//
//    @Test
//    public void removeScreenTest() {
//        Pane testPane = new Pane();
//        screenStateManager.addScreen("testPane", testPane);
//
//        screenStateManager.removeScreen("testPane");
//        assertFalse(screenStateManager.containsScreen("testPane"));
//    }
//
//    @Test
//    public void containsScreenByNameTest() {
//        Pane testPane = new Pane();
//        assertFalse(screenStateManager.containsScreen("testPane"));
//
//        screenStateManager.addScreen("testPane", testPane);
//        assertTrue(screenStateManager.containsScreen("testPane"));
//
//    }
//
//    @Test
//    public void containsScreenByScreenTest() {
//        Pane testPane = new Pane();
//        assertFalse(screenStateManager.containsScreen(testPane));
//
//        screenStateManager.addScreen("testPane", testPane);
//        assertTrue(screenStateManager.containsScreen(testPane));
//
//    }
}
