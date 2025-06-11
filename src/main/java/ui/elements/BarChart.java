package ui.elements;

import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import dto.collection.DTO;

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
    public void updateChart(List<DTO> Data) {
        // Clear previous data
        ValueSeries.getData().clear();
        LimitSeries.getData().clear();


        for (DTO datum : Data) {
            XYChart.Data<String, Number> valueData;
            XYChart.Data<String, Number> limitData;
            if (type.equals("buyer")) {
                valueData = new XYChart.Data<>(
                        datum.id() + " " + datum.status(),
                        datum.value()
                );
                limitData = new XYChart.Data<>(
                        datum.id() + " " + datum.status(),
                        datum.limit() - datum.value()
                );

                ValueSeries.getData().add(valueData);
                LimitSeries.getData().add(limitData);
            } else {
                valueData = new XYChart.Data<>(
                        datum.id() + " " + datum.status(),
                        datum.value() - datum.limit()
                );
                limitData = new XYChart.Data<>(
                        datum.id() + " " + datum.status(),
                        datum.limit()
                );
                LimitSeries.getData().add(limitData);
                ValueSeries.getData().add(valueData);
            }
            Node limitNode = limitData.getNode();
            Node valueNode = valueData.getNode();
            if (limitNode != null && !datum.status()) {
                limitNode.setStyle("-fx-bar-fill: #464545;");
            }
            if (valueNode != null && !datum.status()) {
                valueNode.setStyle("-fx-bar-fill: #464545;");
            }
        }
        }
    }
