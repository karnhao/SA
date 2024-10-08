package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ku.cs.service.RootService;


public class LoginController {

    public TextField passwordField;
    public TextField usernameField;

    @FXML
    public void initialize() {

    }

    public void onSignUpButton() {
        RootService.getController().open("register.fxml");
    }

    public void onLogin() {
        RootService.getController().open("register.fxml");
    }

    public void onForgotPassword() {

    }
}
