package models;

import utils.DTO;
import utils.StageController;

public class Admin extends User
{
    public Admin()
    {

    }

    public Admin(String login, String password, String fullName)
    {
        super(login, password, fullName);
    }

    public Admin(Integer id, String login, String password, String fullName)
    {
        super(id, login, password, fullName);
    }

    @Override
    public void enter()
    {
        DTO.getInstance().getStageController().getPrimaryStage().setResizable(true);
        DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.ADMIN_USERS);
    }
}
