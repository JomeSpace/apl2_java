package ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sim.Buyer;
import sim.SimManager;

import java.util.List;

public class LiveChart extends Application {

    private final SimManager simManager = new SimManager(10, 8); // start with 3 sellers & 3 buyers
    private final XYChart.Series<String, Number> buyerBidSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> buyerBidMaxSeries = new XYChart.Series<>();

    @Override
    public void start(Stage primaryStage) {
        simManager.runSim();

        BorderPane root = new BorderPane();

        //Buyers Chart
        BarChart chartBuyers = new BarChart("Buyer");
        root.setTop(chartBuyers.createStackedBarChart());

        //Sellers Chart
        BarChart chartSellers = new BarChart("Seller");
        root.setBottom(chartSellers.createStackedBarChart());

        Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            chartBuyers.updateChart(simManager.getBuyerData());
            chartSellers.updateChart(simManager.getSellerData());
        }));
        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.play();




        Scene scene = new Scene(root, 800, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Live Buyer Chart");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
