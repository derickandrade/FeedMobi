package com.user.fmuser.controllers;

import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;

public class CadastroController {

    @FXML
    protected void retornarLogin() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }
}
