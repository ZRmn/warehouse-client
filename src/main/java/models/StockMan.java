package models;

import utils.DTO;
import utils.StageController;

public class StockMan extends User
{
    public StockMan()
    {

    }

    public StockMan(String login, String password, String fullName)
    {
        super(login, password, fullName);
    }

    public StockMan(Integer id, String login, String password, String fullName)
    {
        super(id, login, password, fullName);
    }

    @Override
    public void enter()
    {
        DTO.getInstance().getStageController().getPrimaryStage().setResizable(false);
        DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.STOCKMAN_MENU);
    }
}
