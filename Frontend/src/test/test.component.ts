import {Component, OnInit} from '@angular/core';
import {SharedDataService} from '../service/sharedata.service';
import {QRCodeComponent} from 'angularx-qrcode';

@Component({
  selector: 'app-test',
  imports: [
    QRCodeComponent
  ],
  templateUrl: './test.component.html',
  standalone: true,
  styleUrl: './test.component.css'
})
export class TestComponent implements OnInit {

  constructor(private sharedService: SharedDataService) {
  }
  ngOnInit(): void {

    this.sharedService.currentData.subscribe(data =>{
      console.log("share",data)
    })
  }

}
