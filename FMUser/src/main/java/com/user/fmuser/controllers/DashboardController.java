package com.user.fmuser.controllers;

import com.user.fmuser.models.Usuario;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;

public class DashboardController {
    @FXML
    public void avaliacoes() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/avaliacoes-view.fxml", "Avaliações");
    }

    @FXML
    public void infraestruturas() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/infraestruturaAdmin-view.fxml", "Infraestruturas");
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

    @FXML
    public void verInfraestruturas() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/infraestruturaAdmin-view.fxml", "Infraestruturas");
    }
}
