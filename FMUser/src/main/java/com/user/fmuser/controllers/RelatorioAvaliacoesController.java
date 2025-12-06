package com.user.fmuser.controllers;

import com.user.fmuser.models.Database;
import com.user.fmuser.models.RelatorioAvaliacao;
import com.user.fmuser.utils.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RelatorioAvaliacoesController implements Initializable {

    @FXML
    private TableView<RelatorioAvaliacao> relatorioTable;

    @FXML
    private TableColumn<RelatorioAvaliacao, Integer> codigoColumn;

    @FXML
    private TableColumn<RelatorioAvaliacao, String> tipoColumn;

    @FXML
    private TableColumn<RelatorioAvaliacao, Integer> codigoIdColumn;

    @FXML
    private TableColumn<RelatorioAvaliacao, Integer> notaColumn;

    @FXML
    private TableColumn<RelatorioAvaliacao, String> usuarioColumn;

    @FXML
    private TableColumn<RelatorioAvaliacao, String> textoColumn;

    @FXML
    private ComboBox<String> filtroTipoComboBox;

    @FXML
    private Label totalLabel;

    @FXML
    private Label mediaLabel;

    private ObservableList<RelatorioAvaliacao> relatorioData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabela();
        configurarFiltros();
        carregarDados();
    }

    private void configurarTabela() {
        codigoColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getCodigo()).asObject());

        tipoColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipoAvaliacao()));

        codigoIdColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getCodigoId()).asObject());

        notaColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getNota()).asObject());

        usuarioColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsuario()));

        textoColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTexto()));

        // Permitir quebra de linha na coluna de texto
        textoColumn.setCellFactory(tc -> {
            TableCell<RelatorioAvaliacao, String> cell = new TableCell<>();
            Label label = new Label();
            label.setWrapText(true);
            label.setMaxWidth(370);
            cell.setGraphic(label);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            label.textProperty().bind(cell.itemProperty());
            return cell;
        });
    }

    private void configurarFiltros() {
        filtroTipoComboBox.setItems(FXCollections.observableArrayList(
                "Todos",
                "Viagem",
                "Parada",
                "Ciclovia"
        ));
        filtroTipoComboBox.setValue("Todos");
    }

    private void carregarDados() {
        carregarDados(null);
    }

    private void carregarDados(String filtroTipo) {
        try {
            relatorioData.clear();

            ArrayList<RelatorioAvaliacao> lista;
            if (filtroTipo == null || filtroTipo.equals("Todos")) {
                lista = Database.retrieveRelatorioGeralAvaliacoes();
            } else {
                lista = Database.retrieveRelatorioGeralAvaliacoes(filtroTipo);
            }

            if (lista != null && !lista.isEmpty()) {
                relatorioData.addAll(lista);
                System.out.println("Carregadas " + relatorioData.size() + " avaliações");
            } else {
                System.out.println("Nenhuma avaliação encontrada");
            }

            relatorioTable.setItems(relatorioData);
            atualizarEstatisticas();

        } catch (Exception e) {
            System.err.println("Erro ao carregar relatório: " + e.getMessage());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao carregar relatório de avaliações.");
            alert.showAndWait();
        }
    }

    private void atualizarEstatisticas() {
        int total = relatorioData.size();
        double media = 0.0;

        if (total > 0) {
            int somaNotas = 0;
            for (RelatorioAvaliacao rel : relatorioData) {
                somaNotas += rel.getNota();
            }
            media = (double) somaNotas / total;
        }

        totalLabel.setText("Total de avaliações: " + total);
        mediaLabel.setText(String.format("Nota média: %.2f", media));
    }

    @FXML
    public void aplicarFiltro() {
        String filtroSelecionado = filtroTipoComboBox.getValue();
        if (filtroSelecionado != null && !filtroSelecionado.equals("Todos")) {
            carregarDados(filtroSelecionado);
        } else {
            carregarDados(null);
        }
    }

    @FXML
    public void limparFiltro() {
        filtroTipoComboBox.setValue("Todos");
        carregarDados(null);
    }

    @FXML
    public void atualizarTabela() {
        String filtroAtual = filtroTipoComboBox.getValue();
        if (filtroAtual.equals("Todos")) {
            carregarDados(null);
        } else {
            carregarDados(filtroAtual);
        }
    }

    @FXML
    public void voltar() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/dashboard-view.fxml", "Home");
    }
}