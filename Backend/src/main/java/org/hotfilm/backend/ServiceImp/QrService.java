package org.hotfilm.backend.ServiceImp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QrService {

    public void createQR() throws WriterException, IOException {
        String data = "Ha noi mua thu";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

        String outputFile = "C:/My_Documents/KI_8/TTTN/TTTN/Backend/src/main/resources/static/b/qr.png";
        Path path = FileSystems.getDefault().getPath(outputFile);
        MatrixToImageWriter.writeToPath(matrix, "PNG", path);
    }
}
