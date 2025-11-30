package com.user.fmuser.controllers;

import com.sun.tools.javac.Main;
import com.user.fmuser.MainApplication;
import com.user.fmuser.models.Database;
import com.user.fmuser.models.Usuario;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
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

    @FXML
    protected void excluirConta() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXCLUIR CONTA");
        alert.setHeaderText("Você tem certeza que deseja continuar?");
        alert.setContentText("Esta ação não pode ser desfeita.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Database.removeUser(MainApplication.usuarioSessao.getCPF());
                Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                sucesso.setTitle("CONTA EXCLUÍDA");
                sucesso.setHeaderText("Conta excluída com sucesso!");
                MainApplication.logout();
                ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");

            } catch (RuntimeException e) {
                Alert erro = new Alert(Alert.AlertType.ERROR);
                erro.setTitle("ERRO");
                erro.setHeaderText("Não foi possível excluir a conta!");
                erro.setContentText("Ocorreu um erro:\n" + e);
            }

        }
    }
}
