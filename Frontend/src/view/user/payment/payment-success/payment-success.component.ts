import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {SeatService} from '../../../../service/seat.service';
import {SharedDataService} from '../../../../service/sharedata.service';
import {DatePipe, DecimalPipe} from '@angular/common';
import {OrderService} from '../../../../service/order.service';
import {TicketService} from '../../../../service/ticket.service';
import {Order, OrderStatus} from '../../../../model/order.model';
import {Ticket} from '../../../../model/ticket.model';

@Component({
  selector: 'app-payment-success',
  imports: [
    RouterLink,
    DecimalPipe,
    DatePipe
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
              private ticketService: TicketService) {
  }

  dataPayment: any
  orderId!: string
  total_price!: number
  datePayment!: string
  partnerCode!: string
  order: Order = new Order()
  ticket: Ticket = new Ticket()

  ngOnInit(): void {
    this.activeRouter.queryParams.subscribe(params => {
      this.orderId = params['orderId']
      this.total_price = params['amount']
      this.datePayment = params['responseTime']
      this.partnerCode = params['partnerCode']
      console.log(this.datePayment)
      console.log(this.orderId)
    })

    this.shareDataService.currentData.subscribe(data => {
      this.dataPayment = data;
      console.log(this.dataPayment)
    })

    this.updateStatusSeat()

    this.createOrder()
  }

  updateStatusSeat() {
    const seatId = this.dataPayment.seat.forEach((seat: any) => {
      this.seatService.updateStatusSeat(seat.seatId, seat).subscribe((data: any) => {
        console.log(data);
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
    console.log("data Order ", this.order)
    this.orderService.createOrder(this.order).subscribe((data: any) => {
      console.log(data.result)
    })
  }

  createTicket() {
    this.ticketService.createTicket(this.ticket).subscribe((data: any) => {
      console.log(data.result)
    })
  }
}
