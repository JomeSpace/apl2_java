package userinterface;

import dtos.ParamDTO;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.scenes.ControlMenu;
import userinterface.scenes.LiveChart;

/**
 * Manages the switching between different UI scenes in the application.
 */
public class SceneManager {

    private static Stage primaryStage;

    /**
     * Initializes the SceneManager with the primary stage.
     *
     * @param stage the primary Stage of the application
     */
    public static void init(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Displays the start menu scene.
     */
    public static void showStartMenu() {
        ControlMenu startMenu = new ControlMenu();
        Scene scene = new Scene(startMenu.getView(), 1000, 800);
        if (primaryStage != null) {
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            System.err.println("Primary stage is not initialized.");
        }
    }

    /**
     * Displays the simulation scene with the given simulation parameters.
     *
     * @param configs the parameters to pass to the simulation view
     */
    public static void showSimulation(ParamDTO configs) {
        LiveChart simulationView = new LiveChart();
        Scene scene = new Scene(simulationView.getView(configs), 1000, 800);

        String stylesheet = SceneManager.class.getResource("/style.css").toExternalForm();
        if (scene.getStylesheets().add(stylesheet)) {
            System.out.println("Stylesheet loaded successfully.");
        } else {
            System.out.println("Failed to load stylesheet.");
        }

        if (primaryStage != null) {
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            System.err.println("Primary stage is not initialized.");
        }
    }
}
