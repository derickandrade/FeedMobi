package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;

public class FuncionariosController {

    @FXML
    public void logout() {
        MainApplication.logout();
    }

    @FXML
    public void voltar(){
        ScreenManager.getInstance().showScreen("/com/user/fmuser/dashboard-view.fxml", "Home");
    }

    @FXML
        public void irParaIncluirFuncionario() {

    }

}
