package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Product;
import utils.DTO;
import utils.StageController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckManProductsController
{
    private ObservableList<Product> products;

    @FXML
    private TableView<Product> table;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, Long> codeColumn;

    @FXML
    private TableColumn<Product, String> descriptionColumn;

    @FXML
    private ImageView refresh;

    @FXML
    private ImageView back;


    @FXML
    void onRefresh()
    {
        products.clear();
        products.addAll(DTO.getInstance().getClient().getProducts());
    }

    @FXML
    void onRefreshHovered()
    {
        refresh.setFitWidth(30);
        refresh.setFitHeight(30);
    }

    @FXML
    void onRefreshExited()
    {
        refresh.setFitWidth(25);
        refresh.setFitHeight(25);
    }

    @FXML
    void onBack()
    {
        DTO.getInstance().getStageController().getPrimaryStage().setResizable(false);
        DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.CHECKMAN_MENU);
    }

    @FXML
    void onBackHovered()
    {
        back.setFitWidth(30);
        back.setFitHeight(30);
    }


    @FXML
    void onBackExited()
    {
        back.setFitWidth(25);
        back.setFitHeight(25);
    }

    @FXML
    void onAdd()
    {
        CheckManProductsAddEditController controller = new CheckManProductsAddEditController(null);
        DTO.getInstance().getStageController().setModalScene(StageController.SceneType.CHECKMAN_PRODUCTS_ADD_EDIT,
                controller);
        DTO.getInstance().getStageController().showModal();

        onRefresh();
    }

    @FXML
    void onEdit()
    {
        if (table.getSelectionModel().getSelectedItems() != null)
        {
            CheckManProductsAddEditController controller = new CheckManProductsAddEditController(
                    table.getSelectionModel().getSelectedItems().get(0));
            DTO.getInstance().getStageController().setModalScene(StageController.SceneType.CHECKMAN_PRODUCTS_ADD_EDIT,
                    controller);
            DTO.getInstance().getStageController().showModal();

            onRefresh();
        }
    }

    @FXML
    void onDelete()
    {
        if (table.getSelectionModel().getSelectedItems() != null)
        {
            List<Integer> indexes = new ArrayList<>(table.getSelectionModel().getSelectedIndices());

            Collections.sort(indexes);
            Collections.reverse(indexes);

            for (Integer i : indexes)
            {
                Product product = table.getItems().get(i);
                table.getItems().remove(product);
                DTO.getInstance().getClient().deleteProduct(product);
            }

            onRefresh();
        }
    }

    @FXML
    void initialize()
    {
        refresh.setImage(new Image(getClass().getResource("/resources/images/refresh.png").toString()));
        back.setImage(new Image(getClass().getResource("/resources/images/back.png").toString()));

        products = FXCollections.observableArrayList();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setItems(products);

        idColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<Product, Long>("code"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));

        onRefresh();
    }
}
