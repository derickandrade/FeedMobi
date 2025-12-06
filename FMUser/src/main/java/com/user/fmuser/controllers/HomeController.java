package com.user.fmuser.controllers;

import com.user.fmuser.MainApplication;
import com.user.fmuser.models.Avaliacao;
import com.user.fmuser.models.Database;
import com.user.fmuser.models.Location;
import com.user.fmuser.utils.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Label userNameLabel;

    @FXML
    private TableView<AvaliacaoTableData> avaliacoesTable;

    @FXML
    private TableColumn<AvaliacaoTableData, String> tipoColumn;

    @FXML
    private TableColumn<AvaliacaoTableData, String> localViagemColumn;

    @FXML
    private TableColumn<AvaliacaoTableData, Integer> notaColumn;

    @FXML
    private TableColumn<AvaliacaoTableData, Void> imagemColumn;

    @FXML
    private TableColumn<AvaliacaoTableData, String> comentarioColumn;

    public static ObservableList<Avaliacao> avaliacoes = FXCollections.observableArrayList();

    private final Image imgIcon = new Image(getClass().getResource("/com/user/fmuser/images/img.png").toExternalForm());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userName = MainApplication.usuarioSessao.nome + ' ' + MainApplication.usuarioSessao.sobrenome;
        userNameLabel.setText("Olá, " + userName + "!");
        carregarDados();
        if (avaliacoesTable != null) {
            configurarTabela();
            carregarTabelaAvaliacoes();
        }
    }

    private void configurarTabela() {
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        localViagemColumn.setCellValueFactory(new PropertyValueFactory<>("localViagem"));

        notaColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));

        comentarioColumn.setCellValueFactory(new PropertyValueFactory<>("comentario"));

        imagemColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btnVerImagem = new Button();

            {
                ImageView imgView = new ImageView();
                imgView.setImage(imgIcon);
                imgView.setFitHeight(20.0);
                imgView.setFitWidth(20.0);
                btnVerImagem.setGraphic(imgView);
                btnVerImagem.getStyleClass().add("tertiaryButton");
                btnVerImagem.setMaxSize(20, 20);
                btnVerImagem.setOnAction(event -> {
                    AvaliacaoTableData data = getTableView().getItems().get(getIndex());
                    if (data.getAvaliacao() != null) {
                        mostrarImagem(data.getAvaliacao());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    AvaliacaoTableData data = getTableView().getItems().get(getIndex());
                    // Só mostrar botão se for Ciclovia ou Parada
                    if (data.getTipo().equals("Ciclovia") || data.getTipo().equals("Parada")) {
                        setGraphic(btnVerImagem);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });
    }

    private void carregarTabelaAvaliacoes() {
        ObservableList<AvaliacaoTableData> tableData = FXCollections.observableArrayList();

        for (Avaliacao av : avaliacoes) {
            String tipo = av.getTipo();
            String localViagem;

            // Determinar o que mostrar na coluna Local/Viagem
            if (av.getTipoAlvo() == Avaliacao.TargetType.Viagem) {
                localViagem = "Viagem #" + av.getCodigoAlvo();
            } else {
                // Buscar a localização do banco de dados por código
                Location location = null;
                if (av.getTipoAlvo() == Avaliacao.TargetType.Ciclovia) {
                    location = Database.retrieveLocation(
                            av.getCodigoAlvo(),
                            Location.LocationType.Ciclovia
                    );
                } else if (av.getTipoAlvo() == Avaliacao.TargetType.Parada) {
                    location = Database.retrieveLocation(
                            av.getCodigoAlvo(),
                            Location.LocationType.Parada
                    );
                }

                // Se não encontrou, usar o código
                localViagem = (location != null) ? location.localizacao : "Código: " + av.getCodigoAlvo();
            }

            tableData.add(new AvaliacaoTableData(
                    tipo,
                    localViagem,
                    av.getNota(),
                    av.getTexto(),
                    av
            ));
        }

        avaliacoesTable.setItems(tableData);
    }

    private void mostrarImagem(Avaliacao avaliacao) {
        try {
            Image imagem = Database.retrieveImage(avaliacao);

            if (imagem != null) {
                // Criar janela para mostrar a imagem
                Stage imageStage = new Stage();
                imageStage.setTitle("Imagem da Avaliação");

                ImageView imageView = new ImageView(imagem);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(600);
                imageView.setFitHeight(600);

                VBox vbox = new VBox(imageView);
                vbox.setStyle("-fx-padding: 10; -fx-alignment: center;");

                Scene scene = new Scene(vbox);
                imageStage.setScene(scene);
                imageStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sem Imagem");
                alert.setHeaderText(null);
                alert.setContentText("Esta avaliação não possui imagem anexada.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao carregar a imagem.");
            alert.showAndWait();
        }
    }

    public static void setScreen() {
        String path = (avaliacoes != null && !avaliacoes.isEmpty())
                ? "/com/user/fmuser/homeFilled-view.fxml"
                : "/com/user/fmuser/home-view.fxml";

        ScreenManager.getInstance().showScreen(path, "Home");
    }

    public static void carregarDados() {
        try {
            avaliacoes.clear();

            ArrayList<Avaliacao> listaAvaliacoes = Database.retrieveReviews(
                    MainApplication.usuarioSessao.getCPF()
            );

            if (listaAvaliacoes != null && !listaAvaliacoes.isEmpty()) {
                avaliacoes.addAll(listaAvaliacoes);
            } else {
                System.out.println("Nenhuma avaliação encontrada");
            }

        } catch (Exception e) {
            System.err.println("Erro ao carregar avaliações: " + e.getMessage());
            e.printStackTrace();
            avaliacoes.clear();
        }
    }

    @FXML
    public void logout() {
        handleLogout();
    }

    public static void handleLogout() {
        novaAvaliacaoController.resetarCampos();
        avaliacoes.clear();
        MainApplication.logout();
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }

    @FXML
    public void irParaAvaliacao() {
        String telaAvaliacao;
        int tipoAvaliacao = novaAvaliacaoController.tipoAvaliacao;

        switch (tipoAvaliacao) {
            case 1:
                telaAvaliacao = "/com/user/fmuser/viagem-view.fxml";
                break;
            case 2:
                telaAvaliacao = "/com/user/fmuser/infraestrutura-view.fxml";
                break;
            case 3:
                telaAvaliacao = "/com/user/fmuser/ciclovia-view.fxml";
                break;
            default:
                telaAvaliacao = "/com/user/fmuser/novaAvaliacao-view.fxml";
        }

        ScreenManager.getInstance().showScreen(telaAvaliacao, "Nova Avaliação");
    }

    @FXML
    public void atualizarCadastro() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/atualizarCadastro-view.fxml", "Meu Cadastro");
    }

    // Classe interna para dados da tabela
    public static class AvaliacaoTableData {
        private final String tipo;
        private final String localViagem;
        private final Integer nota;
        private final String comentario;
        private final Avaliacao avaliacao;

        public AvaliacaoTableData(String tipo, String localViagem, Integer nota, String comentario, Avaliacao avaliacao) {
            this.tipo = tipo;
            this.localViagem = localViagem;
            this.nota = nota;
            this.comentario = comentario;
            this.avaliacao = avaliacao;
        }

        public String getTipo() {
            return tipo;
        }

        public String getLocalViagem() {
            return localViagem;
        }

        public Integer getNota() {
            return nota;
        }

        public String getComentario() {
            return comentario;
        }

        public Avaliacao getAvaliacao() {
            return avaliacao;
        }
    }
}