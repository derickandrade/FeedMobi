package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class novoFuncionarioController {

    protected static String texto = "Selecionar";

    public static int cargoFuncionario = 0;

    public void logout() {
        MainApplication.logout();
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }

    @FXML
    public void voltar(){
        ScreenManager.getInstance().showScreen("/com/user/fmuser/funcionarios-view.fxml", "Funcionarios");
    }

    @FXML
    public ComboBox<String> cargoFuncionarioMenu;

    @FXML
    public void initialize(){
        cargoFuncionarioMenu.getItems().addAll("Motorista", "Cobrador");

        if (cargoFuncionario == 1) {
            texto = "Motorista";
        }
        else if (cargoFuncionario == 2) {
            texto = "Cobrador";
        }
        else {
            texto = null;
        }
        cargoFuncionarioMenu.setValue(texto);
    }



    @FXML
    public void handleCargoFuncionario() {

    }
    @FXML
    public void handleCadastrarFuncionario() {}

    @FXML
    public void verificarCPF() {}
}
