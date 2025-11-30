package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.Database;
import com.user.fmuser.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.user.fmuser.utils.ScreenManager;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField cpfField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label cpfMessage;

    @FXML
    private Label senhaMessage;

    protected String cpf;
    protected String senha;

    @FXML
    protected void handleLogin() {
        cpf = cpfField.getText();
        senha = passwordField.getText();

        boolean cpfValido = !cpf.isEmpty();
        boolean senhaValida = !senha.isEmpty();

        cpfMessage.setVisible(!cpfValido);
        senhaMessage.setVisible(!senhaValida);
        
        if (cpfValido && senhaValida) {
            Usuario usuarioTeste = Database.retrieveUser(cpf);
            if (usuarioTeste != null && senha.equals(usuarioTeste.getSenha())) {
                MainApplication.usuarioSessao = usuarioTeste;
                if (usuarioTeste.isAdmin()) {
                    ScreenManager.getInstance().showScreen("/com/user/fmuser/dashboard-view.fxml", "Home");
                }
                else {
                    ScreenManager.getInstance().showScreen("/com/user/fmuser/home-view.fxml", "Home");
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERRO");
                alert.setHeaderText("Não foi possível realizar o login!");
                alert.setContentText("Usuário e/ou senha incorreto(s).");
                alert.showAndWait();
            }
        }
    }

    @FXML
    protected void irParaCadastro() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/cadastro-view.fxml", "Cadastro");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
