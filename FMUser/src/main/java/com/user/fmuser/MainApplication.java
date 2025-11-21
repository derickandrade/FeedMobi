package com.user.fmuser;

import javafx.application.Application;
import javafx.stage.Stage;
import com.user.fmuser.utils.ScreenManager;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ScreenManager screenManager = ScreenManager.getInstance();
        stage.setResizable(false);
        screenManager.setPrimaryStage(stage);
        screenManager.showScreen("/com/user/fmuser/login-view.fxml", "Login");
    }
}
