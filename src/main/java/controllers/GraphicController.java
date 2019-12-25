package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import models.Place;
import utils.DTO;

import java.util.List;

public class GraphicController
{
    @FXML
    private BarChart lineChart;

    @FXML
    private PieChart pieChart;

    @FXML
    void initialize()
    {
        pieChart.setTitle("Заполненность склада");
        lineChart.setTitle("Заполненность стеллажей");

        List<Place> places = DTO.getInstance().getClient().getPlaces();

        int zapO = 0;

        for(Place place : places)
        {
            float zap = (place.getFullness() / (float) place.getCapacity())*100;

            zapO += zap;


            XYChart.Series dataSeries = new XYChart.Series();
            dataSeries.setName(place.getPosition());
            dataSeries.getData().add(new XYChart.Data("", zap));
            lineChart.getData().add(dataSeries);


        }

        PieChart.Data pieData = new PieChart.Data("Занято", zapO);
        PieChart.Data pieData2 = new PieChart.Data("Свободно", 100 - zapO);
pieChart.getData().add(pieData);
pieChart.getData().add(pieData2);

    }
}
