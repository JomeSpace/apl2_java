package ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import sim.SimManager;

public class LiveChart  {

    BorderPane root = new BorderPane();
    private final SimManager simManager = new SimManager(9, 10); // start with 3 sellers & 3 buyers
    private final XYChart.Series<String, Number> buyerBidSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> buyerBidMaxSeries = new XYChart.Series<>();


    public void start() {


        GridPane grid = new GridPane();
        root.setCenter(grid);
        root.getStyleClass().add("root");

        //Buyers Chart
        BarChart chartBuyers = new BarChart("buyer");
        Node chartBuyersNode = chartBuyers.createStackedBarChart();
        root.setTop(chartBuyersNode);
        root.getStyleClass().add("bar-chart-buyer");
        grid.add(chartBuyersNode, 0, 0);

        //Sellers Chart
        BarChart chartSellers = new BarChart("seller");
        Node chartSellerNode = chartSellers.createStackedBarChart();
        root.setBottom(chartSellerNode);
        root.getStyleClass().add("bar-chart-seller");
        grid.add(chartSellerNode, 0, 1);

        //Statistics Line Chart
        StatisticLineChart statisticLineChart = new StatisticLineChart();
        Node statisticLineChartNode = statisticLineChart.createLineChart();
        root.setRight(statisticLineChartNode);
        grid.add(statisticLineChartNode, 1, 0);

        Button stoppButton = new Button("Stop Simulation");
        stoppButton.setOnAction(e -> {
            simManager.stopSimThread();
            Platform.exit();
        });

        grid.add(stoppButton,1,1);

        Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            chartBuyers.updateChart(simManager.getBuyerData());
            chartSellers.updateChart(simManager.getSellerData());
            statisticLineChart.updateChart(simManager.getBuyerData(), simManager.getSellerData());
        }));
        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.play();


        HBox charts = new HBox(10, grid);
        charts.setPadding(new Insets(20));
        root.setCenter(charts);

        simManager.startSimThread();
    }
    public Parent getView() {
        start();
        return root;
    }
}
