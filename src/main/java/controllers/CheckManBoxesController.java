package controllers;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import models.Product;
import utils.DTO;
import utils.StageController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Box;

public class CheckManBoxesController
{
    private ObservableList<Box> boxes;

    @FXML
    private TableView<Box> table;

    @FXML
    private TableColumn<Box, Integer> idColumn;

    @FXML
    private TableColumn<Box, Product> productColumn;

    @FXML
    private TableColumn<Box, Integer> countColumn;

    @FXML
    private ImageView refresh;

    @FXML
    private ImageView back;


    @FXML
    void onRefresh()
    {
        boxes.clear();
        boxes.addAll(DTO.getInstance().getClient().getBoxes());
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
        CheckManBoxesAddEditController controller = new CheckManBoxesAddEditController(null);
        DTO.getInstance().getStageController().setModalScene(StageController.SceneType.CHECKMAN_BOXES_ADD_EDIT,
                controller);
        DTO.getInstance().getStageController().showModal();

        onRefresh();
    }

    @FXML
    void onEdit()
    {
        if (!table.getSelectionModel().getSelectedItems().isEmpty())
        {
            CheckManBoxesAddEditController controller = new CheckManBoxesAddEditController(
                    table.getSelectionModel().getSelectedItems().get(0));
            DTO.getInstance().getStageController().setModalScene(StageController.SceneType.CHECKMAN_BOXES_ADD_EDIT,
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
                Box box = table.getItems().get(i);
                table.getItems().remove(box);
                DTO.getInstance().getClient().deleteBox(box);
            }

            onRefresh();
        }
    }

    @FXML
    void initialize()
    {
        refresh.setImage(new Image(getClass().getResource("/resources/images/refresh.png").toString()));
        back.setImage(new Image(getClass().getResource("/resources/images/back.png").toString()));

        boxes = FXCollections.observableArrayList();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setItems(boxes);
        table.setPlaceholder(new Label("Нет коробок"));

        idColumn.setCellValueFactory(new PropertyValueFactory<Box, Integer>("id"));
        productColumn.setCellValueFactory(new PropertyValueFactory<Box, Product>("product"));
        countColumn.setCellValueFactory(new PropertyValueFactory<Box, Integer>("count"));

        onRefresh();
    }
}
