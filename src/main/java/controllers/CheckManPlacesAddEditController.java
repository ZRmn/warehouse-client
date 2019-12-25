package controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.DTO;
import models.*;
import utils.StageController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckManPlacesAddEditController
{
    private ObservableList<ObservableList<String>> warehouseMap;

    @FXML
    private TableView<ObservableList<String>> table;

    @FXML
    private ComboBox<Box> boxes;

    @FXML
    private TextField count;

    @FXML
    private TextField capacity;

    @FXML
    private Label boxRequired;

    @FXML
    private Label countRequired;

    void createTable()
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

        for (Place place : places)
        {
            String[] temp = place.getPosition().split(":");
            int columns = Integer.parseInt(temp[0]);
            int rows = Integer.parseInt(temp[1]);

            if (columns > map.getColumns() || rows > map.getRows())
            {
                continue;
            }

            warehouseMap.get(rows - 1).set(columns, String.format ("%.0f", (place.getFullness() / (float) place.getCapacity()) * 100) + "%");
        }

        table.setItems(warehouseMap);
    }


    @FXML
    void onEdit()
    {
        List<Place> places = DTO.getInstance().getClient().getPlaces();

        boolean hasError = false;

        Integer capacity = 0;

        try
        {
            capacity = Integer.parseInt(this.capacity.getText());
        }
        catch (Exception e)
        {
            return;
        }

        for (Place place : places)
        {
            if(place.getFullness() > capacity)
            {
                hasError = true;
                break;
            }
        }

        if (!hasError)
        {
            DTO.getInstance().getClient().setPlaceCapacity(capacity);
            createTable();
        }
    }

    @FXML
    void onAdd()
    {
        boolean hasError = false;

        boxRequired.setVisible(false);
        countRequired.setVisible(false);

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

        if (table.getSelectionModel().getSelectedItem() != null)
        {
            TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow() + 1;
            int column = pos.getColumn();

            Place place = DTO.getInstance().getClient().getPlace(column + ":" + row);

            if (place != null)
            {
                if (place.getCapacity() < place.getFullness() + Integer.parseInt(count.getText()))
                {
                    return;
                }

                DTO.getInstance().getClient().deletePlace(place);
            }
            else
            {
                place = new Place(column + ":" + row, 50, Integer.parseInt(count.getText()), new ArrayList<>());
            }


            place.getPallets().add(
                    new Pallet(boxes.getSelectionModel().getSelectedItem(), Integer.parseInt(count.getText())));

            DTO.getInstance().getClient().addPlace(place);

            createTable();
        }
    }

    @FXML
    void initialize()
    {
        boxRequired.setVisible(false);
        countRequired.setVisible(false);

        List<Box> boxesList = DTO.getInstance().getClient().getBoxes();

        for (Box box : boxesList)
        {
            boxes.getItems().add(box);
        }

        if (!boxes.getItems().isEmpty())
        {
            boxes.getSelectionModel().select(0);
        }

        table.setOnMouseClicked(mouseEvent ->
        {
            TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow() + 1;
            int column = pos.getColumn();

            Place place = DTO.getInstance().getClient().getPlace(column + ":" + row);


            if (mouseEvent.getClickCount() == 2)
            {
                if (place == null || place.getPallets().isEmpty())
                {
                    return;
                }

                try
                {
                    CheckManPlacesPalletsDeleteController controller = new CheckManPlacesPalletsDeleteController(
                            place);
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/resources/views/checkman-places-pallets-delete.fxml"));
                    loader.setController(controller);
                    Scene scene = new Scene(loader.load());
                    scene.getStylesheets().add(0, "/resources/styles/style.css");
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(capacity.getScene().getWindow());
                    stage.getIcons().add(new Image("/resources/images/gear.png"));
                    stage.setTitle("");
                    stage.setScene(scene);
                    stage.showAndWait();

                    createTable();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        warehouseMap = FXCollections.observableArrayList();
        table.getSelectionModel().setCellSelectionEnabled(true);

        createTable();
    }
}
