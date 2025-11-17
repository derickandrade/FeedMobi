package com.user.fmuser.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import com.user.fmuser.utils.ScreenManager;

public class LoginController {
    @FXML
    private Label loginLabel;

    @FXML
    private Button loginButton;

 ;  @FXML
    protected void login() {
        // Lógica pro login com a JDBC;
    }

    @FXML
    protected void handleLogin() {
        if (validarLogin()) {
            ScreenManager.getInstance().showScreen("/com/user/fmuser/home-view.fxml", "Home");
        }
    }

    @FXML
    protected void irParaCadastro() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/cadastro-view.fxml", "Cadastro");
    }

    protected boolean validarLogin() {
        return true;
    }
}
