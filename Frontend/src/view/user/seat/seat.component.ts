import {Component, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RoomType} from '../../../model/room.model';

@Component({
  selector: 'app-seat',
  imports: [
    FormsModule
  ],
  templateUrl: './seat.component.html',
  standalone: true,
  styleUrl: './seat.component.css'
})
export class SeatComponent implements OnInit{

  roomType!: RoomType | ''

  ngOnInit(): void {
  }

}
