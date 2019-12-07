package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.User;
import utils.DTO;
import utils.MD5;

public class SignInController
{
    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Label loginFailed;

    @FXML
    void onSubmit()
    {
        if(login.getText().isEmpty() || password.getText().isEmpty())
        {
            return;
        }

        User user = DTO.getInstance().getClient().authoriseUser(login.getText(), MD5.encode(password.getText()));

        if(user == null)
        {
            loginFailed.setVisible(true);
            return;
        }

        DTO.getInstance().setUser(user);

        user.enter();
    }

    @FXML
    void initialize()
    {
        loginFailed.setVisible(false);
    }
}
