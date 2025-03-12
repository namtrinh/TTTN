import {Component, OnInit} from '@angular/core';
import {SharedDataService} from '../../../../service/sharedata.service';
import {DecimalPipe, NgClass} from '@angular/common';
import {MomoPaymentService} from '../../../../service/payment.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-checkout-inf',
  imports: [
    DecimalPipe,
    FormsModule,
    NgClass
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
  paymentMethod!: string;


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


  paymentMoMo() {
    const data = {
      amount: this.dataPayment.room.roomPrice * this.dataPayment.seat.length,
      extraData: "",
      orderInfo: ""

    }
    this.paymentService.createOrder(data).subscribe((data: any) => {
      window.location.href = data.payUrl;
    })
  }

  selectedPaymentMethod: string = '';

  pay() {
    switch (this.paymentMethod) {
      case 'momo':
        this.paymentMoMo();
        break;
      case 'domestic':
        alert("this payment method is currently under development!")
        break;
      case 'international':
        alert("this payment method is currently under development!")
        break;
      default:
        this.selectedPaymentMethod = 'Chưa chọn phương thức thanh toán';
    }
  }

  selectPaymentMethod(method: string) {
    this.paymentMethod = method;
  }

  protected readonly alert = alert;


}
