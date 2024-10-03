package ku.cs.controller;

import javafx.fxml.FXML;
import ku.cs.service.RootService;


public class LoginController {
    
    @FXML
    public void initialize() {

    }

    public void onSignUpButton() {
        RootService.getController().open("register.fxml");
    }
}
