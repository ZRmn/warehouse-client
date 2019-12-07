package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Address;
import models.Item;
import models.Order;
import utils.DTO;
import models.*;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrdersAddController
{
    @FXML
    private ComboBox<Box> boxes;

    @FXML
    private TextField count;

    @FXML
    private ComboBox<Address> addresses;

    @FXML
    private ListView<Item> basket;

    @FXML
    private Label boxRequired;

    @FXML
    private Label countRequired;

    @FXML
    private Label addressRequired;

    @FXML
    void onSubmit()
    {
        addressRequired.setVisible(false);

        if(basket.getItems().isEmpty())
        {
            return;
        }

        if(addresses.getSelectionModel().getSelectedItem() == null)
        {
            addressRequired.setVisible(true);
            return;
        }

        Order order = new Order(addresses.getSelectionModel().getSelectedItem(), new ArrayList<>(basket.getItems()));

        DTO.getInstance().getClient().addOrder(order);

        boxes.getScene().getWindow().hide();
    }

    @FXML
    void onAdd()
    {
        boolean hasError = false;

        countRequired.setVisible(false);
        boxRequired.setVisible(false);

        if (count.getText().isEmpty())
        {
            countRequired.setVisible(true);
            hasError = true;
        }

        if (boxes.getSelectionModel().getSelectedItem() == null)
        {
            boxRequired.setVisible(true);
            hasError = true;
        }

        try
        {
            Integer.parseInt(count.getText());
        }
        catch (Exception e)
        {
            hasError = true;
        }

        if (hasError)
        {
            return;
        }

        basket.getItems().add(new Item(boxes.getSelectionModel().getSelectedItem(), Integer.parseInt(count.getText())));
        boxes.getItems().remove(boxes.getSelectionModel().getSelectedItem());

        boxes.getSelectionModel().select(boxes.getSelectionModel().getSelectedIndex() + 1);
    }

    @FXML
    void onDelete()
    {
        if(basket.getSelectionModel().getSelectedItem() != null)
        {
            boxes.getItems().add(basket.getSelectionModel().getSelectedItem().getBox());
            basket.getItems().remove(basket.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void initialize()
    {
        addressRequired.setVisible(false);
        countRequired.setVisible(false);
        boxRequired.setVisible(false);

        basket.getStyleClass().add("customListView");

        List<Box> boxesList = DTO.getInstance().getClient().getBoxes();

        for (Box box : boxesList)
        {
            boxes.getItems().add(box);
        }

        if (!boxes.getItems().isEmpty())
        {
            boxes.getSelectionModel().select(0);
        }

        List<Address> addressesList = DTO.getInstance().getClient().getAddresses();

        for (Address address : addressesList)
        {
            addresses.getItems().add(address);
        }
    }
}
