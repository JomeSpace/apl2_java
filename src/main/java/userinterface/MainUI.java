package userinterface;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * View-Schicht: User Interface containing all the formals for the javafx ui
 */
public class MainUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        SceneManager.init(primaryStage);
        SceneManager.showStartMenu();
        primaryStage.setTitle("Simulation Dashboard");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
