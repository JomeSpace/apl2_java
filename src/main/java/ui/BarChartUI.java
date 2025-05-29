package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BarChartUI extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("JavaFX Bar Chart Example");

        // Define X and Y axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");

        // Create the BarChart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Sample Data");

        // Add data
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("2025");

        series1.getData().add(new XYChart.Data<>("A", 20));
        series1.getData().add(new XYChart.Data<>("B", 34));
        series1.getData().add(new XYChart.Data<>("C", 15));
        series1.getData().add(new XYChart.Data<>("D", 60));
        series1.getData().add(new XYChart.Data<>("E", 60));

        barChart.getData().add(series1);

        // Add to layout and scene
        VBox vbox = new VBox(barChart);
        Scene scene = new Scene(vbox, 800, 600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
