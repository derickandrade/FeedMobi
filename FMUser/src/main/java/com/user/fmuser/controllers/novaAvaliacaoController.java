package com.user.fmuser.controllers;

import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;

public class novaAvaliacaoController {

    private int avaliacao = 0;

    public static int tipoAvaliacao = 0;

    protected String texto = "Selecionar";

    public Image estrelaAcesa;

    public Image estrelaApagada;

    @FXML
    public ImageView logoutIcon;

    @FXML
    public Button logoutButton;

    @FXML
    public ImageView star1;

    @FXML
    public ImageView star2;

    @FXML
    public ImageView star3;

    @FXML
    public ImageView star4;

    @FXML
    public ImageView star5;

    @FXML
    public MenuButton tipoAvaliacaoMenu;

    @FXML
    public void initialize() { // Usei IA aqui para pegar a URL das imagens
        // Obter a URL do recurso usando o ClassLoader
        URL resourceUrl = getClass().getResource("/com/user/fmuser/images/star_fill.png");

        if (resourceUrl == null) {
            System.err.println("Erro: Recurso 'star_fill.png' não encontrado. Verifique o caminho.");
            return;
        }

        this.estrelaAcesa = new Image(resourceUrl.toExternalForm());

        resourceUrl = getClass().getResource("/com/user/fmuser/images/star.png");
        if (resourceUrl == null) {
            System.err.println("Erro: Recurso 'star_fill.png' não encontrado. Verifique o caminho.");
            return;
        }
        this.estrelaApagada = new Image(resourceUrl.toExternalForm());
        if (tipoAvaliacao != 0) {
            tipoAvaliacaoMenu.setStyle("-fx-text-fill: #5e605e;");
        }
        if (tipoAvaliacao == 1) {
            texto = "Percurso/Viagem";
        }
        else if (tipoAvaliacao == 2) {
            texto = "Parada/Estação";
        }
        else if (tipoAvaliacao == 3) {
            texto = "Ciclovia";
        }
        else {
            texto = "Selecionar";
            tipoAvaliacaoMenu.setStyle("-fx-text-fill: #B0B0B0;");
        }
        tipoAvaliacaoMenu.setText(texto);
    }

    @FXML
    public void logout() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }

    @FXML
    public void percursoSelecionado() {
        if (tipoAvaliacao != 1) {
            tipoAvaliacao = 1;
            ScreenManager.getInstance().showScreen("/com/user/fmuser/viagem-view.fxml", "Nova avaliação");
        }
    }

    public int getAvaliacao() {
        return this.avaliacao;
    }

    public void setAvaliacao(int valor) {
        this.avaliacao = valor;
    }

    public void apagarTodas() {
        star1.setImage(estrelaApagada);
        star2.setImage(estrelaApagada);
        star3.setImage(estrelaApagada);
        star4.setImage(estrelaApagada);
        star5.setImage(estrelaApagada);
    }

    @FXML
    public void definirNota() {
        apagarTodas();
        int nota = getAvaliacao();
        if (nota >= 1) {
            star1.setImage(estrelaAcesa);
        }
        if (nota >= 2) {
            star2.setImage(estrelaAcesa);
        }
        if (nota >= 3) {
            star3.setImage(estrelaAcesa);
        }
        if (nota >= 4) {
            star4.setImage(estrelaAcesa);
        }
        if (nota == 5) {
            star5.setImage(estrelaAcesa);
        }
    }

    @FXML
    public void acenderEstrela1() {
        star1.setImage(estrelaAcesa);
        star2.setImage(estrelaApagada);
        star3.setImage(estrelaApagada);
        star4.setImage(estrelaApagada);
        star5.setImage(estrelaApagada);
    }

    @FXML
    public void apagarEstrela1() {
        definirNota();
    }

    @FXML
    public void clicouEstrela1() {
        setAvaliacao(1);
        definirNota();
    }

    @FXML
    public void acenderEstrela2() {
        star1.setImage(estrelaAcesa);
        star2.setImage(estrelaAcesa);
        star3.setImage(estrelaApagada);
        star4.setImage(estrelaApagada);
        star5.setImage(estrelaApagada);
    }

    @FXML
    public void clicouEstrela2() {
        setAvaliacao(2);
        definirNota();
    }

    @FXML
    public void acenderEstrela3() {
        star1.setImage(estrelaAcesa);
        star2.setImage(estrelaAcesa);
        star3.setImage(estrelaAcesa);
        star4.setImage(estrelaApagada);
        star5.setImage(estrelaApagada);
    }

    @FXML
    public void clicouEstrela3() {
        setAvaliacao(3);
        definirNota();
    }

    @FXML
    public void acenderEstrela4() {
        star1.setImage(estrelaAcesa);
        star2.setImage(estrelaAcesa);
        star3.setImage(estrelaAcesa);
        star4.setImage(estrelaAcesa);
        star5.setImage(estrelaApagada);
    }

    @FXML
    public void clicouEstrela4() {
        setAvaliacao(4);
        definirNota();
    }

    @FXML
    public void acenderEstrela5() {
        star1.setImage(estrelaAcesa);
        star2.setImage(estrelaAcesa);
        star3.setImage(estrelaAcesa);
        star4.setImage(estrelaAcesa);
        star5.setImage(estrelaAcesa);
    }

    @FXML
    public void clicouEstrela5() {
        setAvaliacao(5);
        definirNota();
    }
}
