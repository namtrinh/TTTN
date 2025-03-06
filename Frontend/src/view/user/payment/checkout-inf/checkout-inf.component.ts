import {Component, OnInit} from '@angular/core';
import {SharedDataService} from '../../../../service/sharedata.service';
import {DatePipe, DecimalPipe} from '@angular/common';
import {MomoPaymentService} from '../../../../service/payment.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-checkout-inf',
  imports: [
    DatePipe,
    DecimalPipe,
    FormsModule
  ],
  templateUrl: './checkout-inf.component.html',
  standalone: true,
  styleUrl: './checkout-inf.component.css'
})
export class CheckoutInfComponent implements OnInit {

  constructor(private sharedService: SharedDataService,
              private paymentService: MomoPaymentService) {
  }

  showInf: boolean = false;
  dataPayment: any;
  inf_customer: {
    name: string;
    phone: number | null;
    email: string;
  } = {name: '', phone: null, email: ''};


  ngOnInit(): void {
    this.sharedService.currentData.subscribe(data => {
      this.dataPayment = data
      console.log("share", data)
    })
  }

  showInfo(user: { name: string, phone: number | null, email: string }) {
    if (user.name == "" || user.phone == null || user.email == "") {
      alert("User's info cannot empty!")
    } else {
      this.showInf = true;
    }
    this.dataPayment.user = user
    this.sharedService.changeData(this.dataPayment)
    console.log(this.dataPayment)
  }


  payment() {
    const data = {
      amount: this.dataPayment.room.roomPrice * this.dataPayment.seat.length,
      extraData: "",
      orderInfo: ""

    }
    this.paymentService.createOrder(data).subscribe((data: any) => {
      window.location.href = data.payUrl;
    })
  }
}
