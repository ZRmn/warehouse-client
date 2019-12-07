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
import models.User;
import utils.DTO;
import utils.StageController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminUsersController
{
    private ObservableList<User> users;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, String> fullNameColumn;

    @FXML
    private ImageView refresh;

    @FXML
    private ImageView back;

    @FXML
    void onRefresh()
    {
        users.clear();
        users.addAll(DTO.getInstance().getClient().getUsers());
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
        SignUpController controller = new SignUpController(null);
        DTO.getInstance().getStageController().setModalScene(StageController.SceneType.ADMIN_USERS_ADD_EDIT, controller);
        DTO.getInstance().getStageController().showModal();

        onRefresh();
    }

    @FXML
    void onEdit()
    {
        if(!table.getSelectionModel().getSelectedItems().isEmpty())
        {
            SignUpController controller = new SignUpController(table.getSelectionModel().getSelectedItems().get(0));
            DTO.getInstance().getStageController().setModalScene(StageController.SceneType.ADMIN_USERS_ADD_EDIT, controller);
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
                User user = table.getItems().get(i);
                table.getItems().remove(user);
                DTO.getInstance().getClient().deleteUser(user);
            }


            onRefresh();
        }
    }

    @FXML
    void initialize()
    {
        refresh.setImage(new Image(getClass().getResource("/resources/images/refresh.png").toString()));
        back.setImage(new Image(getClass().getResource("/resources/images/back.png").toString()));

        users = FXCollections.observableArrayList();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setItems(users);
        table.setPlaceholder(new Label("Нет пользователей"));


        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("fullName"));

        onRefresh();
    }
}
