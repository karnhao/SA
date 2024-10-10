package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ku.cs.net.ClientLogin;
import ku.cs.net.ClientUserInfo;
import ku.cs.service.RootService;
import org.json.JSONObject;


public class LoginController {

    public TextField passwordField;
    public TextField usernameField;

    @FXML
    public void initialize() {

    }

    public void onSignUpButton() {
        RootService.getController().open("setting.fxml");
    }

    public void onLogin() {
        try {
            ClientLogin clientLogin = new ClientLogin();
            clientLogin.login(usernameField.getText(), passwordField.getText());

            ClientUserInfo clientUserInfo = new ClientUserInfo();
            JSONObject userInfo = clientUserInfo.getUserInfo();
            System.out.println(userInfo.toString(4));
        } catch (Exception e) {
            RootService.showErrorBar(e.getMessage());
        }
    }

    public void onForgotPassword() {
        RootService.getController().open("forgotPassword.fxml");
    }

}
