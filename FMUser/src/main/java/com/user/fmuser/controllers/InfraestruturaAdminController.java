package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.Database;
import com.user.fmuser.models.Location;
import com.user.fmuser.models.Parada;
import com.user.fmuser.utils.ScreenManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class InfraestruturaAdminController implements Initializable {

    @FXML
    public ComboBox<String> tipoComboBox;

    @FXML
    public TextField localField;

    @FXML
    public Label tipoMessage;

    @FXML
    public Label localMessage;

    @FXML
    private TableView<Location> infraestruturaTable;

    @FXML
    private TableColumn<Location, String> tipoColumn;

    @FXML
    private TableColumn<Location, String> localizacaoColumn;

    @FXML
    private TableColumn<Location, Void> acoesColumn;

    public static ObservableList<Location> infraestruturaList;

    public static final String EDIT_SVG = "M11.8125 2.6875L10.5938 3.90625L8.09375 1.40625L9.3125 0.1875C9.4375 0.0625 9.59375 0 9.78125 0C9.96875 0 10.125 0.0625 10.25 0.1875L11.8125 1.75C11.9375 1.875 12 2.03125 12 2.21875C12 2.40625 11.9375 2.5625 11.8125 2.6875ZM0 9.5L7.375 2.125L9.875 4.625L2.5 12H0V9.5Z";

    public static final String DELETE_SVG = "M9.3125 0.65625V2H0V0.65625H2.3125L3 0H6.3125L7 0.65625H9.3125ZM0.65625 10.6562V2.65625H8.65625V10.6562C8.65625 11.0104 8.52083 11.3229 8.25 11.5938C7.97917 11.8646 7.66667 12 7.3125 12H2C1.64583 12 1.33333 11.8646 1.0625 11.5938C0.791667 11.3229 0.65625 11.0104 0.65625 10.6562Z";

    private static boolean isEdit = false;

    public static Location itemAtt;

    @FXML
    public void voltar() {
        setScreen();
    }

    @FXML
    public void voltarDashboard() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/dashboard-view.fxml", "Home");
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
        boolean tipoValido = tipoSelecionado != null && (tipoSelecionado.equals("Parada") || tipoSelecionado.equals("Ciclovia"));
        boolean localValido = local != null && !local.isEmpty();

        tipoMessage.setVisible(!tipoValido);
        localMessage.setVisible(!localValido);

        if (tipoValido && localValido) {
            if (tipoSelecionado.equals("Parada")) {
                if (Database.addLocation(local, Location.LocationType.Parada)) {
                    successMessage("Parada adicionada com sucesso!");
                    carregarDados();
                } else {
                    errorMessage("Não foi possível adicionar a parada!\nTente novamente.");
                }
            } else {
                if (Database.addLocation(local, Location.LocationType.Ciclovia)) {
                    successMessage("Ciclovia adicionada com sucesso!");
                    carregarDados();
                }
                else {
                    errorMessage("Não foi possível adicionar a ciclovia!\nTente novamente.");
                }
            }
        }
    }

    @FXML
    public void atualizar() {
        String novaLoc = localField.getText();
        if (novaLoc != null && !novaLoc.isEmpty()) {
            itemAtt.localizacao = novaLoc;
            if (Database.updateLocation(itemAtt)) {
                successMessage("Infraestrutura atualizada com sucesso!");
                carregarDados();
                carregarTabela();
            }
            else {
                errorMessage("Não possível atualizar a infraestrutura!\nTente novamente");
            }
        }
        isEdit = false;
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

    private void excluirInfraestrutura(Location item) {
        if (Database.removeLocation(item)) {
            successMessage("Infraestrutura excluída com sucesso!");
            carregarDados();
            carregarTabela();
        }
        else {
            errorMessage("Não foi possível excluir a infraestrutura!\nTente novamente.");
        }
    }

    private void editarInfraestrutura(Location item) {
        isEdit = true;
        itemAtt = item;
        ScreenManager.getInstance().showScreen("/com/user/fmuser/atualizarInfraestrutura-view.fxml", "Infraestruturas");
    }

    public static void carregarDados() {
        infraestruturaList = FXCollections.observableArrayList();
        try {
            // Carregar todas as localizações (Paradas e Ciclovias)
            infraestruturaList.addAll(Database.retrieveLocations());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setScreen() {
        if (InfraestruturaAdminController.infraestruturaList == null || InfraestruturaAdminController.infraestruturaList.isEmpty()) {
            ScreenManager.getInstance().showScreen("/com/user/fmuser/infraestruturaAdmin-view.fxml", "Infraestruturas");
            return;
        }
        ScreenManager.getInstance().showScreen("/com/user/fmuser/infraestruturaAdminFilled-view.fxml", "Infraestruturas");
    }

    public void carregarTabela() {
        if (infraestruturaTable != null) {
            infraestruturaTable.setItems(infraestruturaList);
        }
        if (infraestruturaList == null || infraestruturaList.isEmpty()) {
            ScreenManager.getInstance().showScreen("/com/user/fmuser/infraestruturaAdmin-view.fxml", "Infraestruturas");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (tipoComboBox != null) {
            tipoComboBox.getItems().addAll("Parada", "Ciclovia");
        }

        if (isEdit && tipoComboBox != null) {
            if (itemAtt instanceof Parada) {
                tipoComboBox.setValue("Parada");
            } else {
                tipoComboBox.setValue("Ciclovia");
            }
            tipoComboBox.setDisable(true);
            localField.setText(itemAtt.localizacao);
        }

        if (infraestruturaTable != null) {
            tipoColumn.setCellValueFactory(cellData -> {
                Location loc = cellData.getValue();
                String tipo = (loc instanceof Parada) ? "Parada" : "Ciclovia";
                return new SimpleStringProperty(tipo);
            });

            localizacaoColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().localizacao));

            acoesColumn.setCellFactory(col -> new TableCell<Location, Void>() {
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

                    editButton.setPrefWidth(70);
                    deleteButton.setPrefWidth(70);

                    container.setAlignment(Pos.CENTER);

                    editButton.setOnAction(event -> {
                        Location item = getTableView().getItems().get(getIndex());
                        editarInfraestrutura(item);
                    });

                    deleteButton.setOnAction(event -> {
                        Location item = getTableView().getItems().get(getIndex());
                        excluirInfraestrutura(item);
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
            carregarTabela();
        }
    }
}


