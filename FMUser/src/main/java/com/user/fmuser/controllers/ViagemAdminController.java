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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViagemAdminController implements Initializable {

    @FXML
    public ComboBox<String> motoristaComboBox;

    @FXML
    public ComboBox<String> cobradorComboBox;

    @FXML
    public ComboBox<String> hdpComboBox;

    @FXML
    public ComboBox<String> veiculoComboBox;

    @FXML
    public Label motoristaMessage;

    @FXML
    public Label cobradorMessage;

    @FXML
    public Label hdpMessage;

    @FXML
    public Label veiculoMessage;

    @FXML
    private TableView<Viagem> viagemTable;

    @FXML
    private TableColumn<Viagem, String> motoristaColumn;

    @FXML
    private TableColumn<Viagem, String> cobradorColumn;

    @FXML
    private TableColumn<Viagem, String> hdpColumn;

    @FXML
    private TableColumn<Viagem, String> veiculoColumn;

    @FXML
    private TableColumn<Viagem, Void> acoesColumn;

    public static ObservableList<Viagem> viagemList;

    public static final String EDIT_SVG = "M11.8125 2.6875L10.5938 3.90625L8.09375 1.40625L9.3125 0.1875C9.4375 0.0625 9.59375 0 9.78125 0C9.96875 0 10.125 0.0625 10.25 0.1875L11.8125 1.75C11.9375 1.875 12 2.03125 12 2.21875C12 2.40625 11.9375 2.5625 11.8125 2.6875ZM0 9.5L7.375 2.125L9.875 4.625L2.5 12H0V9.5Z";

    public static final String DELETE_SVG = "M9.3125 0.65625V2H0V0.65625H2.3125L3 0H6.3125L7 0.65625H9.3125ZM0.65625 10.6562V2.65625H8.65625V10.6562C8.65625 11.0104 8.52083 11.3229 8.25 11.5938C7.97917 11.8646 7.66667 12 7.3125 12H2C1.64583 12 1.33333 11.8646 1.0625 11.5938C0.791667 11.3229 0.65625 11.0104 0.65625 10.6562Z";

    private static boolean isEdit = false;

    public static Viagem itemAtt;

    private ArrayList<Funcionario> motoristasDisponiveis;
    private ArrayList<Funcionario> cobradoresDisponiveis;
    private ArrayList<HorarioDiaPercurso> hdpDisponiveis;
    private ArrayList<Veiculo> veiculosDisponiveis;

    @FXML
    public void voltar() {
        isEdit = false;
        setScreen();
    }

    @FXML
    public void voltarDashboard() {
        isEdit = false;
        ScreenManager.getInstance().showScreen("/com/user/fmuser/dashboard-view.fxml", "Home");
    }

    @FXML
    public void incluirViagem() {
        isEdit = false;
        ScreenManager.getInstance().showScreen("/com/user/fmuser/incluirViagem-view.fxml", "Viagens");
    }

    @FXML
    public void logout() {
        isEdit = false;
        MainApplication.logout();
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }

    @FXML
    public void confirmar() {
        String motoristaSelecionado = motoristaComboBox.getValue();
        String cobradorSelecionado = cobradorComboBox.getValue();
        String hdpSelecionado = hdpComboBox.getValue();
        String veiculoSelecionado = veiculoComboBox.getValue();

        boolean motoristaValido = motoristaSelecionado != null && !motoristaSelecionado.isEmpty();
        boolean hdpValido = hdpSelecionado != null && !hdpSelecionado.isEmpty();
        boolean veiculoValido = veiculoSelecionado != null && !veiculoSelecionado.isEmpty();

        motoristaMessage.setVisible(!motoristaValido);
        hdpMessage.setVisible(!hdpValido);
        veiculoMessage.setVisible(!veiculoValido);

        if (motoristaValido && hdpValido && veiculoValido) {
            Funcionario motorista = buscarFuncionarioPorNome(motoristaSelecionado, true);
            Funcionario cobrador = (cobradorSelecionado != null && !cobradorSelecionado.isEmpty() && !cobradorSelecionado.equals("Sem cobrador"))
                    ? buscarFuncionarioPorNome(cobradorSelecionado, false)
                    : null;
            HorarioDiaPercurso hdp = buscarHDPPorDescricao(hdpSelecionado);
            Veiculo veiculo = buscarVeiculoPorNumero(veiculoSelecionado);

            if (motorista != null && hdp != null && veiculo != null) {
                Viagem novaViagem = new Viagem(motorista, cobrador, hdp, veiculo);

                if (Database.addTrip(novaViagem)) {
                    successMessage("Viagem adicionada com sucesso!");
                    carregarDados();
                    setScreen();
                } else {
                    errorMessage("Não foi possível adicionar a viagem!\nTente novamente.");
                }
            } else {
                errorMessage("Erro ao localizar os dados selecionados!");
            }
        }
    }

    @FXML
    public void atualizar() {
        String novoMotorista = motoristaComboBox.getValue();
        String novoCobrador = cobradorComboBox.getValue();
        String novoHdp = hdpComboBox.getValue();
        String novoVeiculo = veiculoComboBox.getValue();

        boolean motoristaValido = novoMotorista != null && !novoMotorista.isEmpty();
        boolean hdpValido = novoHdp != null && !novoHdp.isEmpty();
        boolean veiculoValido = novoVeiculo != null && !novoVeiculo.isEmpty();

        motoristaMessage.setVisible(!motoristaValido);
        hdpMessage.setVisible(!hdpValido);
        veiculoMessage.setVisible(!veiculoValido);

        if (motoristaValido && hdpValido && veiculoValido) {
            Funcionario motorista = buscarFuncionarioPorNome(novoMotorista, true);
            Funcionario cobrador = (novoCobrador != null && !novoCobrador.isEmpty() && !novoCobrador.equals("Sem cobrador"))
                    ? buscarFuncionarioPorNome(novoCobrador, false)
                    : null;
            HorarioDiaPercurso hdp = buscarHDPPorDescricao(novoHdp);
            Veiculo veiculo = buscarVeiculoPorNumero(novoVeiculo);

            if (motorista != null && hdp != null && veiculo != null) {
                itemAtt.motorista = motorista;
                itemAtt.cobrador = cobrador;
                itemAtt.horarioDiaPercurso = hdp;
                itemAtt.veiculo = veiculo;

                if (Database.removeTrip(itemAtt.codigo) && Database.addTrip(itemAtt)) {
                    successMessage("Viagem atualizada com sucesso!");
                    carregarDados();
                    setScreen();
                } else {
                    errorMessage("Não foi possível atualizar a viagem!\nTente novamente.");
                }
            } else {
                errorMessage("Erro ao localizar os dados selecionados!");
            }
        }
        isEdit = false;
    }

    private Funcionario buscarFuncionarioPorNome(String nomeCompleto, boolean isMotorista) {
        ArrayList<Funcionario> lista = isMotorista ? motoristasDisponiveis : cobradoresDisponiveis;
        for (Funcionario func : lista) {
            String nome = func.nome + " " + func.sobrenome;
            if (nome.equals(nomeCompleto)) {
                return func;
            }
        }
        return null;
    }

    private HorarioDiaPercurso buscarHDPPorDescricao(String descricao) {
        for (HorarioDiaPercurso hdp : hdpDisponiveis) {
            String desc = formatarHDP(hdp);
            if (desc.equals(descricao)) {
                return hdp;
            }
        }
        return null;
    }

    private Veiculo buscarVeiculoPorNumero(String numero) {
        for (Veiculo veiculo : veiculosDisponiveis) {
            if (String.valueOf(veiculo.numero).equals(numero)) {
                return veiculo;
            }
        }
        return null;
    }

    private String formatarHDP(HorarioDiaPercurso hdp) {
        return hdp.getDia() + " - " + hdp.hora + " (" +
                hdp.percurso.origem.localizacao + " → " +
                hdp.percurso.destino.localizacao + ")";
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

    private void excluirViagem(Viagem item) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Deseja realmente excluir esta viagem?");
        confirmacao.setContentText("Motorista: " + item.motorista.nome + " " + item.motorista.sobrenome);

        confirmacao.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (Database.removeTrip(item.codigo)) {
                    successMessage("Viagem excluída com sucesso!");
                    carregarDados();
                    carregarTabela();
                } else {
                    errorMessage("Não foi possível excluir a viagem!\nTente novamente.");
                }
            }
        });
    }

    private void editarViagem(Viagem item) {
        isEdit = true;
        itemAtt = item;
        ScreenManager.getInstance().showScreen("/com/user/fmuser/atualizarViagem-view.fxml", "Viagens");
    }

    public static void carregarDados() {
        viagemList = FXCollections.observableArrayList();
        try {
            viagemList.addAll(Database.retrieveTrips());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarFuncionarios() {
        motoristasDisponiveis = new ArrayList<>();
        cobradoresDisponiveis = new ArrayList<>();

        try {
            ArrayList<Funcionario> todosFuncionarios = Database.retrieveEmployees();
            if (todosFuncionarios != null) {
                for (Funcionario func : todosFuncionarios) {
                    if (func.isMotorista) {
                        motoristasDisponiveis.add(func);
                    } else {
                        cobradoresDisponiveis.add(func);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarHDPs() {
        hdpDisponiveis = new ArrayList<>();
        try {
            hdpDisponiveis = Database.retrieveHDPs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarVeiculos() {
        veiculosDisponiveis = new ArrayList<>();
        try {
            veiculosDisponiveis = Database.retrieveVehicles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherComboBoxes() {
        if (motoristaComboBox != null) {
            ObservableList<String> nomesMotoristas = FXCollections.observableArrayList();
            for (Funcionario motorista : motoristasDisponiveis) {
                nomesMotoristas.add(motorista.nome + " " + motorista.sobrenome);
            }
            motoristaComboBox.setItems(nomesMotoristas);
        }

        if (cobradorComboBox != null) {
            ObservableList<String> nomesCobradores = FXCollections.observableArrayList();
            nomesCobradores.add("Sem cobrador");
            for (Funcionario cobrador : cobradoresDisponiveis) {
                nomesCobradores.add(cobrador.nome + " " + cobrador.sobrenome);
            }
            cobradorComboBox.setItems(nomesCobradores);
        }

        if (hdpComboBox != null) {
            ObservableList<String> hdps = FXCollections.observableArrayList();
            for (HorarioDiaPercurso hdp : hdpDisponiveis) {
                hdps.add(formatarHDP(hdp));
            }
            hdpComboBox.setItems(hdps);
        }

        if (veiculoComboBox != null) {
            ObservableList<String> veiculos = FXCollections.observableArrayList();
            for (Veiculo veiculo : veiculosDisponiveis) {
                veiculos.add(String.valueOf(veiculo.numero));
            }
            veiculoComboBox.setItems(veiculos);
        }
    }

    public static void setScreen() {
        if (viagemList == null || viagemList.isEmpty()) {
            ScreenManager.getInstance().showScreen("/com/user/fmuser/viagensAdmin-view.fxml", "Viagens");
            return;
        }
        ScreenManager.getInstance().showScreen("/com/user/fmuser/viagensAdminFilled-view.fxml", "Viagens");
    }

    public void carregarTabela() {
        if (viagemTable != null) {
            viagemTable.setItems(viagemList);
        }
        if (viagemList == null || viagemList.isEmpty()) {
            ScreenManager.getInstance().showScreen("/com/user/fmuser/viagensAdmin-view.fxml", "Viagens");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarDados();
        carregarFuncionarios();
        carregarHDPs();
        carregarVeiculos();
        preencherComboBoxes();

        if (isEdit && motoristaComboBox != null && veiculoComboBox != null) {
            motoristaComboBox.setValue(itemAtt.motorista.nome + " " + itemAtt.motorista.sobrenome);
            if (itemAtt.cobrador != null) {
                cobradorComboBox.setValue(itemAtt.cobrador.nome + " " + itemAtt.cobrador.sobrenome);
            } else {
                cobradorComboBox.setValue("Sem cobrador");
            }
            hdpComboBox.setValue(formatarHDP(itemAtt.horarioDiaPercurso));
            veiculoComboBox.setValue(String.valueOf(itemAtt.veiculo.numero));
        }

        if (viagemTable != null) {
            motoristaColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().motorista.nome + " " +
                            cellData.getValue().motorista.sobrenome));

            cobradorColumn.setCellValueFactory(cellData -> {
                Funcionario cobrador = cellData.getValue().cobrador;
                String valor = cobrador != null ? cobrador.nome + " " + cobrador.sobrenome : "Sem cobrador";
                return new SimpleStringProperty(valor);
            });

            hdpColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(formatarHDP(cellData.getValue().horarioDiaPercurso)));

            veiculoColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(String.valueOf(cellData.getValue().veiculo.numero)));

            acoesColumn.setCellFactory(col -> new TableCell<Viagem, Void>() {
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
                        Viagem item = getTableView().getItems().get(getIndex());
                        editarViagem(item);
                    });

                    deleteButton.setOnAction(event -> {
                        Viagem item = getTableView().getItems().get(getIndex());
                        excluirViagem(item);
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