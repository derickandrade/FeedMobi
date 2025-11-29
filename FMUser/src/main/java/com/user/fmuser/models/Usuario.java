package com.user.fmuser.models;

import com.user.fmuser.utils.ScreenManager;

public class Usuario {
    public static String nome = "Fulano";
    public static String sobrenome = "Da Silva";
    public static String cpf = "000.000.000-00";
    public static String email = "fulanosilva@mail.com";
    public static boolean isAdmin = false;
    public static String senha = "1234";

    public static void logout() {
        ScreenManager.getInstance().showScreen("/com/user/fmuser/login-view.fxml", "Login");
        nome = null;
        sobrenome = null;
        cpf = null;
        email = null;
        isAdmin = false;
        senha = null;
    }
}
