package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.Database;
import com.user.fmuser.models.Veiculo;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class novoVeiculoController implements Initializable {
    protected static LocalDate date;

    @FXML
    public TextField placaField;

    @FXML
    public DatePicker datePicker;

    @FXML
    public TextField assentosField;

    @FXML
    public TextField capacidadeField;

    @FXML
    private Label placaMessage;

    @FXML
    private Label dataMessage;

    @FXML
    private Label assentosMessage;

    @FXML
    private Label capacidadeMessage;

    public void initialize(URL location, ResourceBundle resources) {
        //inicializa com mensagens ocultas
        placaMessage.setVisible(false);
        dataMessage.setVisible(false);
        assentosMessage.setVisible(false);
        capacidadeMessage.setVisible(false);
    }

    public void logout() {
        MainApplication.logout();
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }

    @FXML
    public void voltar() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/veiculos-view.fxml", "Veículos");
    }

    @FXML
    public void setDate() {
        date = datePicker.getValue();
    }


    @FXML
    public void handleCadastrarVeiculo() {
        // verificando a placa
        String placaText = placaField.getText();
        String assentosText = assentosField.getText();
        String capacidadeText = capacidadeField.getText();
        Date dataValidadeText = Date.valueOf(datePicker.getValue());
        boolean valid = true;

        // resetar mensagens de erro
        assentosMessage.setVisible(false);
        capacidadeMessage.setVisible(false);

        // validações
        if (placaText != null) {
            placaText = placaText.trim();
            if (placaText.isEmpty()) {
                placaText = null; // null se sem placa
            } else {
                placaText = placaText.toUpperCase();
            }
        }
        if (!isNumeric(assentosText)) {
            assentosMessage.setVisible(true);
            valid = false;
        } else {
            if (!isNumeric(capacidadeText)) {
                capacidadeMessage.setVisible(true);
                valid = false;
            }

            if (valid) {
                Veiculo veiculo;
                try {
                    if (placaText == null) {
                        veiculo = new Veiculo(0, dataValidadeText, Integer.parseInt(assentosText), Integer.parseInt(capacidadeText));
                    } else {
                        veiculo = new Veiculo(0, dataValidadeText, Integer.parseInt(assentosText), Integer.parseInt(capacidadeText), placaText);
                    }
                } catch (IllegalArgumentException e) {
                    placaMessage.setText("Placa inválida");
                    placaMessage.setVisible(true);
                    return;
                }

                boolean ok = Database.addVehicle(veiculo);
                if (!ok) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao cadastrar veículo (verifique se já existe veículo/placa igual).", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veículo cadastrado com sucesso.", ButtonType.OK);
                alert.showAndWait();
                voltar();
            }


        }

    }
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
