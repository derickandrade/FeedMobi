package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.Usuario;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;
import com.user.fmuser.models.Avaliacao;

public class AvaliacoesController implements Initializable {

    @FXML
    protected TableView<Avaliacao> avaliacoesTable;

    @FXML
    public void novaAvaliacao() {
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
    public void logout() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Nova Avaliação");
        MainApplication.logout();
    }

    @FXML
    public void voltarHome() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/dashboard-view.fxml", "Home");
    }

    @FXML
    public void cancelarAvaliacao() {
        novaAvaliacaoController.resetarCampos();
        ScreenManager.getInstance().showScreen("/com/user/fmuser/dashboard-view.fxml", "Home");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
