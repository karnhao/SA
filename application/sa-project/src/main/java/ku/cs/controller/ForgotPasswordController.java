package ku.cs.controller;

import javafx.fxml.FXML;
import ku.cs.service.RootService;

public class ForgotPasswordController {

    @FXML
    public void initialize() {

    }
    public void onBackToLoginPage() {
        RootService.getController().open("login.fxml");
    }

    public void onResetPassword() {
        RootService.getController().open("login.fxml");
    }

}
