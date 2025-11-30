package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.Usuario;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AtualizarCadastroController implements Initializable {
    @FXML
    private Button voltarButton;

    @FXML
    private Button atualizarButton;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cpfField.setText(MainApplication.usuarioSessao.getCPF());
        cpfField.setDisable(true);
        nomeField.setText(MainApplication.usuarioSessao.nome);
        sobrenomeField.setText(MainApplication.usuarioSessao.sobrenome);
        emailField.setText(MainApplication.usuarioSessao.email);
    }

    @FXML
    protected void retornarHome() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/home-view.fxml", "Home");
    }
}
