import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QRCodeDisplay extends Application {
    @Override
    public void start(Stage primaryStage) {
        // สมมติว่า QR Code ถูกสร้างและแสดงที่นี่
        Image qrImage = new Image("file:qr_code.png"); // เปลี่ยน path ตามไฟล์จริง
        ImageView qrView = new ImageView(qrImage);
        qrView.setFitWidth(200);
        qrView.setFitHeight(200);

        // ปุ่ม Cancel สีแดง
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> primaryStage.close());

        // ปุ่ม OK สีเขียว
        Button okButton = new Button("OK");
        okButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        okButton.setOnAction(e -> System.out.println("Payment Confirmed!"));

        // จัดเรียง UI
        VBox root = new VBox(10, qrView, okButton, cancelButton);
        root.setStyle("-fx-alignment: center; -fx-padding: 20px;");

        Scene scene = new Scene(root, 300, 350);
        primaryStage.setTitle("QR Code Display");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
