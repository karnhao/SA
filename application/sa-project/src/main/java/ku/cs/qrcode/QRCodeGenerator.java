package ku.cs.qrcode;

import com.google.zxing.BarcodeFormat;
//import com.google.zxing.BitMatrix;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeGenerator {
    public static void main(String[] args) {
        try {
            // ข้อความ QR Code (เช่น ลิงก์สำหรับชำระเงิน)
            String paymentUrl = "https://example.com/payment?amount=100&order_id=12345";
            
            // กำหนดขนาด QR Code
            int width = 300;
            int height = 300;

            // สร้าง BitMatrix (ข้อมูล QR Code)
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(paymentUrl, BarcodeFormat.QR_CODE, width, height);

            // กำหนดไฟล์สำหรับบันทึก QR Code
            String filePath = "payment_qr.png";
            Path path = FileSystems.getDefault().getPath(filePath);

            // บันทึก QR Code เป็นไฟล์ PNG
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            System.out.println("QR Code ถูกสร้างที่: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}