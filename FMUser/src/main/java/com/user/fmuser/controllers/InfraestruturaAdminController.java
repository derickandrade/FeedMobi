package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.Database;
import com.user.fmuser.models.Location;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InfraestruturaAdminController implements Initializable {

    @FXML
    public ComboBox<String> tipoComboBox;

    @FXML
    public TextField localField;

    @FXML
    public void voltar() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/infraestruturaAdmin-view.fxml", "Infraestruturas");
    }

    @FXML
    public void incluirInfraestrutura() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/incluirInfraestrutura-view.fxml", "Infraestruturas");
    }

    @FXML
    public void logout() {
        MainApplication.logout();
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }

    @FXML
    public void confirmar() {
        String tipoSelecionado = tipoComboBox.getValue();
        String local = localField.getText();
        boolean tipoValido = tipoSelecionado.equals("Parada") || tipoSelecionado.equals("Ciclovia");
        boolean localValido = local != null && !local.isEmpty();

        if (tipoValido && localValido) {
            if (tipoSelecionado.equals("Parada")) {
                if (Database.addLocation(local, Location.LocationType.Parada)) {
                    successMessage();
                } else {
                    errorMessage();
                }
            } else {
                if (Database.addLocation(local, Location.LocationType.Ciclovia)) {
                    successMessage();
                }
                else {
                    errorMessage();
                }
            }
        }
    }

    private void successMessage() {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("SUCESSO");
        success.setHeaderText("Infraestrutura adicionada com sucesso!");
        success.showAndWait();
    }

    private void errorMessage() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("ERRO");
        error.setHeaderText("Não foi possível adicionar a infraestrutura!");
        error.setContentText("Tente novamente. Ocorreu um erro.");
        error.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (tipoComboBox != null) {
            tipoComboBox.getItems().addAll("Parada", "Ciclovia");
        }
    }
}
