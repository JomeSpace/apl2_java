package ui;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import sim.dto;

import java.util.List;

public class BarChart {
    private final XYChart.Series<String, Number> ValueSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> LimitSeries = new XYChart.Series<>();
    String type;

    public BarChart(String type) {
        this.type = type;
    }

    public StackPane createStackedBarChart() {
        // X and Y axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(this.type+"ID");
        yAxis.setLabel(this.type+"Value");

        // Create the stackedBarChart
        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.setTitle("Value and Limit of "+this.type);
        stackedBarChart.setAnimated(false);

        //preparing xAxis Data
        ValueSeries.setName(this.type+" Values");
        stackedBarChart.getData().add(ValueSeries);
        LimitSeries.setName(this.type+" Limits");
        stackedBarChart.getData().add(LimitSeries);

        return new StackPane(stackedBarChart);
    }
    public void updateChart(List<dto> buyerData) {
        // Clear previous data
        ValueSeries.getData().clear();
        LimitSeries.getData().clear();

        for (dto dtoBuyer : buyerData) {
            ValueSeries.getData().add(new XYChart.Data<>(dtoBuyer.id(), dtoBuyer.value()));
            LimitSeries.getData().add(new XYChart.Data<>(dtoBuyer.id(), Math.abs(dtoBuyer.limit()-dtoBuyer.value())));
        }
    }
}

