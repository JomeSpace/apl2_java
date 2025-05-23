package ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class BarChartExample extends JFrame {

    public BarChartExample() {
        setTitle("Bar Chart Example");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10, "Sales", "January");
        dataset.addValue(15, "Sales", "February");
        dataset.addValue(12, "Sales", "March");
        dataset.addValue(18, "Sales", "April");

        // Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Sales",      // Chart title
                "Month",              // Category axis label
                "Amount",             // Value axis label
                dataset
        );

        // Add chart to panel
        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BarChartExample example1 = new BarChartExample();
            BarChartExample example2 = new BarChartExample();
            example1.setVisible(true);
            example2.setVisible(true);
        });
    }
}
