package com.user.fmuser.controllers;

import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Label userNameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userName = "Beltrana Silva";

        userNameLabel.setText("Olá, " + userName + " !");
    }

    @FXML
    public void logout() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }
}
