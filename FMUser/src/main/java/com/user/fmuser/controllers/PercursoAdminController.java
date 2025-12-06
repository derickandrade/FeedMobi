package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.*;
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
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PercursoAdminController implements Initializable {

    @FXML
    public ComboBox<String> origemComboBox;

    @FXML
    public ComboBox<String> destinoComboBox;

    @FXML
    public Label origemMessage;

    @FXML
    public Label destinoMessage;

    @FXML
    private TableView<Percurso> percursoTable;

    @FXML
    private TableColumn<Percurso, String> origemColumn;

    @FXML
    private TableColumn<Percurso, String> destinoColumn;

    @FXML
    private TableColumn<Percurso, Void> acoesColumn;

    public static ObservableList<Percurso> percursoList;

    public static final String EDIT_SVG = "M11.8125 2.6875L10.5938 3.90625L8.09375 1.40625L9.3125 0.1875C9.4375 0.0625 9.59375 0 9.78125 0C9.96875 0 10.125 0.0625 10.25 0.1875L11.8125 1.75C11.9375 1.875 12 2.03125 12 2.21875C12 2.40625 11.9375 2.5625 11.8125 2.6875ZM0 9.5L7.375 2.125L9.875 4.625L2.5 12H0V9.5Z";

    public static final String DELETE_SVG = "M9.3125 0.65625V2H0V0.65625H2.3125L3 0H6.3125L7 0.65625H9.3125ZM0.65625 10.6562V2.65625H8.65625V10.6562C8.65625 11.0104 8.52083 11.3229 8.25 11.5938C7.97917 11.8646 7.66667 12 7.3125 12H2C1.64583 12 1.33333 11.8646 1.0625 11.5938C0.791667 11.3229 0.65625 11.0104 0.65625 10.6562Z";

    private static boolean isEdit = false;

    public static Percurso itemAtt;

    private ArrayList<Parada> paradasDisponiveis;

    @FXML
    public void voltar() {
        setScreen();
    }

    @FXML
    public void voltarDashboard() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/dashboard-view.fxml", "Home");
    }

    @FXML
    public void incluirPercurso() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/incluirPercurso-view.fxml", "Percursos");
    }

    @FXML
    public void logout() {
        MainApplication.logout();
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }

    @FXML
    public void confirmar() {
        String origemSelecionada = origemComboBox.getValue();
        String destinoSelecionado = destinoComboBox.getValue();

        boolean origemValida = origemSelecionada != null && !origemSelecionada.isEmpty();
        boolean destinoValido = destinoSelecionado != null && !destinoSelecionado.isEmpty();

        origemMessage.setVisible(!origemValida);
        destinoMessage.setVisible(!destinoValido);

        if (origemValida && destinoValido) {
            if (origemSelecionada.equals(destinoSelecionado)) {
                errorMessage("Origem e destino não podem ser iguais!");
                return;
            }

            // Buscar as paradas selecionadas
            Parada origem = buscarParadaPorNome(origemSelecionada);
            Parada destino = buscarParadaPorNome(destinoSelecionado);

            if (origem != null && destino != null) {
                Percurso novoPercurso = new Percurso(origem, destino);

                if (Database.addRoute(novoPercurso)) {
                    successMessage("Percurso adicionado com sucesso!");
                    Time hour = Time.valueOf(LocalTime.of(12,30));
                    HorarioDiaPercurso hdp = new HorarioDiaPercurso(hour,"seg", novoPercurso);
                    Database.addHDP(hdp);
                    carregarDados();
                } else {
                    errorMessage("Não foi possível adicionar o percurso!\nTente novamente.");
                }
            } else {
                errorMessage("Erro ao localizar as paradas selecionadas!");
            }
        }
    }

    @FXML
    public void atualizar() {
        String novaOrigem = origemComboBox.getValue();
        String novoDestino = destinoComboBox.getValue();

        boolean origemValida = novaOrigem != null && !novaOrigem.isEmpty();
        boolean destinoValido = novoDestino != null && !novoDestino.isEmpty();

        origemMessage.setVisible(!origemValida);
        destinoMessage.setVisible(!destinoValido);

        if (origemValida && destinoValido) {
            if (novaOrigem.equals(novoDestino)) {
                errorMessage("Origem e destino não podem ser iguais!");
                return;
            }

            Parada origem = buscarParadaPorNome(novaOrigem);
            Parada destino = buscarParadaPorNome(novoDestino);

            if (origem != null && destino != null) {
                itemAtt.origem = origem;
                itemAtt.destino = destino;

                if (Database.updateRoute(itemAtt)) {
                    successMessage("Percurso atualizado com sucesso!");
                    carregarDados();
                    carregarTabela();
                } else {
                    errorMessage("Não foi possível atualizar o percurso!\nTente novamente.");
                }
            } else {
                errorMessage("Erro ao localizar as paradas selecionadas!");
            }
        }
        isEdit = false;
    }

    private Parada buscarParadaPorNome(String nome) {
        for (Parada parada : paradasDisponiveis) {
            if (parada.localizacao.equals(nome)) {
                return parada;
            }
        }
        return null;
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

    private void excluirPercurso(Percurso item) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir este percurso?");
        confirmacao.setContentText(item.origem.localizacao + " → " + item.destino.localizacao);

        confirmacao.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (Database.removeRoute(item)) {
                    successMessage("Percurso excluído com sucesso!");
                    carregarDados();
                    carregarTabela();
                } else {
                    errorMessage("Não foi possível excluir o percurso!\nEle pode estar vinculado a viagens.");
                }
            }
        });
    }

    private void editarPercurso(Percurso item) {
        isEdit = true;
        itemAtt = item;
        ScreenManager.getInstance().showScreen("/com/user/fmuser/atualizarPercurso-view.fxml", "Percursos");
    }

    public static void carregarDados() {
        percursoList = FXCollections.observableArrayList();
        try {
            ArrayList<Percurso> percursos = Database.retrieveRoutes();
            if (percursos != null) {
                percursoList.addAll(percursos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarParadas() {
        paradasDisponiveis = new ArrayList<>();
        try {
            ArrayList<Location> todasLocalizacoes = Database.retrieveLocations();
            if (todasLocalizacoes != null) {
                // Filtrar apenas as Paradas (remover Ciclovias)
                for (Location loc : todasLocalizacoes) {
                    if (loc instanceof Parada) {
                        paradasDisponiveis.add((Parada) loc);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherComboBoxes() {
        if (origemComboBox != null && destinoComboBox != null) {
            ObservableList<String> nomesParadas = FXCollections.observableArrayList();

            for (Parada parada : paradasDisponiveis) {
                nomesParadas.add(parada.localizacao);
            }

            origemComboBox.setItems(nomesParadas);
            destinoComboBox.setItems(nomesParadas);
        }
    }

    public static void setScreen() {
        if (percursoList == null || percursoList.isEmpty()) {
            ScreenManager.getInstance().showScreen("/com/user/fmuser/percursoAdmin-view.fxml", "Percursos");
            return;
        }
        ScreenManager.getInstance().showScreen("/com/user/fmuser/percursoAdminFilled-view.fxml", "Percursos");
    }

    public void carregarTabela() {
        if (percursoTable != null) {
            percursoTable.setItems(percursoList);
        }
        if (percursoList == null || percursoList.isEmpty()) {
            ScreenManager.getInstance().showScreen("/com/user/fmuser/percursoAdmin-view.fxml", "Percursos");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Carregar dados iniciais
        carregarDados();

        // Carregar paradas disponíveis para os ComboBoxes
        carregarParadas();
        preencherComboBoxes();

        if (isEdit && origemComboBox != null && destinoComboBox != null) {
            origemComboBox.setValue(itemAtt.origem.localizacao);
            destinoComboBox.setValue(itemAtt.destino.localizacao);
        }

        if (percursoTable != null) {
            origemColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().origem.localizacao));

            destinoColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().destino.localizacao));

            acoesColumn.setCellFactory(col -> new TableCell<Percurso, Void>() {
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
                        Percurso item = getTableView().getItems().get(getIndex());
                        editarPercurso(item);
                    });

                    deleteButton.setOnAction(event -> {
                        Percurso item = getTableView().getItems().get(getIndex());
                        excluirPercurso(item);
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