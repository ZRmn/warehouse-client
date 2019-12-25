package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import models.Pallet;
import models.Place;
import utils.DTO;

public class CheckManPlacesPalletsDeleteController
{
    @FXML
    private ListView<Pallet> pallets;

    private Place place;

    public CheckManPlacesPalletsDeleteController(Place place)
    {
        this.place = place;
    }

    @FXML
    void onDelete()
    {
        if (pallets.getSelectionModel().getSelectedItem() != null)
        {
            place.getPallets().remove(pallets.getSelectionModel().getSelectedItem());
            pallets.getItems().remove(pallets.getSelectionModel().getSelectedItem());
            DTO.getInstance().getClient().deletePlace(place);
            DTO.getInstance().getClient().addPlace(place);
        }
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
