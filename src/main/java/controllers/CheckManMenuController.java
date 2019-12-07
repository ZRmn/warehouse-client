package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.DTO;
import utils.StageController;

public class CheckManMenuController
{
    @FXML
    private ImageView back;

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
    public void onProducts()
    {
        DTO.getInstance().getStageController().getPrimaryStage().setResizable(true);
        DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.CHECKMAN_PRODUCTS);
    }

    @FXML
    public void onBoxes()
    {
        DTO.getInstance().getStageController().getPrimaryStage().setResizable(true);
        DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.CHECKMAN_BOXES);
    }

    @FXML
    public void onPlaces()
    {
        DTO.getInstance().getStageController().getPrimaryStage().setResizable(true);
        DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.CHECKMAN_PLACES);
    }

    @FXML
    void onAddresses()
    {
        DTO.getInstance().getStageController().getPrimaryStage().setResizable(true);
        DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.CHECKMAN_ADDRESSES);
    }

    @FXML
    void onWarehouse()
    {
        DTO.getInstance().getStageController().getPrimaryStage().setResizable(true);
        DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.CHECKMAN_WAREHOUSE_MAP);
    }

    @FXML
    void initialize()
    {
        back.setImage(new Image(getClass().getResource("/resources/images/back.png").toString()));
    }
}
