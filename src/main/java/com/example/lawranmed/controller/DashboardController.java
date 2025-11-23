package com.example.lawranmed.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class DashboardController {

    @FXML
    private BarChart<String, Number> appointmentsChart;

    @FXML
    public void initialize() {
        // Populate the bar chart with some static data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Appointments");
        series.getData().add(new XYChart.Data<>("Jan", 25));
        series.getData().add(new XYChart.Data<>("Feb", 45));
        series.getData().add(new XYChart.Data<>("Mar", 60));
        series.getData().add(new XYChart.Data<>("Apr", 80));
        series.getData().add(new XYChart.Data<>("May", 100));
        series.getData().add(new XYChart.Data<>("Jun", 90));

        appointmentsChart.getData().add(series);
    }
}
