package ku.cs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class ChangePasswordController {
    @FXML
    public VBox vBox;
    private TextFormController OldPasswordFormController;
    private TextFormController NewPasswordFormController;
    private TextFormController AgainNewPasswordFormController;

    public void initialize() {

        // Setting name Form
        OldPasswordFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        OldPasswordFormController.setTitleText("Old Password");
        OldPasswordFormController.getTextField().setPromptText("Input Old Password");

        // Setting phone Form
        NewPasswordFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        NewPasswordFormController.setTitleText("New Password");
        NewPasswordFormController.getTextField().setPromptText("Input New Password");

        // Setting email Form
        AgainNewPasswordFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        AgainNewPasswordFormController.setTitleText("New Password Again");
        AgainNewPasswordFormController.getTextField().setPromptText("Input New Password Again");
    }

    public void OnChangePassword() {
    }

    public void OnBackToHome() {
        RootService.getController().open("login.fxml");
    }
}
