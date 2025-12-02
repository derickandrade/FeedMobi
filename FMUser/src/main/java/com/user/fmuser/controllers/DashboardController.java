package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.*;
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

    @FXML
    private Label veiculosContagem;

    @FXML
    private Label infraestruturasContagem;

    @FXML
    private Label percursosContagem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        atualizarContadores();
        InfraestruturaAdminController.carregarDados();
        PercursoAdminController.carregarDados();
    }

    // atualizar o número de funcionarios
    public void atualizarContadores() {
        ArrayList<Funcionario> funcionarios = Database.retrieveEmployees();
        int count = (funcionarios == null) ? 0 : funcionarios.size();
        funcionariosContagem.setText(String.valueOf(count));

        ArrayList<Veiculo> veiculos = Database.retrieveVehicles();
        int count1 = (veiculos == null) ? 0 : veiculos.size();
        veiculosContagem.setText(String.valueOf(count1));

        ArrayList<Location> infraestruturas = Database.retrieveLocations();
        int count2 = (infraestruturas == null) ? 0 : infraestruturas.size();
        infraestruturasContagem.setText(String.valueOf(count2));

        ArrayList<Percurso> percursos = Database.retrieveRoutes();
        int count3 = (percursos == null) ? 0 : percursos.size();
        percursosContagem.setText(String.valueOf(count3));
    }

    @FXML
    public void avaliacoes() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/avaliacoes-view.fxml", "Avaliações");
    }

    @FXML
    public void infraestruturas() {
        InfraestruturaAdminController.setScreen();
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
        PercursoAdminController.setScreen();
    }

    @FXML
    public void viagens() {
        ViagemAdminController.setScreen();
    }

    @FXML
    public void logout() {
        MainApplication.logout();
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }

    @FXML
    public void verInfraestruturas() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/infraestruturaAdmin-view.fxml", "Infraestruturas");
    }
}
