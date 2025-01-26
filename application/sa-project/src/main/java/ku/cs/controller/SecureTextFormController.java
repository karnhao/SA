package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class SecureTextFormController {

    public Label title;
    public TextField textField;
    public Pane pane;
    public ImageView imageView;
    public VBox vBox;
    public PasswordField passwordField;

    private boolean show;

    @FXML
    public void initialize() {
        this.show = true;
        this.onHiddenToggleClick();
    }

    public void setTitleText(String text) {
        this.title.setText(text);
    }

    public String getText() {
        return this.show ? textField.getText() : passwordField.getText();
    }

    public void setPromptText(String text) {
        textField.setPromptText(text);
        passwordField.setPromptText(text);
    }

    public void onHiddenToggleClick() {
        this.show = !this.show;

        if (this.show) {
            imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ku/cs/images/icons/show.png"))));
            textField.setText(passwordField.getText());
            textField.setVisible(true);
            passwordField.setVisible(false);
            return;
        }
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ku/cs/images/icons/hide.png"))));
        passwordField.setText(textField.getText());
        passwordField.setVisible(true);
        textField.setVisible(false);
    }
}
