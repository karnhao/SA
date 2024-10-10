package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ku.cs.net.ClientLogin;
import ku.cs.service.RootService;


public class LoginController {

    public TextField passwordField;
    public TextField usernameField;

    @FXML
    public void initialize() {

    }

    public void onSignUpButton() {
        RootService.getController().open("change_password.fxml");
    }

    public void onLogin() {

        try {
            ClientLogin clientLogin = new ClientLogin();
            clientLogin.login(usernameField.getText(), passwordField.getText());
        } catch (Exception e) {
            RootService.showErrorBar(e.getMessage());
        }
    }

    public void onForgotPassword() {
        RootService.getController().open("forgotPassword.fxml");
    }

}
