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
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;

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
    private TableColumn<Funcionario, Void> colAcoes;

    @FXML
    private VBox emptyStateVBox;

    public static final String EDIT_SVG = "M11.8125 2.6875L10.5938 3.90625L8.09375 1.40625L9.3125 0.1875C9.4375 0.0625 9.59375 0 9.78125 0C9.96875 0 10.125 0.0625 10.25 0.1875L11.8125 1.75C11.9375 1.875 12 2.03125 12 2.21875C12 2.40625 11.9375 2.5625 11.8125 2.6875ZM0 9.5L7.375 2.125L9.875 4.625L2.5 12H0V9.5Z";

    public static final String DELETE_SVG = "M9.3125 0.65625V2H0V0.65625H2.3125L3 0H6.3125L7 0.65625H9.3125ZM0.65625 10.6562V2.65625H8.65625V10.6562C8.65625 11.0104 8.52083 11.3229 8.25 11.5938C7.97917 11.8646 7.66667 12 7.3125 12H2C1.64583 12 1.33333 11.8646 1.0625 11.5938C0.791667 11.3229 0.65625 11.0104 0.65625 10.6562Z";

    private static boolean isEdit = false;

    public static Funcionario itemAtt;

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

        colAcoes.setCellFactory(col -> new TableCell<Funcionario, Void>() {
            private final Button editButton = new Button("");
            private final Button deleteButton = new Button("");
            private final HBox container = new HBox(10, editButton, deleteButton);

            {
                SVGPath editIcon = new SVGPath();
                editIcon.setContent(EDIT_SVG);
                editIcon.getStyleClass().add("editIcon");
                editButton.setGraphic(editIcon);
                SVGPath deleteIcon = new SVGPath();
                deleteIcon.setContent(DELETE_SVG);
                deleteIcon.getStyleClass().add("deleteIcon");
                deleteButton.setGraphic(deleteIcon);

                editButton.getStyleClass().add("tertiaryButton");
                deleteButton.getStyleClass().add("tertiaryButton");

                editButton.setPrefWidth(20);
                deleteButton.setPrefWidth(20);

                container.setAlignment(Pos.CENTER);

                editButton.setOnAction(event -> {
                    Funcionario item = getTableView().getItems().get(getIndex());
                    editarFuncionario(item);
                });

                deleteButton.setOnAction(event -> {
                    Funcionario item = getTableView().getItems().get(getIndex());
                    excluirFuncionario(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });
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

    private void successMessage(String frase) {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("SUCESSO");
        success.setHeaderText(frase);
        success.showAndWait();
    }

    private void errorMessage(String frase) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("ERRO");
        error.setHeaderText(frase);
        error.showAndWait();
    }

    private void excluirFuncionario(Funcionario item) {
        if (Database.removeEmployee(item)) {
            successMessage("Funcionário excluído com sucesso!");
            atualizarTableFuncionarios();
        }
        else {
            errorMessage("Não foi possível excluir o funcionário!\nTente novamente.");
        }
    }

    private void editarFuncionario(Funcionario item) {
        isEdit = true;
        itemAtt = item;
        ScreenManager.getInstance().showScreen("/com/user/fmuser/atualizarFuncionario-view.fxml", "Funcionário");
    }

}
