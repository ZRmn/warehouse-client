package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.*;
import utils.DTO;

import java.util.List;

public class CheckManBoxesAddEditController
{
    private Box box;
    private boolean editing;

    @FXML
    private ComboBox<Product> products;

    @FXML
    private TextField count;

    @FXML
    private Label countRequired;
    @FXML
    private Label productRequired;

    @FXML
    private Button submit;

    @FXML
    void onSubmit()
    {
        boolean hasError = false;

        countRequired.setVisible(false);
        productRequired.setVisible(false);

        if(count.getText().isEmpty())
        {
            countRequired.setVisible(true);
            hasError = true;
        }

        if(products.getSelectionModel().getSelectedItem() == null)
        {
            productRequired.setVisible(true);
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

        if(editing)
        {
            box.setCount(Integer.parseInt(count.getText()));
            box.setProduct(products.getSelectionModel().getSelectedItem());

            DTO.getInstance().getClient().editBox(box);
        }
        else
        {
            box = new Box(Integer.parseInt(count.getText()), products.getSelectionModel().getSelectedItem());
            DTO.getInstance().getClient().addBox(box);
        }

        submit.getScene().getWindow().hide();
    }

    public CheckManBoxesAddEditController(Box box)
    {
        this.box = box;
    }

    @FXML
    void initialize()
    {
        countRequired.setVisible(false);
        productRequired.setVisible(false);

        List<Product> productsList = DTO.getInstance().getClient().getProducts();

        for(Product product : productsList)
        {
            products.getItems().add(product);
        }

        if(box != null)
        {
            editing = true;
            count.setText(String.valueOf(box.getCount()));
            if(!products.getItems().isEmpty())
            {
                products.getSelectionModel().select(box.getProduct());
            }
            submit.setText("Изменить");
        }
        else
        {
            editing = false;
            if(!products.getItems().isEmpty())
            {
                products.getSelectionModel().select(0);
            }
        }
    }
}
