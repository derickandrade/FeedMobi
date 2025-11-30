package com.user.fmuser;

import com.user.fmuser.models.Database;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        Database.connect();
        Application.launch(MainApplication.class, args);
        Database.disconnect();
    }
}
