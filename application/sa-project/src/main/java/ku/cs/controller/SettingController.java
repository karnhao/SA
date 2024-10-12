package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ku.cs.model.User;
import ku.cs.net.ClientUpdateUserInfo;
import ku.cs.net.ClientUserInfo;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class SettingController {
    @FXML
    public ImageView profileImage;
    @FXML
    public VBox vBox;
    private TextFormController nameFormController;
    private TextFormController phoneFormController;
    private TextFormController emailFormController;

    public void initialize() {

        User userData = RootService.getData().getUser();

        // Setting name Form
        nameFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        nameFormController.setTitleText("Your Name");
        nameFormController.getTextField().setPromptText("Your name");
        nameFormController.getTextField().setText(userData.getName());


        // Setting phone Form
        phoneFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        phoneFormController.setTitleText("Phone Number");
        phoneFormController.getTextField().setPromptText("Your phone");
        phoneFormController.getTextField().setText(userData.getPhone_number());

        // Setting email Form
        emailFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        emailFormController.setTitleText("Your email");
        emailFormController.getTextField().setPromptText("Your email");
        emailFormController.getTextField().setText(userData.getEmail());
    }

    public void OnChangeProfileImage() {

    }

    public void OnLogOut() {
        RootService.open("login.fxml");
    }

    public void OnChangePassword() {
        RootService.getController().getNavigationController().open("change_password.fxml");
    }

    public void OnApply() {
        ClientUpdateUserInfo c = new ClientUpdateUserInfo();
        ClientUserInfo clientUserInfo = new ClientUserInfo();
        try {
            String response = c.updateInfo(nameFormController.getText(), emailFormController.getText(), phoneFormController.getText());

            // Update User Info In Application
            User user = clientUserInfo.getUserInfo();
            RootService.getData().setUser(user);
            RootService.showBar(response);
        } catch (Exception e) {
            RootService.showErrorBar(e.getMessage());
        }
    }
}
