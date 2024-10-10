package ku.cs.controller;

import animatefx.animation.FadeIn;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ku.cs.net.ClientRegister;
import ku.cs.service.RootService;
import ku.cs.util.ComponentLoader;

public class RegisterController {

    public VBox vBox;

    private DropDownController roleDropDownController;
    private TextFormController usernameFormController;
    private SecureTextFormController passwordFormController;
    private SecureTextFormController passwordConfirmFormController;
    private TextFormController emailFormController;
    private TextFormController phoneNumberFormController;
    private TextFormController nameFormController;

    @FXML
    public void initialize() {

        new FadeIn(vBox).play();

        // Setting role dropdown menu
        roleDropDownController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/dropdown.fxml"));

        roleDropDownController.setTitleText("Role");
        roleDropDownController.getComboBox().setPromptText("Select Role");
        roleDropDownController.getComboBox().getItems().add("Customer");
        roleDropDownController.getComboBox().getItems().add("Musician");

        roleDropDownController.addListener(() -> System.out.println(roleDropDownController.getComboBox().getValue()));

        roleDropDownController.getComboBox().getSelectionModel().select(0);


        // Setting Username Form
        usernameFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        usernameFormController.setTitleText("Username (use to login)");
        usernameFormController.getTextField().setPromptText("Enter your username");

        // Setting Username Form
        nameFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        nameFormController.setTitleText("Your name");
        nameFormController.getTextField().setPromptText("Enter your name");

        // Setting Password Form
        passwordFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/secureTextForm.fxml"));
        passwordFormController.setTitleText("Password");
        passwordFormController.setPromptText("Set your password");

        // Setting Password Form
        passwordConfirmFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/secureTextForm.fxml"));
        passwordConfirmFormController.setTitleText("Password Again");
        passwordConfirmFormController.setPromptText("Enter your password again");

        // Setting Email Form
        emailFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        emailFormController.setTitleText("Email");
        emailFormController.getTextField().setPromptText("Enter your email");

        // Setting Phone Number Form
        phoneNumberFormController = ComponentLoader.loadInto(vBox, getClass().getResource("/ku/cs/views/components/textForm.fxml"));
        phoneNumberFormController.setTitleText("Phone");
        phoneNumberFormController.getTextField().setPromptText("Enter your phone number");

    }

    public void onBackButton() {
        RootService.getController().open("login.fxml");
    }

    public void onCreateAccountClick() {

        // validate confirm password
        if (!isPasswordValid()) {
            Platform.runLater(() -> RootService.showErrorBar("Confirm Password failed"));
            return;
        }

        ClientRegister client = new ClientRegister();
        String res;
        try {
            res = client.register(
                    usernameFormController.getTextField().getText(),
                    nameFormController.getTextField().getText(),
                    emailFormController.getTextField().getText(),
                    passwordFormController.getText(),
                    phoneNumberFormController.getTextField().getText(),
                    roleDropDownController.getComboBox().getValue()
                    );
            RootService.getController().showBar(res, RootController.Color.GREEN, Duration.seconds(5));
            RootService.getController().open("login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            res = e.getMessage();
            RootService.getController().showBar(res, RootController.Color.RED, Duration.seconds(5));
        }

        System.out.println(res);


    }

    private boolean isPasswordValid() {
        return passwordFormController.getText().equals(passwordConfirmFormController.getText());
    }
}