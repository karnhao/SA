package ku.cs.qrcode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileInputStream;

public class QRCodeDisplay extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // โหลด QR Code ที่สร้างไว้
        Image qrImage = new Image(new FileInputStream("payment_qr.png"));
        ImageView imageView = new ImageView(qrImage);
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);

        VBox root = new VBox(imageView);
        Scene scene = new Scene(root, 350, 350);

        primaryStage.setTitle("QR Code Payment");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
