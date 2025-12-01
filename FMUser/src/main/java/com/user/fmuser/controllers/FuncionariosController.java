package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;

public class FuncionariosController {

    @FXML
    public void logout() {
        MainApplication.logout();
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }

    @FXML
    public void voltar(){
        ScreenManager.getInstance().showScreen("/com/user/fmuser/dashboard-view.fxml", "Home");
    }

    @FXML
        public void irParaIncluirFuncionario() {

        ScreenManager.getInstance().showScreen("/com/user/fmuser/incluirFuncionario-view.fxml", "Incluir");
    }

    @FXML
    public void handleCadastrarFuncionario() {}

    @FXML
    public void verificarCPF() {}


}
