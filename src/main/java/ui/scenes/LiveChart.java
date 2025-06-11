package ui.scenes;

import dto.collection.ParamDTO;
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
import sim.SimManager;
import ui.elements.BarChart;
import ui.elements.CommandBar;
import ui.elements.StatisticLineChart;

public class LiveChart {
    //global Button for pause/resume
    Button resumepauseButton = new Button("Pause/Resume Simulation");;

    BorderPane root = new BorderPane();
    private SimManager simManager;
    Timeline timeline1;
    BarChart chartBuyers;
    BarChart chartSellers;
    StatisticLineChart statisticLineChart;

    public void start() {
        root.getStyleClass().add("root");

        // Grid for charts and controls
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20));

        // Buyer Chart
        chartBuyers = new BarChart("buyer");
        Node chartBuyersNode = chartBuyers.createStackedBarChart();
        grid.add(chartBuyersNode, 0, 0);

        // Seller Chart
        chartSellers = new BarChart("seller");
        Node chartSellerNode = chartSellers.createStackedBarChart();
        grid.add(chartSellerNode, 0, 1);

        // Statistic Line Chart
        statisticLineChart = new StatisticLineChart();
        Node statisticLineChartNode = statisticLineChart.createLineChart();
        grid.add(statisticLineChartNode, 1, 0, 1, 2); // spans two rows

        // Label above stop button
        Label stopLabel = new Label("Controls:");
        stopLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Stop Button
        Button stopButton = new Button("leave Simulation");
        stopButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        stopButton.setOnAction(e -> {
            simManager.killSimThread();
            Platform.exit();
            System.exit(0);
        });

        // Pause/Resume Button
        resumepauseButton.setOnAction(e -> {
            if (simManager.isPaused()) {
                simManager.resumeThread();
                resumepauseButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            } else {
                simManager.pauseThread();
            }
        });

        // Set style for pause/resume Box
        HBox ButtonsBox = new HBox(10, stopButton, resumepauseButton);
        ButtonsBox.setAlignment(Pos.CENTER);

        // VBox for label + button
        VBox ControlBox = new VBox(10, stopLabel, ButtonsBox);
        ControlBox.setAlignment(Pos.CENTER);
        ControlBox.setPadding(new Insets(10));

        // Add to grid in bottom right
        grid.add(ControlBox, 1, 2); // new row below the charts
        // css styling
        ControlBox.getStyleClass().add("stop-box");
        stopButton.getStyleClass().add("button");
        resumepauseButton.getStyleClass().add("button");

        // Command Bar
        CommandBar commandBar = new CommandBar(() -> simManager.pauseThread());
        Node commandBarNode = commandBar.createCommandBar("Enter Command");
        grid.add(commandBarNode,0,2);

        // Set grid as center of root pane
        root.setCenter(grid);
    }


    public void startChartUpdateLoop() {
        timeline1 = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (!simManager.running) {
                timeline1.stop(); // Stop if simulation is completely terminated
                return;
            }

            if (simManager.paused) {
                resumepauseButton.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
                // Skip updates while paused
                return;
            }

            // Regular update logic
            chartBuyers.updateChart(simManager.getBuyerData());
            chartSellers.updateChart(simManager.getSellerData());
            statisticLineChart.updateChart(simManager.getBuyerData(), simManager.getSellerData());
        }));
        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.play();
    }
    public Parent getView(ParamDTO configs) {
        simManager = new SimManager(configs);
        simManager.startSimThread();
        startChartUpdateLoop();
        start();
        return root;
    }
}
