package ku.cs.controller;

import animatefx.animation.FadeIn;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class RegisterController {

    public VBox vBox;

    @FXML
    public void initialize() {

        new FadeIn(vBox).play();

        // Setting role dropdown menu
        DropDownController roleDropDownController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/dropdown.fxml"));

        roleDropDownController.setTitleText("Role");
        roleDropDownController.getComboBox().setPromptText("Select Role");
        roleDropDownController.getComboBox().getItems().add("Customer");
        roleDropDownController.getComboBox().getItems().add("Musician");

        roleDropDownController.addListener(() -> System.out.println(roleDropDownController.getComboBox().getValue()));

        roleDropDownController.getComboBox().getSelectionModel().select(0);


        // Setting Username Form
        TextFormController usernameFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        usernameFormController.setTitleText("Username (use to login)");
        usernameFormController.getTextField().setPromptText("Enter your username");

        // Setting Password Form
        SecureTextFormController passwordFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/secureTextForm.fxml"));
        passwordFormController.setTitleText("Password");
        passwordFormController.setPromptText("Set your password");

        // Setting Password Form
        SecureTextFormController passwordConfirmFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/secureTextForm.fxml"));
        passwordConfirmFormController.setTitleText("Password Again");
        passwordConfirmFormController.setPromptText("Enter your password again");

        // Setting Email Form
        TextFormController emailFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        emailFormController.setTitleText("Email");
        emailFormController.getTextField().setPromptText("Enter your email");

        // Setting Phone Number Form
        TextFormController phoneNumberFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        phoneNumberFormController.setTitleText("Phone");
        phoneNumberFormController.getTextField().setPromptText("Enter your phone number");

    }

    public void onBackButton() {
        RootService.getController().open("login.fxml");
    }

    public void onCreateAccountClick() {
        RootService.getController().showBar("Response Message Here", RootController.Color.GREEN, Duration.seconds(5));
        RootService.getController().open("login.fxml");
    }
}
