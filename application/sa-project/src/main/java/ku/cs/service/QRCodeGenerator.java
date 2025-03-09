package ku.cs.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class QRCodeGenerator {
    public static Image generateQRCode(String phoneNumber, int amount) throws WriterException {
        // สร้างข้อมูล QR Code ตามรูปแบบ PromptPay
        String qrData = "00020101021129370016A00000067701011201150123"
                + phoneNumber + "5303764"
                + "540" + String.format("%02d", amount) + "5802TH6304";

        int size = 200; // ขนาดของ QR Code
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrData, BarcodeFormat.QR_CODE, size, size);

        // สร้าง WritableImage สำหรับแสดงผลใน JavaFX
        WritableImage image = new WritableImage(size, size);
        PixelWriter pixelWriter = image.getPixelWriter();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                pixelWriter.setArgb(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}
