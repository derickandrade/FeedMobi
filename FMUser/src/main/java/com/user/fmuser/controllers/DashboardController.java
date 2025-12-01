package com.user.fmuser.controllers;

import com.user.fmuser.models.Database;
import com.user.fmuser.models.Funcionario;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;

import javafx.scene.control.Label;

public class DashboardController implements Initializable {
    @FXML
    private Label funcionariosContagem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        atualizarContadores();
    }

    // atualizar o número de funcionarios
    public void atualizarContadores() {
        ArrayList<Funcionario> funcionarios = Database.retrieveEmployees();
        int count = (funcionarios == null) ? 0 : funcionarios.size();
        funcionariosContagem.setText(String.valueOf(count));
    }

    @FXML
    public void avaliacoes() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/avaliacoes-view.fxml", "Avaliações");
    }

    @FXML
    public void infraestruturas() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/infraestruturas-view.fxml", "Infraestruturas");
    }

    @FXML
    public void veiculos() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/veiculos-view.fxml", "Veículos");
    }

    @FXML
    public void funcionarios() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/funcionarios-view.fxml", "Funcionários");
    }

    @FXML
    public void percursos() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/percursos-view.fxml", "Percursos");
    }

    @FXML
    public void viagens() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/viagens-view.fxml", "Viagens");
    }

    @FXML
    public void logout() {

        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }
}
