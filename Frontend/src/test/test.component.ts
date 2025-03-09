import {Component, NgZone, OnInit} from '@angular/core';
import {SharedDataService} from '../service/sharedata.service';
import {QRCodeComponent} from 'angularx-qrcode';
import {ZXingScannerModule} from '@zxing/ngx-scanner';
import {BarcodeFormat} from '@zxing/library';
import {Camera, CameraResultType} from '@capacitor/camera';
import jsQR from 'jsqr';
import {TicketService} from '../service/ticket.service';

@Component({
  selector: 'app-test',
  imports: [
    QRCodeComponent,
    ZXingScannerModule
  ],
  templateUrl: './test.component.html',
  standalone: true,
  styleUrl: './test.component.css'
})
export class TestComponent {
  scannedResult: string | null = null;
  scanErrorMessage: string | null = null;

  constructor(private ngZone: NgZone,
              private ticketService:TicketService) {}

  // 📸 Chụp ảnh và quét QR
  async scanQRCode() {
    try {
      const image = await Camera.getPhoto({ resultType: CameraResultType.DataUrl });
      if (!image.dataUrl) throw new Error("Không thể lấy ảnh từ camera");

      const img = new Image();
      img.src = image.dataUrl;
      img.onload = () => this.processQrCode(img);
    } catch (error:any) {
      this.ngZone.run(() => this.scanErrorMessage = "Lỗi: " + error.message);
    }
  }

  private processQrCode(img: HTMLImageElement) {
    const canvas = Object.assign(document.createElement("canvas"), { width: img.width, height: img.height });
    const context = canvas.getContext("2d");
    if (!context) return;

    context.drawImage(img, 0, 0);
    const code = jsQR(context.getImageData(0, 0, img.width, img.height).data, img.width, img.height);

    this.ngZone.run(() => {
      this.scannedResult = code?.data || null;
      this.ticketService.findById(this.scannedResult).subscribe((data:any) =>{
        console.log(data.result)
      })

      this.scanErrorMessage = code ? null : "Không tìm thấy mã QR";
    });
  }


  // 🔦 Bật/tắt đèn flash

}
