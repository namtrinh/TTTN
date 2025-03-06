import {Component, OnInit} from '@angular/core';
import {SharedDataService} from '../../../../service/sharedata.service';

@Component({
  selector: 'app-payment',
  imports: [],
  templateUrl: './payment.component.html',
  standalone: true,
  styleUrl: './payment.component.css'
})
export class PaymentComponent implements OnInit {
  constructor(private shareDataService: SharedDataService) {
  }

  ngOnInit(): void {
    this.shareDataService.currentData.subscribe(data =>{
      console.log(data)
    })
  }


}
