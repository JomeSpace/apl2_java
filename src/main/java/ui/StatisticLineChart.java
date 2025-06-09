package ui;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import dto.collection.dto;

import java.util.List;

public class StatisticLineChart {
    private final XYChart.Series<String, Number> SellerAverageList = new XYChart.Series<>();
    private final XYChart.Series<String, Number> BuyerAverageList = new XYChart.Series<>();
    Integer time;

    public StatisticLineChart() {
        SellerAverageList.setName("Seller Average");
        BuyerAverageList.setName("Buyer Average");
        this.time = 0;
    }
    public StackPane createLineChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Average Value");

        LineChart<String,Number> statisticLineChart = new LineChart<>(xAxis,yAxis);
        statisticLineChart.setTitle("Line Chart Sample");
        statisticLineChart.setAnimated(false);

        // Add series to the chart
        List<XYChart.Series<String, Number>> seriesList = List.of(SellerAverageList, BuyerAverageList);

        statisticLineChart.getData().addAll(seriesList);

        return new StackPane(statisticLineChart);
    }
    public void updateChart(List<dto> BuyerAverageData,List<dto> SellerAverageData) {
        // Clear previous data
        //BuyerAverage.getData().clear();
        //SellerAverage.getData().clear();
        Double sum1 = 0.0;
        Double sum2 = 0.0;
        int count1 = 0;
        int count2 = 0;
        for(int i = 0; i<BuyerAverageData.size(); i++) {
            if(BuyerAverageData.get(i).status()){ sum1 += BuyerAverageData.get(i).value();
            count1++;
            }
        }
        for(int i = 0; i<SellerAverageData.size(); i++) {
            if(SellerAverageData.get(i).status()){ sum2 += SellerAverageData.get(i).value();
            count2++;
            }
        }
        double BuyersAverage = sum1 / count1;
        double SellersAverage = sum2 / count2;

        BuyerAverageList.getData().add(new XYChart.Data<>(this.time.toString(),BuyersAverage));
        SellerAverageList.getData().add(new XYChart.Data<>(this.time.toString(), SellersAverage));
        time++;
    }
}
