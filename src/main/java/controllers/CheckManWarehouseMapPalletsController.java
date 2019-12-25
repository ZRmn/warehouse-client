package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import models.Box;
import models.Pallet;
import models.Place;

import java.util.ArrayList;
import java.util.List;

public class CheckManWarehouseMapPalletsController
{
    @FXML
    private ListView<Pallet> pallets;

    private Place place;

    public CheckManWarehouseMapPalletsController(Place place)
    {
        this.place = place;
    }

    @FXML
    void initialize()
    {
        pallets.getStyleClass().add("customListView");

        for (Pallet pallet : place.getPallets())
        {
            this.pallets.getItems().add(pallet);
        }
    }
}
