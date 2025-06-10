package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class mainUI extends Application {
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
