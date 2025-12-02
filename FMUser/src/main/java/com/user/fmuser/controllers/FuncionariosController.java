package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.Funcionario;
import com.user.fmuser.models.Database;
import javafx.beans.property.SimpleStringProperty;
import com.user.fmuser.utils.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;

public class FuncionariosController implements Initializable {

    @FXML
    private TableView<Funcionario> funcionariosTable;

    @FXML
    private TableColumn<Funcionario, String> colCpf;

    @FXML
    private TableColumn<Funcionario, String> colNome;

    @FXML
    private TableColumn<Funcionario, String> colCargo;

    @FXML
    private TableColumn<Funcionario, String> colAcoes;

    @FXML
    private VBox emptyStateVBox;

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
    public void initialize(URL location, ResourceBundle resources) {
        colCpf.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCpf()));
        colNome.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getNome() + " " + cell.getValue().getSobrenome()
        ));
        colCargo.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().isMotorista() ? "Motorista" : "Cobrador"
        ));

        atualizarTableFuncionarios();
    }

    private void atualizarTableFuncionarios() {
        ArrayList<Funcionario> funcionarios = Database.retrieveEmployees();
        ObservableList<Funcionario> observable = FXCollections.observableArrayList();
        if (funcionarios != null && !funcionarios.isEmpty()) {
            observable.addAll(funcionarios);
        }
        funcionariosTable.setItems(observable);
        atualizarVisibilidade();
    }

    private void atualizarVisibilidade() {
        boolean hasData = funcionariosTable.getItems() != null && !funcionariosTable.getItems().isEmpty();
        funcionariosTable.setVisible(hasData);
        funcionariosTable.setManaged(hasData);
        emptyStateVBox.setVisible(!hasData);
        emptyStateVBox.setManaged(!hasData);

    }


}
