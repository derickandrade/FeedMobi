package com.user.fmuser.controllers;

import com.sun.tools.javac.Main;
import com.user.fmuser.MainApplication;
import com.user.fmuser.models.Database;
import com.user.fmuser.models.Usuario;
import com.user.fmuser.utils.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AtualizarCadastroController implements Initializable {
    @FXML
    private Button voltarButton;

    @FXML
    private Button atualizarButton;

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
    private PasswordField confirmarSenhaField;

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

    private String email;
    private String nome;
    private String sobrenome;
    private String senha;
    private String confirmarSenha;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cpfField.setText(MainApplication.usuarioSessao.getCPF());
        cpfField.setDisable(true);
        nomeField.setText(MainApplication.usuarioSessao.nome);
        sobrenomeField.setText(MainApplication.usuarioSessao.sobrenome);
        emailField.setText(MainApplication.usuarioSessao.email);
    }

    @FXML
    protected void retornarHome() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/home-view.fxml", "Home");
    }

    @FXML
    protected void excluirConta() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXCLUIR CONTA");
        alert.setHeaderText("Você tem certeza que deseja continuar?");
        alert.setContentText("Esta ação não pode ser desfeita.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Database.removeUser(MainApplication.usuarioSessao.getCPF());
                Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                sucesso.setTitle("CONTA EXCLUÍDA");
                sucesso.setHeaderText("Conta excluída com sucesso!");
                MainApplication.logout();
                ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");

            } catch (RuntimeException e) {
                Alert erro = new Alert(Alert.AlertType.ERROR);
                erro.setTitle("ERRO");
                erro.setHeaderText("Não foi possível excluir a conta!");
                erro.setContentText("Ocorreu um erro:\n" + e);
            }

        }
    }

    @FXML
    protected void atualizarCadastro() {
        email = emailField.getText();
        senha = senhaField.getText();
        confirmarSenha = confirmarSenhaField.getText();
        nome = nomeField.getText();
        sobrenome = sobrenomeField.getText();

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

        if (emailValido &&
                nomeValido &&
                sobrenomeValido &&
                senhaValida &&
                confirmarSenhaValida &&
                senhasConferem) {
            Usuario usuario = new Usuario(MainApplication.usuarioSessao.getCPF(), nome, sobrenome, email, senha);

            try {
                Database.updateUser(usuario);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText("Atualização cadastral bem sucedida!");
                MainApplication.usuarioSessao = usuario;
                alert.showAndWait();
                retornarHome();
            } catch (RuntimeException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERRO");
                alert.setHeaderText("Não foi possível atualizar o cadastro!");
                alert.setContentText("Ocorreu um erro: \n" + e + "\nTente novamente.");
                alert.showAndWait();
            }
        }
    }
}
