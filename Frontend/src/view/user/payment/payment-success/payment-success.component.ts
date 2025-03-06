import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {SeatService} from '../../../../service/seat.service';
import {SharedDataService} from '../../../../service/sharedata.service';
import {DatePipe, DecimalPipe} from '@angular/common';

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
              private activeRouter:ActivatedRoute) {
  }

  dataPayment: any
  orderId!: string
  total_price!:number
  datePayment!:string
  partnerCode!:string

  ngOnInit(): void {
  this.activeRouter.queryParams.subscribe(params =>{
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
  }

  updateStatusSeat() {
    const seatId = this.dataPayment.seat.forEach((seat: any) => {
      this.seatService.updateStatusSeat(seat.seatId, seat).subscribe((data: any) => {
        console.log(data);
      })
    })

  }

}
