package com.user.fmuser.controllers;

import com.user.fmuser.models.Database;
import com.user.fmuser.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import com.user.fmuser.utils.ScreenManager;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField cpfField;

    @FXML
    private Button singInButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginLabel;

    @FXML
    private Button loginButton;

    protected String cpf;

    @FXML
    protected void login() {
        // Lógica pro login com a JDBC;
    }


    @FXML
    protected void handleLogin() {
        cpf = cpfField.getText();
        if (validarLogin()) {
            if (cpf.equals("admin")) {
                ScreenManager.getInstance().showScreen("/com/user/fmuser/dashboard-view.fxml", "Home");
            }
            else {
                ScreenManager.getInstance().showScreen("/com/user/fmuser/home-view.fxml", "Home");
            }
        }
    }

    @FXML
    protected void irParaCadastro() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/cadastro-view.fxml", "Cadastro");
    }

    protected boolean validarLogin() {

        // Se o CPF existe no BD e a senha corresponde:
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
