package userinterface.scenes;

import dtos.ParamDTO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import simulation.SimManager;
import userinterface.elements.BarChart;
import userinterface.elements.CommandBar;
import userinterface.elements.StatisticLineChart;

/**
 * UI class responsible for rendering and updating
 * live simulation charts and controls.
 */
public class LiveChart {

    // Controls
    private final Button resumepauseButton = new Button("Pause/Resume Simulation");

    // Root container
    private final BorderPane root = new BorderPane();

    // Simulation manager
    private SimManager simManager;

    // Chart update loop
    private Timeline timeline;

    // Chart visualizations
    private BarChart chartBuyers;
    private BarChart chartSellers;
    private StatisticLineChart statisticLineChart;

    /**
     * Initializes and lays out the chart scene with controls and visuals.
     */
    public void start() {
        root.getStyleClass().add("root");

        // Layout grid for charts and controls
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20));

        // Buyer chart
        chartBuyers = new BarChart("buyer");
        Node chartBuyersNode = chartBuyers.createChartPane();
        grid.add(chartBuyersNode, 0, 0);

        // Seller chart
        chartSellers = new BarChart("seller");
        Node chartSellersNode = chartSellers.createChartPane();
        grid.add(chartSellersNode, 0, 1);

        // Statistic line chart (buyers vs sellers)
        statisticLineChart = new StatisticLineChart();
        Node statisticLineChartNode = statisticLineChart.createLineChart();
        grid.add(statisticLineChartNode, 1, 0, 1, 2); // spans two rows

        // Control label
        Label stopLabel = new Label("Controls:");
        stopLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Stop simulation button
        Button stopButton = new Button("Leave Simulation");
        stopButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        stopButton.setOnAction(e -> {
            simManager.killSimThread();
            Platform.exit();
            System.exit(0);
        });

        // Pause/resume simulation button
        resumepauseButton.setOnAction(e -> {
            if (simManager.isPaused()) {
                simManager.resumeThread();
                resumepauseButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            } else {
                simManager.pauseThread();
            }
        });

        // Control box with both buttons
        HBox buttonsBox = new HBox(10, stopButton, resumepauseButton);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox controlBox = new VBox(10, stopLabel, buttonsBox);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.setPadding(new Insets(10));
        controlBox.getStyleClass().add("stop-box");

        stopButton.getStyleClass().add("button");
        resumepauseButton.getStyleClass().add("button");

        // Command bar (e.g., user input control)
        CommandBar commandBar = new CommandBar(() -> simManager.pauseThread());
        Node commandBarNode = commandBar.createCommandBar("Enter Command");

        // Place controls in grid
        grid.add(controlBox, 1, 2);       // Below charts
        grid.add(commandBarNode, 0, 2);   // Left of control box

        // Add entire grid to root
        root.setCenter(grid);
    }

    /**
     * Starts the periodic update loop for charts, synchronized with simulation state.
     */
    public void startChartUpdateLoop() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (!simManager.running) {
                timeline.stop();
                return;
            }

            if (simManager.paused) {
                resumepauseButton.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
                return;
            }

            chartBuyers.updateChart(simManager.getBuyerData());
            chartSellers.updateChart(simManager.getSellerData());
            statisticLineChart.updateChart(simManager.getBuyerData(), simManager.getSellerData());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Initializes the simulation and UI view.
     *
     * @param configs Configuration DTO from JSON
     * @return Parent node representing the full scene
     */
    public Parent getView(ParamDTO configs) {
        simManager = new SimManager(configs);
        simManager.startSimThread();
        startChartUpdateLoop();
        start();
        return root;
    }
}
