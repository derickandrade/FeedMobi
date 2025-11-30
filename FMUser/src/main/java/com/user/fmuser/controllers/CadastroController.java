package com.user.fmuser.controllers;

import com.user.fmuser.models.Database;
import com.user.fmuser.models.Usuario;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class CadastroController {

    private String cpf;
    private String email;
    private String senha;
    private String confirmarSenha;
    private String nome;
    private String sobrenome;
    private boolean isAdmin;
    boolean cpfDisponivel = false;

    @FXML
    private Button voltarButton;

    @FXML
    private Button cadastrarButton;

    @FXML
    private PasswordField confirmarSenhaField;

    @FXML
    private TextField sobrenomeField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField cpfField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Label cpfMessage;

    @FXML
    private Label emailMessage;

    @FXML
    private Label nomeMessage;

    @FXML
    private Label sobrenomeMessage;

    @FXML
    private Label senhaMessage;

    @FXML
    private Label confirmarSenhaMessage;

    @FXML
    private Label senhaConfereMessage;

    @FXML
    protected void retornarLogin() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }

    @FXML
    public void handleCadastro() {
        cpf = cpfField.getText();
        email = emailField.getText();
        senha = senhaField.getText();
        confirmarSenha = confirmarSenhaField.getText();
        nome = nomeField.getText();
        sobrenome = sobrenomeField.getText();
        if (cpf.isEmpty()) {
            cpfMessage.setStyle("-fx-text-fill: red");
            cpfMessage.setText("Insira seu CPF!");
            cpfMessage.setVisible(true);
        }

        boolean emailValido = !email.isEmpty();
        boolean nomeValido = !nome.isEmpty();
        boolean sobrenomeValido = !sobrenome.isEmpty();
        boolean senhaValida = !senha.isEmpty();
        boolean confirmarSenhaValida = !confirmarSenha.isEmpty();
        boolean senhasConferem = senha.equals(confirmarSenha);

        emailMessage.setVisible(!emailValido);
        nomeMessage.setVisible(!nomeValido);
        sobrenomeMessage.setVisible(!sobrenomeValido);
        senhaMessage.setVisible(!senhaValida);
        confirmarSenhaMessage.setVisible(!confirmarSenhaValida);
        senhaConfereMessage.setVisible(!senhasConferem);

        if (emailValido && nomeValido && sobrenomeValido && senhaValida && confirmarSenhaValida && senhasConferem) {
            Usuario usuario = new Usuario(cpf, nome, sobrenome, email, senha);

            try {
                Database.addUser(usuario);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Cadastro sucedido!");
                alert.setContentText("Realize o login.");
                alert.showAndWait();
                retornarLogin();
            } catch (RuntimeException | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERRO");
                alert.setHeaderText("Não foi possível realizar o cadastro!");
                alert.setContentText("Ocorreu um erro: \n" + e + "\nTente novamente.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void verificarCPF() {
        cpf = cpfField.getText();
        System.out.println(cpf.length());
        if (cpf.length() == 11) {
            cpfDisponivel = verificarCPFnoBD(cpf);
            if (cpfDisponivel) {
                cpfMessage.setStyle("-fx-text-fill: green;");
                cpfMessage.setText("CPF disponível!");
                cpfMessage.setVisible(true);
            }
            else {
                cpfMessage.setStyle("-fx-text-fill: red;");
                cpfMessage.setText("CPF já cadastrado!");
                cpfMessage.setVisible(true);
            }
        }
    }

    public boolean verificarCPFnoBD(String cpf) {
        boolean cpfDisponivel = !(Database.isCpfRegistered(cpf));
        System.out.println(cpfDisponivel);
        return cpfDisponivel;
    }
}
