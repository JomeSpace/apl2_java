package ui;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import sim.dto;

import java.util.List;
import java.util.Objects;

public class BarChart {
    private final XYChart.Series<String, Number> ValueSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> LimitSeries = new XYChart.Series<>();
    String type;

    public BarChart(String type) {
        if(Objects.equals(type, "buyer") || Objects.equals(type, "seller")){
            this.type = type;
        } else {
            throw new IllegalArgumentException("Invalid type");
        }
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

        //naming the data series
        ValueSeries.setName(this.type+" Values");
        LimitSeries.setName(this.type+" Limits");

        List<XYChart.Series<String, Number>> seriesList;

        if (type.equals("buyer")) {
            seriesList = List.of(ValueSeries,LimitSeries);
        } else {
            seriesList = List.of(LimitSeries, ValueSeries);
        }

        stackedBarChart.getData().addAll(seriesList);

        return new StackPane(stackedBarChart);
    }
    public void updateChart(List<dto> Data) {
        // Clear previous data
        ValueSeries.getData().clear();
        LimitSeries.getData().clear();

        if(type.equals("buyer")){
            for (dto dtoData : Data) {
                ValueSeries.getData().add(new XYChart.Data<>(dtoData.id(), dtoData.value()));
                LimitSeries.getData().add(new XYChart.Data<>(dtoData.id(), Math.abs(dtoData.limit()-dtoData.value())));
            }
        } else {
            for (dto dtoData : Data) {
                LimitSeries.getData().add(new XYChart.Data<>(dtoData.id(), dtoData.limit()));
                ValueSeries.getData().add(new XYChart.Data<>(dtoData.id(), dtoData.value() - dtoData.limit()));
            }
        }

    }
}

