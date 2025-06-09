package ui;

import javafx.application.Platform;
import javafx.scene.Node;
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


            for (int i = 0; i < Data.size(); i++) {
                XYChart.Data<String, Number> valueData;
                XYChart.Data<String, Number> limitData;
                if(type.equals("buyer")){
                    valueData = new XYChart.Data<>(
                            Data.get(i).id() + " " + Data.get(i).status(),
                            Data.get(i).value()
                    );
                    limitData = new XYChart.Data<>(
                            Data.get(i).id() + " " + Data.get(i).status(),
                            Data.get(i).limit()- Data.get(i).value()
                    );

                    ValueSeries.getData().add(valueData);
                    LimitSeries.getData().add(limitData);
                }else {
                    valueData = new XYChart.Data<>(
                            Data.get(i).id() + " " + Data.get(i).status(),
                            Data.get(i).value()- Data.get(i).limit()
                    );
                    limitData = new XYChart.Data<>(
                            Data.get(i).id() + " " + Data.get(i).status(),
                            Data.get(i).limit()
                    );
                    LimitSeries.getData().add(limitData);
                    ValueSeries.getData().add(valueData);
                }
                Node limitNode = limitData.getNode();
                Node valueNode = valueData.getNode();
                if (limitNode != null && !Data.get(i).status()) {
                    limitNode.setStyle("-fx-bar-fill: #464545;");
                }
                if (valueNode != null && !Data.get(i).status()) {
                    valueNode.setStyle("-fx-bar-fill: #464545;");
                }
            }
        }
    }
