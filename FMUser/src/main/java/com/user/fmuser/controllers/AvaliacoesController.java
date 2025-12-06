package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.Usuario;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;
import com.user.fmuser.models.Avaliacao;
import javafx.scene.control.cell.PropertyValueFactory;

public class AvaliacoesController implements Initializable {

    @FXML
    protected TableView<Avaliacao> avaliacoesTable;

    @FXML
    private TableColumn<Avaliacao, Integer> notaColumn;

    @FXML
    private TableColumn<Avaliacao, String> textoColumn;

    @FXML
    private TableColumn<Avaliacao, String> tipoColumn;

    @FXML
    public void novaAvaliacao() {
        String telaAvaliacao;
        int tipoAvaliacao = novaAvaliacaoController.tipoAvaliacao;
        telaAvaliacao = "/com/user/fmuser/novaAvaliacao-view.fxml";
        if (tipoAvaliacao == 1) {
            telaAvaliacao = "/com/user/fmuser/viagem-view.fxml";
        }
        else if (tipoAvaliacao == 2) {
            telaAvaliacao = "/com/user/fmuser/infraestrutura-view.fxml";
        }
        else if (tipoAvaliacao == 3) {
            telaAvaliacao = "/com/user/fmuser/ciclovia-view.fxml";
        }
        ScreenManager.getInstance().showScreen(telaAvaliacao, "Nova Avaliação");
    }

    @FXML
    public void logout() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Nova Avaliação");
        MainApplication.logout();
    }

    @FXML
    public void voltarHome() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/dashboard-view.fxml", "Home");
    }

    @FXML
    public void cancelarAvaliacao() {
        novaAvaliacaoController.resetarCampos();
        ScreenManager.getInstance().showScreen("/com/user/fmuser/dashboard-view.fxml", "Home");
    }

    private void configurarTabela() {
        // Configurar as colunas (ajuste os nomes de acordo com sua classe Avaliacao)
        notaColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));
        textoColumn.setCellValueFactory(new PropertyValueFactory<>("texto"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        avaliacoesTable.setItems(HomeController.avaliacoes);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (avaliacoesTable != null) {
            HomeController.carregarDados();
            configurarTabela();
        }
    }
}
