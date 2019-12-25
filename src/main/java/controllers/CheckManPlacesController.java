package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import models.*;
import utils.DTO;
import utils.StageController;

public class CheckManPlacesController
{
    private ObservableList<Place> places;

    @FXML
    private TableView<Place> table;

    @FXML
    private TableColumn<Place, Integer> idColumn;

    @FXML
    private TableColumn<Place, String> palletsColumn;

    @FXML
    private TableColumn<Place, Integer> capacityColumn;

    @FXML
    private TableColumn<Place, Integer> fullnessColumn;

    @FXML
    private ImageView refresh;

    @FXML
    private ImageView back;


    @FXML
    void onRefresh()
    {
        places.clear();
        places.addAll(DTO.getInstance().getClient().getPlaces());
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
    void onEdit()
    {
        CheckManPlacesAddEditController controller = new CheckManPlacesAddEditController();
        DTO.getInstance().getStageController().setModalScene(StageController.SceneType.CHECKMAN_PLACES_ADD_EDIT, controller);
        DTO.getInstance().getStageController().showModal();

        onRefresh();
    }

    @FXML
    void initialize()
    {
        refresh.setImage(new Image(getClass().getResource("/resources/images/refresh.png").toString()));
        back.setImage(new Image(getClass().getResource("/resources/images/back.png").toString()));

        places = FXCollections.observableArrayList();
        table.setItems(places);
        table.setPlaceholder(new Label("Нет стеллажей"));

        idColumn.setCellValueFactory(new PropertyValueFactory<Place, Integer>("id"));
        palletsColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Place, String>, ObservableValue<String>>()
                {
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<Place, String> orderStringCellDataFeatures)
                    {
                        StringBuilder str = new StringBuilder();

                        for (Pallet pallet : orderStringCellDataFeatures.getValue().getPallets())
                        {
                            str.append(pallet).append("\n");
                        }

                        return new SimpleStringProperty(str.toString());
                    }
                });

        capacityColumn.setCellValueFactory(new PropertyValueFactory<Place, Integer>("capacity"));
        fullnessColumn.setCellValueFactory(new PropertyValueFactory<Place, Integer>("fullness"));

        onRefresh();
    }
}
