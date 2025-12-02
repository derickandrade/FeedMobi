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
    public Date dataValidade;
    public int assentos;
    public int capacidadeEmPe;
    public String placa;

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
    public void voltar(){
        ScreenManager.getInstance().showScreen("/com/user/fmuser/veiculos-view.fxml", "Veículos");
    }

    @FXML
    public void setDate(){
        date = datePicker.getValue();
    }


    @FXML
    public void handleCadastrarVeiculo(){
        // verificando a placa
        String placaText = placaField.getText();
        if (placaText != null) {
            placaText = placaText.trim();
            if (placaText.isEmpty()) {
                placaText = null; // null se sem placa
            } else {
                placaText = placaText.toUpperCase();
            }
        }
        dataValidade = Date.valueOf(datePicker.getValue());
        assentos = Integer.parseInt(assentosField.getText());
        capacidadeEmPe = Integer.parseInt(capacidadeField.getText());

        Veiculo veiculo;
        try {
            if (placaText == null) {
                veiculo = new Veiculo(0, dataValidade, assentos, capacidadeEmPe);
            } else {
                veiculo = new Veiculo(0, dataValidade, assentos, capacidadeEmPe, placaText);
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
