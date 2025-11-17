package com.user.fmuser.controllers;

import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CadastroController {

    @FXML
    private Button voltarButton;

    @FXML
    private Button cadastrarButton;

    @FXML
    private PasswordField confirmarSenhaField;

    @FXML
    private TextField sobrenomeField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField cpfField;

    @FXML
    private PasswordField senhaField;

    @FXML
    protected void retornarLogin() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }
}
