package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.*;
import utils.DTO;
import utils.MD5;
import utils.StageController;

public class SignUpController
{
    private boolean hasAdmin;
    private User user;
    private boolean editing;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField fullName;

    @FXML
    private ComboBox<String> roles;

    @FXML
    private Button submit;

    @FXML
    private Label loginRequired;

    @FXML
    private Label passwordRequired;

    @FXML
    private Label fullNameRequired;

    @FXML
    private Label loginTaken;

    public SignUpController(User user)
    {
        this.user = user;
    }

    @FXML
    public void onSubmit()
    {
        boolean hasError;

        loginRequired.setVisible(false);
        passwordRequired.setVisible(false);
        fullNameRequired.setVisible(false);
        loginTaken.setVisible(false);

        hasError = false;

        if(login.getText().isEmpty())
        {
            loginRequired.setVisible(true);
            hasError = true;
        }

        if(password.getText().isEmpty())
        {
            passwordRequired.setVisible(true);
            hasError = true;
        }

        if(fullName.getText().isEmpty())
        {
            fullNameRequired.setVisible(true);
            hasError = true;
        }

        if (hasError)
        {
            return;
        }

        switch (roles.getSelectionModel().getSelectedItem())
        {
            case "Администратор":
            {
                user = new Admin(0, login.getText(), MD5.encode(password.getText()), fullName.getText());
                break;
            }

            case "Учетчик":
            {
                user = new CheckMan(0, login.getText(), MD5.encode(password.getText()), fullName.getText());
                break;
            }

            case "Заказчик":
            {
                user = new Customer(0, login.getText(), MD5.encode(password.getText()), fullName.getText());
                break;
            }

            default:
            {
                user = new StockMan(0, login.getText(), MD5.encode(password.getText()), fullName.getText());
            }
        }

        if(editing)
        {
            if(!DTO.getInstance().getClient().editUser(user))
            {
                loginTaken.setVisible(true);
                return;
            }
        }
        else
        {
            if(!DTO.getInstance().getClient().addUser(user))
            {
                loginTaken.setVisible(true);
                return;
            }
        }

        if(!hasAdmin)
        {
            DTO.getInstance().getStageController().setPrimaryScene(StageController.SceneType.SIGN_IN);
        }
        else
        {
            login.getScene().getWindow().hide();
        }
    }

    @FXML
    void initialize()
    {
        loginRequired.setVisible(false);
        passwordRequired.setVisible(false);
        fullNameRequired.setVisible(false);
        loginTaken.setVisible(false);

        roles.getItems().add("Администратор");
        roles.getSelectionModel().select("Администратор");

        if (DTO.getInstance().getClient().hasAdmin())
        {
            hasAdmin = true;

            roles.getItems().add("Учетчик");
            roles.getItems().add("Заказчик");
            roles.getItems().add("Кладовщик");
        }
        else
        {
            hasAdmin = false;
        }

        if(user != null)
        {
            editing = true;
            login.setText(user.getLogin());
            fullName.setText(user.getFullName());
            submit.setText("Изменить");

            switch (user.getClass().getSimpleName())
            {
                case "Admin":
                {
                    roles.getSelectionModel().select("Администратор");
                    break;
                }

                case "CheckMan":
                {
                    roles.getSelectionModel().select("Учетчик");
                    break;
                }

                case "Customer":
                {
                    roles.getSelectionModel().select("Заказчик");
                    break;
                }

                case "StockMan":
                {
                    roles.getSelectionModel().select("Кладовщик");
                }
            }
        }
        else
        {
            editing = false;
        }
    }
}
