package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import ku.cs.net.ClientUpdatePassword;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;
import org.json.JSONObject;

public class ChangePasswordController {
    @FXML
    public VBox vBox;
    private TextFormController oldPasswordFormController;
    private SecureTextFormController newPasswordFormController;
    private SecureTextFormController againNewPasswordFormController;

    public void initialize() {

        // Setting name Form
        oldPasswordFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        oldPasswordFormController.setTitleText("Old Password");
        oldPasswordFormController.getTextField().setPromptText("Input Old Password");

        // Setting phone Form
        newPasswordFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/secureTextForm.fxml"));
        newPasswordFormController.setTitleText("New Password");
        newPasswordFormController.setPromptText("Input New Password");

        // Setting email Form
        againNewPasswordFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/secureTextForm.fxml"));
        againNewPasswordFormController.setTitleText("New Password Again");
        againNewPasswordFormController.setPromptText("Input New Password Again");
    }

    public void OnChangePassword() {

        String oldPassword = oldPasswordFormController.getText();
        String newPassword = newPasswordFormController.getText();
        String newPasswordAgain = againNewPasswordFormController.getText();

        if (!newPasswordAgain.equals(newPassword)) {
            RootService.showErrorBar("Confirm Password Failed");
            return;
        }

        try {
            ClientUpdatePassword clientUpdatePassword = new ClientUpdatePassword();
            String response = clientUpdatePassword.updatePassword(oldPassword, newPassword);
            RootService.showBar(response);
            RootService.getController().getNavigationController().open("setting.fxml");
        } catch (Exception e) {
            RootService.showErrorBar(e.getClass().getSimpleName() + "\n" + e.getMessage());
        }
    }

    public void OnBackToHome() {
        RootService.getController().getNavigationController().open("setting.fxml");
    }
}
