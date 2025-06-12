package userinterface.elements;

import dtos.AgentDTO;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.Objects;

/**
 * A reusable stacked bar chart for displaying values and limits of buyers or sellers.
 * The chart shows two series: actual values and corresponding limits.
 */
public class BarChart {

    private final XYChart.Series<String, Number> valueSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> limitSeries = new XYChart.Series<>();
    private final String agentType;

    /**
     * Constructs a BarChart for the specified agent type.
     *
     * @param agentType either "buyer" or "seller"
     * @throws IllegalArgumentException if agentType is not "buyer" or "seller"
     */
    public BarChart(String agentType) {
        if (Objects.equals(agentType, "buyer") || Objects.equals(agentType, "seller")) {
            this.agentType = agentType;
        } else {
            throw new IllegalArgumentException("Invalid agent type: must be 'buyer' or 'seller'");
        }
    }

    /**
     * Creates and returns a StackPane containing the configured StackedBarChart.
     *
     * @return a StackPane with the stacked bar chart node
     */
    public StackPane createChartPane() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(agentType.substring(0, 1).toUpperCase() + agentType.substring(1) + " ID");
        yAxis.setLabel(agentType.substring(0, 1).toUpperCase() + agentType.substring(1) + " Value");

        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.setTitle(agentType.substring(0, 1).toUpperCase() + agentType.substring(1) + " Values and Limits");
        stackedBarChart.setAnimated(false);

        valueSeries.setName(agentType.substring(0, 1).toUpperCase() + agentType.substring(1) + " Values");
        limitSeries.setName(agentType.substring(0, 1).toUpperCase() + agentType.substring(1) + " Limits");

        if (agentType.equals("buyer")) {
            stackedBarChart.getData().addAll(valueSeries, limitSeries);
        } else {
            // For sellers, add limitSeries first for visual preference
            stackedBarChart.getData().addAll(limitSeries, valueSeries);
        }

        return new StackPane(stackedBarChart);
    }

    /**
     * Updates the chart data with a list of DTO objects.
     * Each DTO represents an agent with its value, limit, id, and status.
     * Bars representing inactive agents are visually dimmed.
     *
     * @param data List of DTOs containing the data to display
     */
    public void updateChart(List<AgentDTO> data) {
        valueSeries.getData().clear();
        limitSeries.getData().clear();

        for (AgentDTO dto : data) {
            String label = dto.id() + " " + dto.status();
            XYChart.Data<String, Number> valueData;
            XYChart.Data<String, Number> limitData;

            if (agentType.equals("buyer")) {
                valueData = new XYChart.Data<>(label, dto.value());
                limitData = new XYChart.Data<>(label, dto.limit() - dto.value());
            } else {
                valueData = new XYChart.Data<>(label, dto.value() - dto.limit());
                limitData = new XYChart.Data<>(label, dto.limit());
            }

            valueSeries.getData().add(valueData);
            limitSeries.getData().add(limitData);

            // Dim bars for inactive agents
            Node valueNode = valueData.getNode();
            Node limitNode = limitData.getNode();

            if (valueNode != null && !dto.status()) {
                valueNode.setStyle("-fx-bar-fill: #464545;");
            }
            if (limitNode != null && !dto.status()) {
                limitNode.setStyle("-fx-bar-fill: #464545;");
            }
        }
    }
}
