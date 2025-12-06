package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.*;
import com.user.fmuser.utils.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.ArrayList;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

    @FXML
    private Label avaliacoesContagem;

    @FXML
    private Label mediaAvaliacoes;

    @FXML
    private Label funcionariosContagem;

    @FXML
    private Label veiculosContagem;

    @FXML
    private Label infraestruturasContagem;

    @FXML
    private Label percursosContagem;

    @FXML
    private Label viagensContagem;

    @FXML
    private TableView<Log> logTableView;

    @FXML
    private TableColumn<Log, String> itemColumn;

    @FXML
    private TableColumn<Log, String> contentColumn;

    @FXML
    private TableColumn<Log, LocalDateTime> dateColumn;

    public static ObservableList<Log> logs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (avaliacoesContagem != null && mediaAvaliacoes != null) {
            try {
                ArrayList<Float> pair = Database.retrieveAverage();
                int cont = pair == null ? 0 : Math.round(pair.get(0));
                avaliacoesContagem.setText(Integer.toString(cont));
                mediaAvaliacoes.setText(pair == null ? "0.0" : String.format("%.2f", pair.get(1)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (logTableView != null) {

            itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
            contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

            // Carregar dados se existirem
            if (logs != null && !logs.isEmpty()) {
                logTableView.setItems(logs);
            }
        }

        try {
            atualizarContadores();
            InfraestruturaAdminController.carregarDados();
            PercursoAdminController.carregarDados();
            ViagemAdminController.carregarDados();
        } catch (Exception e) {

        }
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

        ArrayList<Viagem> viagens = Database.retrieveTrips();
        int count4 = (viagens == null) ? 0 : viagens.size();
        viagensContagem.setText(String.valueOf(count4));
    }

    @FXML
    public void verLogs() {
        logs = FXCollections.observableArrayList(Database.retrieveLogs());
        try {
            if (logs != null && !logs.isEmpty()) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/user/fmuser/logs-view.fxml"));
                    Stage newStage = new Stage();
                    Scene newScene = new Scene(root);
                    newStage.setScene(newScene);
                    newStage.setTitle("Logs");
                    newStage.show();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                Alert error = new Alert(Alert.AlertType.INFORMATION);
                error.setTitle("Informação");
                error.setHeaderText("Não há logs disponíveis!");
                error.showAndWait();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void avaliacoes() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/relatorio-avaliacoes-view.fxml", "Avaliações");
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