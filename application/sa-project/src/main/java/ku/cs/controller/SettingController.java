package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ku.cs.model.MusicianRole;
import ku.cs.model.User;
import ku.cs.net.*;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

import java.util.LinkedList;
import java.util.List;

public class SettingController {
    @FXML
    public ImageView profileImage;
    @FXML
    public VBox vBox;
    public VBox musicianAvailableRoleVBox;
    public VBox rolesVBox;
    private TextFormController nameFormController;
    private TextFormController phoneFormController;
    private TextFormController emailFormController;

    private List<MusicianRole> roles;
    private List<AvailableRoleFormController<MusicianRole>> roleFormControllerList;

    public void initialize() {

        User userData = RootService.getData().getUser();
        if (!userData.getRole().equalsIgnoreCase("musician")) musicianAvailableRoleVBox.setVisible(false);

        roleFormControllerList = new LinkedList<>();
        try {
            ClientGetRole clientRole = new ClientGetRole();
            roles = clientRole.getMusicianRoles();
        } catch (Exception ignored){}

        ClientGetAvailableRoles clientGetAvailableRoles = new ClientGetAvailableRoles();
        addAvailableRole(clientGetAvailableRoles.getAvailableRoles());

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

    public void onRoleAddButton() {
        AvailableRoleFormController<MusicianRole> controller = ComponentLoader.loadInto(rolesVBox,
                getClass().getResource("/ku/cs/views/components/role-form.fxml"));

        if (roles != null) {
            roles.forEach(r -> controller.getComboBox().getItems().add(r));
        }

        controller.addDeleteListener(() -> {
            controller.delete();
            roleFormControllerList.remove(controller);
        });

        roleFormControllerList.add(controller);
    }

    public void addAvailableRole(List<MusicianRole> roleList) {
        for (MusicianRole role : roleList) {
            AvailableRoleFormController<MusicianRole> controller = ComponentLoader.loadInto(rolesVBox,
                    getClass().getResource("/ku/cs/views/components/role-form.fxml"));

            if (roles != null) {
                roles.forEach(r -> controller.getComboBox().getItems().add(r));
            }

            controller.getComboBox().getSelectionModel().select(role);

            controller.addDeleteListener(() -> {
                controller.delete();
                roleFormControllerList.remove(controller);
            });

            roleFormControllerList.add(controller);
        }

    }



    public void onRoleSaveButton() {
        ClientSetAvailableRoles clientSetAvailableRoles = new ClientSetAvailableRoles();
        String response =
                clientSetAvailableRoles.
                        setAvailableRoles(roleFormControllerList.stream().map(c->c.getComboBox().getValue()).toList());

        RootService.showBar(response);
    }
}
