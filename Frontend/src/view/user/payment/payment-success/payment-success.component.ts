import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {SeatService} from '../../../../service/seat.service';
import {SharedDataService} from '../../../../service/sharedata.service';
import {DatePipe, DecimalPipe} from '@angular/common';
import {OrderService} from '../../../../service/order.service';
import {TicketService} from '../../../../service/ticket.service';
import {Order, OrderStatus} from '../../../../model/order.model';
import {Ticket, TicketStatus} from '../../../../model/ticket.model';
import {Seat} from '../../../../model/seat.model';
import html2canvas from 'html2canvas';
import {HttpClient} from '@angular/common/http';
import {QRCodeComponent} from 'angularx-qrcode';

@Component({
  selector: 'app-payment-success',
  imports: [
    RouterLink,
    DecimalPipe,
    DatePipe,
    QRCodeComponent
  ],
  templateUrl: './payment-success.component.html',
  standalone: true,
  styleUrl: './payment-success.component.css'
})
export class PaymentSuccessComponent implements OnInit {
  constructor(private seatService: SeatService,
              private shareDataService: SharedDataService,
              private activeRouter: ActivatedRoute,
              private orderService: OrderService,
              private ticketService: TicketService,
              private http: HttpClient) {
  }

  dataPayment: any
  orderId!: string
  total_price!: number
  datePayment!: string
  partnerCode!: string
  order: Order = new Order()
  ticket: Ticket = new Ticket()
  showTicket: boolean = false
  messageMail!:string;

  ngOnInit(): void {
    this.activeRouter.queryParams.subscribe(params => {
      this.orderId = params['orderId']
      this.total_price = params['amount']
      this.datePayment = params['responseTime']
      this.partnerCode = params['partnerCode']
    })

    this.shareDataService.currentData.subscribe(data => {
      this.dataPayment = data;
      console.log(" data payment", this.dataPayment)
    })

    this.updateStatusSeat()
    this.createOrder()
    this.createTicket()

    setTimeout(() => {
      this.generateAndSendImage();
    }, 3000);

  }

  updateStatusSeat() {
    const seatId = this.dataPayment.seat.forEach((seat: any) => {
      this.seatService.updateStatusSeat(seat.seatId, seat).subscribe((data: any) => {
        console.log("update seat", data);
      })
    })
  }

  createOrder() {
    this.order.email = this.dataPayment?.user.email
    this.order.name = this.dataPayment?.user.name
    this.order.phone = this.dataPayment?.user.phone
    this.order.orderStatus = OrderStatus.SUCCESS
    this.order.paymentMethod = this.partnerCode
    this.order.totalPrice = this.dataPayment?.room.roomPrice * this.dataPayment?.seat.length
    this.order.orderDate = this.datePayment
    this.order.paymentId = this.orderId
    this.orderService.createOrder(this.order).subscribe((data: any) => {
      console.log("create order", data.result)
    })
  }

  createTicket() {
    this.ticket.movieId = this.dataPayment?.movie.movieId
    this.ticket.ticketStatus = TicketStatus.NOT_CHECK_IN
    this.ticket.movieName = this.dataPayment.movie.movieName
    this.ticket.seatNumber = this.dataPayment?.seat.map((seat: Seat) => seat.seatNumber).join(',');
    this.ticket.seatPrice = this.dataPayment?.room.roomPrice
    this.ticket.showtime = this.dataPayment?.time.time_start
    console.log(this.ticket.showtime)
    this.ticketService.createTicket(this.ticket).subscribe((data: any) => {
      console.log("create ticket", data.result)
      this.ticket = data.result;
    })
  }


  @ViewChild('ticketContent', {static: false}) ticketContent!: ElementRef;


  generateAndSendImage(): void {
    if (!this.ticketContent?.nativeElement) return console.error("Lỗi: Không tìm thấy ticketContent!");

    html2canvas(this.ticketContent.nativeElement, {scale: 2, useCORS: true})
      .then(canvas => this.sendEmail(new File([this.dataURLToBlob(canvas.toDataURL('image/png'))], 'ticket.png', {type: 'image/png'})))
      .catch(error => console.error("Lỗi khi tạo ảnh:", error));
  }

  private dataURLToBlob(dataURL: string): Blob {
    const byteString = atob(dataURL.split(',')[1]);
    const uintArray = new Uint8Array(byteString.length);
    for (let i = 0; i < byteString.length; i++) {
      uintArray[i] = byteString.charCodeAt(i);
    }
    return new Blob([uintArray], {type: 'image/png'});
  }

  sendEmail(file: File): void {
    const formData = new FormData();
    formData.append('to', this.dataPayment.user.email);
    formData.append('subject', 'Vé xem phim của bạn');
    formData.append('body', 'Vé xem phim của bạn nè');
    formData.append('file', file);

    this.http.post<any>('http://localhost:8080/email/send-with-attachment', formData)
      .subscribe(response => {
        console.log('Email đã được gửi!');
        this.messageMail = 'Your ticket has been sent to your email!'
      },error => {
        this.messageMail = 'Your ticket has been sent to your email!'
      })
  }
}
