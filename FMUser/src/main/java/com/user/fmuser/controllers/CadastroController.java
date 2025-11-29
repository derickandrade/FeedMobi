package com.user.fmuser.controllers;

import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CadastroController {

    private String cpf;
    private String email;
    private String senha;
    private String confirmarSenha;
    private String nome;
    private String sobrenome;
    private boolean isAdmin;

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

    @FXML
    public void handleCadastro() {
        cpf = cpfField.getText();
        email = emailField.getText();
    }
}
