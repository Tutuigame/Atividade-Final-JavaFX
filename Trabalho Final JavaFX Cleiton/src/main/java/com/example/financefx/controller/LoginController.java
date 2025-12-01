
package com.example.financefx.controller;

import com.example.financefx.App;
import com.example.financefx.dao.UserDao;
import com.example.financefx.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label message;
    public static User CURRENT_USER;

    private final UserDao userDao = new UserDao();

    
    @FXML
    public void onLogin() {
        var u = userDao.auth(usernameField.getText(), passwordField.getText());
        if (u != null) {
            CURRENT_USER = u;
            App.setRoot("view/dashboard");
        } else {
            message.setText("Usuário ou senha inválidos.");
        }
    }
}
