package models;

import utils.DTO;
import utils.StageController;

public class Customer extends User
{
    public Customer()
    {

    }

    public Customer(String login, String password, String fullName)
    {
        super(login, password, fullName);
    }

    public Customer(Integer id, String login, String password, String fullName)
    {
        super(id, login, password, fullName);
    }

    @Override
    public void enter()
    {
        DTO.getInstance().getStageController().getPrimaryStage().setResizable(true);
        DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.CUSTOMER_ORDERS);
    }
}
