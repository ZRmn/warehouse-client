package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Address;
import models.Product;
import utils.DTO;
import utils.StageController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckManAddressesController
{
    private ObservableList<Address> addresses;

    @FXML
    private TableView<Address> table;

    @FXML
    private TableColumn<Address, Integer> idColumn;

    @FXML
    private TableColumn<Address, String> addressColumn;

    @FXML
    private ImageView refresh;

    @FXML
    private ImageView back;


    @FXML
    void onRefresh()
    {
        addresses.clear();
        addresses.addAll(DTO.getInstance().getClient().getAddresses());
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
        CheckManAddressesAddEditController controller = new CheckManAddressesAddEditController(null);
        DTO.getInstance().getStageController().setModalScene(StageController.SceneType.CHECKMAN_ADDRESSES_ADD_EDIT,
                controller);
        DTO.getInstance().getStageController().showModal();

        onRefresh();
    }

    @FXML
    void onEdit()
    {
        if (!table.getSelectionModel().getSelectedItems().isEmpty())
        {
            CheckManAddressesAddEditController controller = new CheckManAddressesAddEditController(
                    table.getSelectionModel().getSelectedItems().get(0));
            DTO.getInstance().getStageController().setModalScene(StageController.SceneType.CHECKMAN_ADDRESSES_ADD_EDIT,
                    controller);
            DTO.getInstance().getStageController().showModal();

            onRefresh();
        }
    }

    @FXML
    void onDelete()
    {
        if (!table.getSelectionModel().getSelectedItems().isEmpty())
        {
            List<Integer> indexes = new ArrayList<>(table.getSelectionModel().getSelectedIndices());

            Collections.sort(indexes);
            Collections.reverse(indexes);

            for (Integer i : indexes)
            {
                Address address = table.getItems().get(i);
                table.getItems().remove(address);
                DTO.getInstance().getClient().deleteAddress(address);
            }

            onRefresh();
        }
    }

    @FXML
    void initialize()
    {
        refresh.setImage(new Image(getClass().getResource("/resources/images/refresh.png").toString()));
        back.setImage(new Image(getClass().getResource("/resources/images/back.png").toString()));

        addresses = FXCollections.observableArrayList();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setItems(addresses);
        table.setPlaceholder(new Label("Нет адресов"));

        idColumn.setCellValueFactory(new PropertyValueFactory<Address, Integer>("id"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Address, String>("address"));

        onRefresh();
    }
}
