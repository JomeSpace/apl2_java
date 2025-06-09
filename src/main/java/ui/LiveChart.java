package ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import sim.SimManager;

public class LiveChart extends Application {

    private final SimManager simManager = new SimManager(5, 10); // start with 3 sellers & 3 buyers
    private final XYChart.Series<String, Number> buyerBidSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> buyerBidMaxSeries = new XYChart.Series<>();

    @Override
    public void start(Stage primaryStage) {
        simManager.runSim();

        BorderPane root = new BorderPane();
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
        HBox hBox = new HBox(10); // spacing between charts


        Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            chartBuyers.updateChart(simManager.getBuyerData());
            chartSellers.updateChart(simManager.getSellerData());
            statisticLineChart.updateChart(simManager.getBuyerData(), simManager.getSellerData());
        }));
        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.play();


        Scene scene = new Scene(root, 1500, 1000);

        if(scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm())) {
            System.out.println("Stylesheet loaded successfully.");
        } else {
            System.out.println("Failed to load stylesheet.");
        }


        primaryStage.setScene(scene);
        primaryStage.setTitle("Live Buyer Chart");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
