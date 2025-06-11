package ui;

import dto.collection.ParamDTO;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.scenes.ControlMenu;
import ui.scenes.LiveChart;

public class SceneManager {
    private static Stage primaryStage;

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    public static void showStartMenu() {
        ControlMenu startMenu = new ControlMenu();
        Scene scene = new Scene(startMenu.getView(), 1000, 800);
        primaryStage.setScene(scene);
    }

    public static void showSimulation(ParamDTO configs) {
        LiveChart simulationView = new LiveChart();
        Scene scene = new Scene(simulationView.getView(configs), 1000, 800);

        if(scene.getStylesheets().add(SceneManager.class.getResource("/style.css").toExternalForm())) {
            System.out.println("Stylesheet loaded successfully.");
        } else {
            System.out.println("Failed to load stylesheet.");
        }
        primaryStage.setScene(scene);
    }
}

