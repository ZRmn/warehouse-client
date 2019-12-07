package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import models.Product;
import utils.DTO;

public class CheckManProductsAddEditController
{
    private Product product;
    private boolean editing;

    @FXML
    private TextArea description;

    @FXML
    private Label descriptionRequired;

    @FXML
    private Button submit;

    @FXML
    void onSubmit()
    {
        boolean hasError = false;

        descriptionRequired.setVisible(false);

        if(description.getText().isEmpty())
        {
            descriptionRequired.setVisible(true);
            hasError = true;
        }

        if (hasError)
        {
            return;
        }

        if(editing)
        {
            product.setDescription(description.getText());
            product.setCode(product.hash());
            DTO.getInstance().getClient().editProduct(product);
        }
        else
        {
            product = new Product(description.getText());
            DTO.getInstance().getClient().addProduct(product);
        }

        submit.getScene().getWindow().hide();
    }

    public CheckManProductsAddEditController(Product product)
    {
        this.product = product;
    }

    @FXML
    void initialize()
    {
        descriptionRequired.setVisible(false);

        description.setWrapText(true);

        if(product != null)
        {
            editing = true;
            description.setText(product.getDescription());
            submit.setText("Изменить");
        }
        else
        {
            editing = false;
        }
    }

}
