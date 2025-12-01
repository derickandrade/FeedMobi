package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class InfraestruturaAdminController {

    @FXML
    public ComboBox<String> tipoComboBox;

    @FXML
    public ComboBox<String> localComboBox;

    @FXML
    public ComboBox<String> statusComboBox;

    @FXML
    public void voltar() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/infraestruturaAdmin-view.fxml", "Infraestruturas");
    }

    @FXML
    public void incluirInfraestrutura() {

    }

    @FXML
    public void logout() {
        MainApplication.logout();
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }

    @FXML
    public void confirmar() {

    }
}
