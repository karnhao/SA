package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ku.cs.model.User;
import ku.cs.net.ClientLogin;
import ku.cs.net.ClientUserInfo;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;


public class LoginController {

    public SecureTextFormController passwordField;
    public TextFormController usernameField;
    @FXML
    public Button forceLoginButton;
    @FXML
    private VBox vBoxField;

    @FXML
    public void initialize() {
        // Create Login Field
        this.usernameField = ComponentLoader.loadInto(vBoxField, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        this.usernameField.setTitleText("Username");
        this.usernameField.getTextField().setPromptText("Enter your username...");

        this.passwordField = ComponentLoader.loadInto(vBoxField, getClass().getResource("/ku/cs/views/components/secureTextForm.fxml"));
        this.passwordField.setTitleText("Password");
        this.passwordField.setPromptText("Enter your password...");

        //forceLoginButton.setVisible(false);
    }

    public void onSignUpButton() {
        RootService.getController().open("register.fxml");
    }

    public void onLogin() {

        try {

            // Login
            ClientLogin clientLogin = new ClientLogin();
            clientLogin.login(usernameField.getText(), passwordField.getText());

            // Get User Data
            User user = getUser();
            RootService.getData().setUser(user);

            RootService.getController().open("navigation.fxml");
        } catch (Exception e) {
            RootService.showErrorBar(e.getMessage());
        }
    }

    private static User getUser() {
        ClientUserInfo clientUserInfo = new ClientUserInfo();
        return clientUserInfo.getUserInfo();
    }

    public void onForceLogin() {
        RootService.getController().open("eventRequirement.fxml");  //navigation
    }

    public void onForgotPassword() {
        RootService.getController().open("forgotPassword.fxml");
    }

}
