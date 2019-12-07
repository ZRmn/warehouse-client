package controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import models.*;
import utils.DTO;
import utils.StageController;

import java.util.List;

public class CheckManWarehouseMapController
{
    private ObservableList<ObservableList<String>> warehouseMap;

    @FXML
    private TableView<ObservableList<String>> table;

    @FXML
    private ImageView refresh;

    @FXML
    private ImageView back;


    @FXML
    void onRefresh()
    {
        WarehouseMap map = DTO.getInstance().getClient().getWarehouseMap();

        warehouseMap.clear();
        table.getColumns().clear();

        TableColumn<ObservableList<String>, String> column = new TableColumn<>("Стеллаж\\Ряд");
        column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));

        table.getColumns().add(0, column);

        for (int i = 0; i < map.getColumns(); i++)
        {
            final int index = i;

            column = new TableColumn<>("Р" + (i + 1));
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(index + 1)));

            table.getColumns().add(i + 1, column);
        }

        for (int i = 0; i < map.getRows(); i++)
        {
            ObservableList<String> row = FXCollections.observableArrayList("С" + (i + 1));

            for (int j = 0; j < map.getColumns(); j++)
            {
                row.add("0%");
            }

            warehouseMap.add(row);
        }

        List<Place> places = DTO.getInstance().getClient().getPlaces();

        for(Place place : places)
        {
            String[] temp = place.getPosition().split(":");
            int columns = Integer.parseInt(temp[0]);
            int rows = Integer.parseInt(temp[1]);

            if(columns > map.getColumns() || rows > map.getRows())
            {
                continue;
            }

            warehouseMap.get(rows - 1).set(columns, ((place.getFullness() / (float) place.getCapacity()) * 100) + "%");
        }

        table.setItems(warehouseMap);
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
    void onAddColumn()
    {
        WarehouseMap map = DTO.getInstance().getClient().getWarehouseMap();
        map.setColumns(map.getColumns() + 1);
        DTO.getInstance().getClient().editWarehouseMap(map);
    }

    @FXML
    void onAddRow()
    {
        WarehouseMap map = DTO.getInstance().getClient().getWarehouseMap();
        map.setRows(map.getRows() + 1);
        DTO.getInstance().getClient().editWarehouseMap(map);
    }

    @FXML
    void onRemoveColumn()
    {
        WarehouseMap map = DTO.getInstance().getClient().getWarehouseMap();

        if(map.getColumns() > 1)
        {
            map.setColumns(map.getColumns() - 1);
            DTO.getInstance().getClient().editWarehouseMap(map);
        }
    }

    @FXML
    void onRemoveRow()
    {
        WarehouseMap map = DTO.getInstance().getClient().getWarehouseMap();

        if(map.getRows() > 1)
        {
            map.setRows(map.getRows() - 1);
            DTO.getInstance().getClient().editWarehouseMap(map);
        }
    }

    @FXML
    void initialize()
    {
        refresh.setImage(new Image(getClass().getResource("/resources/images/refresh.png").toString()));
        back.setImage(new Image(getClass().getResource("/resources/images/back.png").toString()));

        warehouseMap = FXCollections.observableArrayList();
        table.getSelectionModel().setCellSelectionEnabled(true);
        onRefresh();
    }
}
