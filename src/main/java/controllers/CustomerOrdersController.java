package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import models.Address;
import models.Item;
import models.Order;
import utils.DTO;
import utils.StageController;

public class CustomerOrdersController
{
    private ObservableList<Order> orders;

    @FXML
    private TableView<Order> table;

    @FXML
    private TableColumn<Order, Integer> idColumn;

    @FXML
    private TableColumn<Order, String> itemsColumn;

    @FXML
    private TableColumn<Order, Address> addressColumn;

    @FXML
    private ImageView refresh;

    @FXML
    private ImageView back;


    @FXML
    void onRefresh()
    {
        orders.clear();
        orders.addAll(DTO.getInstance().getClient().getOrders());
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
        DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.SIGN_IN);
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
        CustomerOrdersAddController controller = new CustomerOrdersAddController();
        DTO.getInstance().getStageController().setModalScene(StageController.SceneType.CUSTOMER_ORDERS_ADD,
                controller);
        DTO.getInstance().getStageController().showModal();

        onRefresh();
    }

    @FXML
    void initialize()
    {
        refresh.setImage(new Image(getClass().getResource("/resources/images/refresh.png").toString()));
        back.setImage(new Image(getClass().getResource("/resources/images/back.png").toString()));

        orders = FXCollections.observableArrayList();
        table.setItems(orders);
        table.setPlaceholder(new Label("Нет заказов"));

        idColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        itemsColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Order, String> orderStringCellDataFeatures)
            {
                StringBuilder str = new StringBuilder();

                for(Item item : orderStringCellDataFeatures.getValue().getItems())
                {
                    str.append(item).append("\n");
                }

                return new SimpleStringProperty(str.toString());
            }
        });
        addressColumn.setCellValueFactory(new PropertyValueFactory<Order, Address>("address"));

        onRefresh();
    }
}
