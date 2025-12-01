package com.user.fmuser.controllers;

import com.user.fmuser.models.Database;
import com.user.fmuser.models.Funcionario;
import com.user.fmuser.MainApplication;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;

public class novoFuncionarioController {

    private String cpf;
    private String nome;
    private String sobrenome;
    private boolean isMotorista;

    public void logout() {
        MainApplication.logout();
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }

    @FXML
    public void voltar(){
        ScreenManager.getInstance().showScreen("/com/user/fmuser/funcionarios-view.fxml", "Funcionarios");
    }

    protected static String texto = "Selecionar";

    public static int cargoFuncionario = 0;

    @FXML
    private TextField cpfField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField sobrenomeField;

    @FXML
    private Label cpfMessage;

    @FXML
    private Label cargoMessage;

    @FXML
    private Label nomeMessage;

    @FXML
    private Label sobrenomeMessage;

    @FXML
    public ComboBox<String> cargoFuncionarioMenu;


    @FXML
    public void initialize(){
        cargoFuncionarioMenu.getItems().addAll("Motorista", "Cobrador");

        if (cargoFuncionario == 1) {
            texto = "Motorista";
        }
        else if (cargoFuncionario == 2) {
            texto = "Cobrador";
        }
        else {
            texto = null;
        }
        cargoFuncionarioMenu.setValue(texto);

        //inicializa com mensagens ocultas
        cpfMessage.setVisible(false);
        cargoMessage.setVisible(false);
        nomeMessage.setVisible(false);
        sobrenomeMessage.setVisible(false);
    }



    @FXML
    public void handleCargoFuncionario() {
        String value = cargoFuncionarioMenu.getValue();
        if("Motorista".equals(value)){
            cargoFuncionario = 1;
        } else if ("Cobrador".equals(value)) {
            cargoFuncionario = 2;
        } else {
            cargoFuncionario = 0;
        }

        isMotorista = (cargoFuncionario == 1);

        if(cargoMessage !=null ){
            cargoMessage.setVisible(cargoFuncionario == 0);
        }
    }
    @FXML
    public void handleCadastrarFuncionario() {
        cpf = cpfField.getText();
        nome = nomeField.getText();
        sobrenome = sobrenomeField.getText();

        boolean cpfValido = Database.validCPF(cpf);
        boolean nomeValido = !nome.isEmpty();
        boolean sobrenomeValido = !sobrenome.isEmpty();
        boolean cargoValido = (cargoFuncionario == 1 || cargoFuncionario == 2);

        cpfMessage.setVisible(!cpfValido);
        nomeMessage.setVisible(!nomeValido);
        sobrenomeMessage.setVisible(!sobrenomeValido);
        cargoMessage.setVisible(!cargoValido);

        if(cpfValido && nomeValido && sobrenomeValido && cargoValido) {
            isMotorista = (cargoFuncionario == 1);

            Funcionario funcionario = new Funcionario(cpf,nome,sobrenome,isMotorista);
            try {
                //Database.addEmployee(funcionario);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Funcionário cadastrado com sucesso!");
                alert.showAndWait();
            //} catch (RuntimeException | SQLException e) {
            } catch (RuntimeException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERRO");
                alert.setHeaderText("Não foi possível realizar o cadastro!");
                alert.setContentText("Ocorreu um erro: \n" + e + "\nTente novamente.");
                alert.showAndWait();
            }
        }

    }

    @FXML
    public void verificarCPF() {}
}
