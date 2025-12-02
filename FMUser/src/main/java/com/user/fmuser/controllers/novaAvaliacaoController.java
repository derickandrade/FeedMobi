package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.*;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;


public class novaAvaliacaoController {

    public static int tipoAvaliacao = 0;
    protected static String comentario;
    protected static LocalDate date; // O padrão é YYYY-MM-DD
    protected static String time;
    protected static String texto = "Selecionar";
    protected static String origin;
    protected static String destination;
    protected static String vehicleCode;
    private static int avaliacao = 0;
    public Image estrelaAcesa;
    public Image estrelaApagada;

    public static File image;

    @FXML
    public ImageView logoutIcon;
    @FXML
    public Button voltarButton;
    @FXML
    public Button logoutButton;
    @FXML
    public Button carregarImagemButton;
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextArea comentarioField;
    @FXML
    public ComboBox<String> originMenu;
    @FXML
    public ComboBox<String> destinationMenu;
    @FXML
    public ComboBox<String> localMenu;
    @FXML
    public TextField horaField;
    @FXML
    public TextField vehicleCodeField;
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
    public Label veiculoMessage;
    @FXML
    public Label origemMessage;
    @FXML
    public Label destinoMessage;
    @FXML
    public Label dataMessage;
    @FXML
    public Label horaMessage;
    @FXML
    public Label notaMessage;
    @FXML
    public Label comentarioMessage;
    @FXML
    public Label imageName;
    @FXML
    public Label cicloviaMessage;

    protected String horaBuffer;

    public static void resetarCampos() {
        avaliacao = 0;
        tipoAvaliacao = 0;
        texto = "Selecionar";
        comentario = null;
        date = null;
        origin = null;
        destination = null;
        vehicleCode = null;
    }

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

            originMenu.getItems().addAll("UnB", "Rodoviária");
            destinationMenu.getItems().addAll("UnB", "Rodoviária");
        } else if (tipoAvaliacao == 2) {
            texto = "Parada/Estação";
            ArrayList<Location> locations = Database.retrieveLocations();
            ArrayList<String> paradas = new ArrayList<>();
            assert locations != null;
            for (Location location : locations) {
                if (location instanceof  Parada) {
                    paradas.add(location.localizacao);
                }
            }
            if (paradas != null) {
                localMenu.getItems().addAll(paradas);
            }

        } else if (tipoAvaliacao == 3) {
            texto = "Ciclovia";
            ArrayList<Location> locations = Database.retrieveLocations();
            ArrayList<String> ciclovias = new ArrayList<>();
            assert locations != null;
            for (Location location : locations) {
                if (location instanceof Ciclovia) {
                    ciclovias.add(location.localizacao);
                }
            }
            if (ciclovias != null) {
                localMenu.getItems().addAll(ciclovias);
            }
        } else {
            texto = null;
        }
        tipoAvaliacaoMenu.setValue(texto);

        if (date == null) {
            date = LocalDate.now();
        }
        if (datePicker != null) {
            datePicker.setValue(date);
        }

        if (comentario != null) {
            comentarioField.setText(comentario);
        }
    }

    @FXML
    public void carregarImagem() {
        carregarImagemButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir Arquivo de Imagem");

            // Define filtros de extensão para mostrar apenas arquivos de imagem
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Arquivos de Imagem", "*.png", "*.jpg", "*.gif"),
                    new FileChooser.ExtensionFilter("Todos os Arquivos", "*.*")
            );

            Stage newStage = new Stage();
            image = fileChooser.showOpenDialog(newStage);
            newStage.close();
            if (image != null) {
                imageName.setText(image.getName());
            }
        });

    }

    @FXML
    public void removeImage() {
        if (image != null) {
            image = null;
            imageName.setText("Selecione uma imagem");
        }
    }

    @FXML
    public void setDate() {
        date = datePicker.getValue();
        dataMessage.setVisible(!(date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now())));

    }

    @FXML
    public void setLocal() {
        //System.out.println(localMenu.ge);
    }

    @FXML
    public void apagarMensagem() {
        veiculoMessage.setVisible(false);
    }

    @FXML
    public void logout() {
        MainApplication.logout();
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

    public void successMessage(String frase) {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("SUCESSO");
        success.setHeaderText(frase);
        success.showAndWait();
    }

    public void errorMessage(String frase) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("ERRO");
        error.setHeaderText(frase);
        error.showAndWait();
    }

    @FXML
    public void handleAvaliarViagem() {
        vehicleCode = vehicleCodeField.getText();
        origin = originMenu.getValue();
        destination = destinationMenu.getValue();
        date = datePicker.getValue();
        time = horaField.getText() + ":00";
        comentario = comentarioField.getText();

        boolean validVehicleCode = !(vehicleCode.isEmpty());
        boolean validOrigin = origin == null ? false : !origin.isEmpty();
        boolean validDestination = destination == null ? false : !destination.isEmpty();
        boolean validDate = date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now());
        boolean validComentario = !comentario.isEmpty();

        veiculoMessage.setVisible(!validVehicleCode);
        origemMessage.setVisible(!validOrigin);
        destinoMessage.setVisible(!validDestination);
        dataMessage.setVisible(!validDate);
        horaMessage.setVisible(!validTime(time));
        notaMessage.setVisible(avaliacao == 0);
        comentarioMessage.setVisible(!validComentario);

        if (validVehicleCode &&
                validOrigin &&
                validDestination &&
                validDate &&
                validTime(time) &&
                avaliacao != 0 &&
                validComentario)
        {
            try {
                int tripCode = Database.retrieveTripCode(Integer.getInteger(vehicleCode), origin, destination, date.toString(), java.sql.Time.valueOf(time));
                Avaliacao review = new Avaliacao(tripCode, Avaliacao.TargetType.Viagem, avaliacao, comentario, MainApplication.usuarioSessao.getCPF());
                if (Database.addReview(review)) {
                    successMessage("Avaliação realizada com sucesso!");
                } else {
                    errorMessage("Ocorreu um erro ao realizar a avaliação. Tente novamente!");
                }
            } catch (Exception e) {
                errorMessage("Ocorreu um erro ao realizar a avaliação. Tente novamente!");
            }
        }
    }

    private boolean validTime(String time) {
        if (time.isEmpty() || time == null) return false;
        try {
            java.sql.Time sqlTime = java.sql.Time.valueOf(time);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @FXML
    public void handleAvaliarCiclovia() {
        String cicloviaNome = localMenu.getValue();
        String comentario = comentarioField.getText();
        boolean validCiclovia = cicloviaNome != null && !cicloviaNome.isEmpty();
        boolean validComentario = comentario != null && !comentario.isEmpty();
        cicloviaMessage.setVisible(!validCiclovia);
        notaMessage.setVisible(avaliacao == 0);
        comentarioMessage.setVisible(!validComentario);
        if (validCiclovia &&
                validComentario &&
                avaliacao != 0) {
            Ciclovia ciclovia = (Ciclovia) Database.retrieveLocation(cicloviaNome, Location.LocationType.Ciclovia);
            int nota = avaliacao;
            Avaliacao avaliacao = new Avaliacao(ciclovia.codigo, Avaliacao.TargetType.Ciclovia, nota, comentario, MainApplication.usuarioSessao.getCPF());
            if (Database.addReview(avaliacao)) {
                if (image != null) {
                    if (Database.addImage(avaliacao, image)) {
                        successMessage("Avaliação realizada com sucesso!");
                    } else {
                        successMessage("Avaliação realizada, ocorreu um erro com o upload da imagem.");
                    }
                }
                successMessage("Avaliação realizada com sucesso!");
            } else {
                errorMessage("Ocorreu um erro realizar a avaliação. Tente novamente!");
            }
        }
    }

    @FXML
    public void handleAvaliarParada() {
        String paradaNome = localMenu.getValue();
        String comentario = comentarioField.getText();
        boolean validParada = paradaNome != null && !paradaNome.isEmpty();
        boolean validComentario = comentario != null && !comentario.isEmpty();
        cicloviaMessage.setVisible(!validParada);
        notaMessage.setVisible(avaliacao == 0);
        comentarioMessage.setVisible(!validComentario);
        if (validParada &&
                validComentario &&
                avaliacao != 0) {
            Parada parada = (Parada) Database.retrieveLocation(paradaNome, Location.LocationType.Parada);
            int nota = avaliacao;
            Avaliacao avaliacao = new Avaliacao(parada.codigo, Avaliacao.TargetType.Parada, nota, comentario, MainApplication.usuarioSessao.getCPF());
            if (Database.addReview(avaliacao)) {
                if (image != null) {
                    if (Database.addImage(avaliacao, image)) {
                        successMessage("Avaliação realizada com sucesso!");
                    } else {
                        successMessage("Avaliação realizada, ocorreu um erro com o upload da imagem.");
                    }
                }
                successMessage("Avaliação realizada com sucesso!");
            } else {
                errorMessage("Ocorreu um erro realizar a avaliação. Tente novamente!");
            }
        }
    }

    @FXML
    public void voltarHome() {
        String tela = "/com/user/fmuser/home-view.fxml";
        if (MainApplication.usuarioSessao.isAdmin()) {
            tela = "/com/user/fmuser/dashboard-view.fxml";
        }
        ScreenManager.getInstance().showScreen(tela, "Home");
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

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int valor) {
        avaliacao = valor;
        notaMessage.setVisible(false);
    }

    @FXML
    public void setPlaca() {
        vehicleCode = vehicleCodeField.getText();
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
