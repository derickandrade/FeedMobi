package com.user.fmuser.controllers;

import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.time.LocalDate;


public class novaAvaliacaoController {

    private static int avaliacao = 0;

    public static int tipoAvaliacao = 0;

    protected static String comentario;

    protected static LocalDate data; // O padrão é YYYY-MM-DD

    protected static String hora;

    protected String horaBuffer;

    protected static String texto = "Selecionar";

    protected static String origem;

    protected static String destino;

    protected static String placa;

    public Image estrelaAcesa;

    public Image estrelaApagada;

    @FXML
    public ImageView logoutIcon;

    @FXML
    public Button voltarButton;

    @FXML
    public Button logoutButton;

    @FXML
    public DatePicker datePicker;

    @FXML
    public TextArea comentarioField;

    @FXML
    public ComboBox<String> origemMenu;

    @FXML
    public ComboBox<String> destinoMenu;

    @FXML
    public ComboBox<String> localMenu;

    @FXML
    public ComboBox<String> cicloviaMenu;

    @FXML
    public TextField horaField;

    @FXML
    public TextField placaField;

    @FXML
    public ComboBox<String> tipoAvaliacaoMenu;

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
    public void initialize() { // Usei IA aqui para pegar a URL das imagens
        // Obter a URL do recurso usando o ClassLoader
        URL resourceUrl = getClass().getResource("/com/user/fmuser/images/star_fill.png");

        if (resourceUrl == null) {
            System.err.println("Erro: Recurso 'star_fill.png' não encontrado. Verifique o caminho.");
            return;
        }

        this.estrelaAcesa = new Image(resourceUrl.toExternalForm());

        resourceUrl = getClass().getResource("/com/user/fmuser/images/star.png");

        tipoAvaliacaoMenu.getItems().addAll("Percurso/Viagem", "Parada/Estação", "Ciclovia");

        if (resourceUrl == null) {
            System.err.println("Erro: Recurso 'star_fill.png' não encontrado. Verifique o caminho.");
            return;
        }
        this.estrelaApagada = new Image(resourceUrl.toExternalForm());

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
            texto = null;
        }
        tipoAvaliacaoMenu.setValue(texto);

        if (data == null) {
            data = LocalDate.now();
        }
        datePicker.setValue(data);

        if (comentario != null) {
            comentarioField.setText(comentario);
        }

    }

    @FXML
    public void setDate() {
        data = datePicker.getValue();
    }

    @FXML
    public void setLocal() {
        //System.out.println(localMenu.ge);
    }

    @FXML
    public void logout() {
        HomeController.handleLogout();
    }

    @FXML
    public void tratarHora() {

    }

    @FXML
    public void handleLocal() {
        System.out.println(localMenu.getValue());
    }

    @FXML
    public void handleTipoAvaliacao() {
        String selecionado = tipoAvaliacaoMenu.getValue();
        System.out.println(selecionado);
        switch (selecionado) {
            case "Percurso/Viagem":
                percursoSelecionado();
                break;
            case "Parada/Estação":
                paradaSelecionada();
                break;
            case "Ciclovia":
                cicloviaSelecionada();
                break;
        }
    }

    @FXML
    public void percursoSelecionado() {
        if (tipoAvaliacao != 1) {
            tipoAvaliacao = 1;
            ScreenManager.getInstance().showScreen("/com/user/fmuser/viagem-view.fxml", "Nova avaliação");
        }
    }

    @FXML
    public void paradaSelecionada() {
        if (tipoAvaliacao != 2) {
            tipoAvaliacao = 2;
            ScreenManager.getInstance().showScreen("/com/user/fmuser/infraestrutura-view.fxml", "Nova avaliação");
        }
    }

    @FXML
    public void cicloviaSelecionada() {
        if (tipoAvaliacao != 3) {
            tipoAvaliacao = 3;
            ScreenManager.getInstance().showScreen("/com/user/fmuser/ciclovia-view.fxml", "Nova avaliação");
        }
    }

    @FXML
    public void voltarHome() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/home-view.fxml", "Home");
    }

    @FXML
    public void cancelarAvaliacao() {
        resetarCampos();
        voltarHome();
    }

    @FXML
    public void setComentario() {
        comentario = comentarioField.getText();
    }

    public static void resetarCampos() {
        avaliacao = 0;
        tipoAvaliacao = 0;
        texto = "Selecionar";
        comentario = null;
        data = null;
        origem = null;
        destino = null;
        placa = null;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int valor) {
        avaliacao = valor;
    }

    @FXML
    public void setPlaca() {
        placa = placaField.getText();
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
