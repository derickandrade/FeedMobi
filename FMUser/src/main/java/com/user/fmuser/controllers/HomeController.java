package com.user.fmuser.controllers;

import com.user.fmuser.models.Usuario;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

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
        handleLogout();
    }

    public static void handleLogout() {
        novaAvaliacaoController.resetarCampos();
        Usuario.logout();
    }

    @FXML
    public void irParaAvaliacao() {
        String telaAvaliacao;
        int tipoAvaliacao = novaAvaliacaoController.tipoAvaliacao;
        telaAvaliacao = "/com/user/fmuser/novaAvaliacao-view.fxml";
        if (tipoAvaliacao == 1) {
            telaAvaliacao = "/com/user/fmuser/viagem-view.fxml";
        }
        else if (tipoAvaliacao == 2) {
            telaAvaliacao = "/com/user/fmuser/infraestrutura-view.fxml";
        }
        else if (tipoAvaliacao == 3) {
            telaAvaliacao = "/com/user/fmuser/ciclovia-view.fxml";
        }
        ScreenManager.getInstance().showScreen(telaAvaliacao, "Nova Avaliação");
    }

    @FXML
    public void atualizarCadastro() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/atualizarCadastro-view.fxml", "Meu Cadastro");
    }
}
