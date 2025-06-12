package userinterface.elements;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import dtos.AgentDTO;

import java.util.List;

/**
 * A reusable line chart to visualize the average values of buyers and sellers over time.
 */
public class StatisticLineChart {

    private static final String SELLER_SERIES_NAME = "Seller Average Ask";
    private static final String BUYER_SERIES_NAME = "Buyer Average Bid";

    private final XYChart.Series<String, Number> sellerAverageSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> buyerAverageSeries = new XYChart.Series<>();

    private int time;

    /**
     * Constructs a StatisticLineChart and initializes series.
     */
    public StatisticLineChart() {
        this.sellerAverageSeries.setName(SELLER_SERIES_NAME);
        this.buyerAverageSeries.setName(BUYER_SERIES_NAME);
        this.time = 0;
    }

    /**
     * Creates and returns a StackPane containing the configured LineChart.
     *
     * @return a StackPane with the line chart node
     */
    public StackPane createLineChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Time");
        yAxis.setLabel("Average Value");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Average Value Line Chart");
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);

        lineChart.getData().addAll(sellerAverageSeries, buyerAverageSeries);

        return new StackPane(lineChart);
    }

    /**
     * Updates the chart with new average data from buyers and sellers.
     *
     * @param buyerData  list of buyer DTOs for this time step
     * @param sellerData list of seller DTOs for this time step
     */
    public void updateChart(List<AgentDTO> buyerData, List<AgentDTO> sellerData) {
        double buyerSum = 0.0;
        int buyerCount = 0;
        for (AgentDTO dto : buyerData) {
            if (dto.status()) {
                buyerSum += dto.value();
                buyerCount++;
            }
        }

        double sellerSum = 0.0;
        int sellerCount = 0;
        for (AgentDTO dto : sellerData) {
            if (dto.status()) {
                sellerSum += dto.value();
                sellerCount++;
            }
        }

        if (buyerCount > 0 && sellerCount > 0) {
            double buyerAverage = buyerSum / buyerCount;
            double sellerAverage = sellerSum / sellerCount;

            String timeLabel = Integer.toString(time);
            buyerAverageSeries.getData().add(new XYChart.Data<>(timeLabel, buyerAverage));
            sellerAverageSeries.getData().add(new XYChart.Data<>(timeLabel, sellerAverage));

            time++;
        }
    }
}
