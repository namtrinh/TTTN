import {Component, NgZone} from '@angular/core';
import {Ticket} from '../../../model/ticket.model';
import {TicketService} from '../../../service/ticket.service';
import {Camera, CameraResultType} from '@capacitor/camera';
import jsQR from 'jsqr';

@Component({
  selector: 'app-checkin',
  imports: [],
  templateUrl: './checkin.component.html',
  standalone: true,
  styleUrl: './checkin.component.css'
})
export class CheckinComponent {
  scannedResult: string | null = null;
  scanErrorMessage: string | null = null;
  ticket:Ticket = new Ticket()

  constructor(private ngZone: NgZone,
              private ticketService:TicketService) {}

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
        this.ticket = data.result
      })

      this.scanErrorMessage = code ? null : "Không tìm thấy mã QR";
    });
  }

  checkin(){
    this.ticketService.updateById(this.scannedResult, this.ticket).subscribe((data:any) =>{
      console.log(data.result)

    }, error => {
      alert(error.error.message)
    })
  }
}
