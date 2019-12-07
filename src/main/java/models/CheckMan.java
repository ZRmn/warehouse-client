package models;

import utils.DTO;
import utils.StageController;

public class CheckMan extends User
{
    public CheckMan()
    {

    }

    public CheckMan(String login, String password, String fullName)
    {
        super(login, password, fullName);
    }

    public CheckMan(Integer id, String login, String password, String fullName)
    {
        super(id, login, password, fullName);
    }

    @Override
    public void enter()
    {
        DTO.getInstance().getStageController().getPrimaryStage().setResizable(false);
        DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.CHECKMAN_MENU);
    }
}
