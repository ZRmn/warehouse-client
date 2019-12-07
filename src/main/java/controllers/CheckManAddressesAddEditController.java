package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.*;
import utils.DTO;

public class CheckManAddressesAddEditController
{
    private Address address;
    private boolean editing;

    @FXML
    private TextField addressField;

    @FXML
    private Label addressRequired;

    @FXML
    private Label addressTaken;

    @FXML
    private Button submit;

    @FXML
    void onSubmit()
    {
        boolean hasError = false;

        addressRequired.setVisible(false);
        addressTaken.setVisible(false);

        if(addressField.getText().isEmpty())
        {
            addressRequired.setVisible(true);
            hasError = true;
        }

        if (hasError)
        {
            return;
        }

        if(editing)
        {
            address.setAddress(addressField.getText());
            DTO.getInstance().getClient().editAddress(address);
        }
        else
        {
            address = new Address(addressField.getText());

            if(!DTO.getInstance().getClient().addAddress(address))
            {
                addressTaken.setVisible(true);
                return;
            }
        }

        submit.getScene().getWindow().hide();
    }

    public CheckManAddressesAddEditController(Address address)
    {
        this.address = address;
    }

    @FXML
    void initialize()
    {
        addressRequired.setVisible(false);
        addressTaken.setVisible(false);

        if(address != null)
        {
            editing = true;
            addressField.setText(address.getAddress());
            submit.setText("Изменить");
        }
        else
        {
            editing = false;
        }
    }

}
