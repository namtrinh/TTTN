import {Component, Input, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Room, RoomType} from '../../../model/room.model';
import {Showtime} from '../../../model/showtime.model';
import {DatePipe} from '@angular/common';
import {ShowtimeService} from '../../../service/showtime.service';
import {RoomService} from '../../../service/room.service';
import {SeatService} from '../../../service/seat.service';
import {Seat} from '../../../model/seat.model';


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

  constructor(private showtimeService:ShowtimeService,
              private roomService: RoomService,
              private seatService:SeatService) {
  }

  @Input() movieId!: string;
  date:string = '';
  showtimes:Showtime[] = [];
  selectedShowtime:Showtime = new Showtime()
  room:Room = new Room;
  seats:Seat[] =[];
  rooms:Room[] = [];


  ngOnInit(): void {
  }
  groupedShowtimes:any

  getShowtime(date: string) {
    if (date && this.movieId) {
      this.showtimeService.getAll(this.date,'', this.movieId).subscribe((data: any) => {
        this.showtimes = data.result;

        this.groupedShowtimes = [];
        this.showtimes.forEach(showtime => {
          const existingGroup = this.groupedShowtimes.find((group:any) => group.roomId === showtime.roomId);
          if (!existingGroup) {
            this.groupedShowtimes.push({
              roomId: showtime.roomId,
              showtimes: [showtime]
            });
            this.getRoomById(showtime.roomId)
          } else {
            existingGroup.showtimes.push(showtime);
          }
        }, console.log(this.groupedShowtimes));

    }, error =>{
        console.log(error.error.message);
      });
    }
  }

  getRoomById(id:string){
    this.roomService.getById(id).subscribe((data:any) =>{
      this.room = data.result;
      this.rooms.push(this.room)
      console.log("room",this.room)
    })
  }

  selectShowtime(roomId: string, time: any) {
    this.selectedShowtime = time;
    console.log("selected",this.selectedShowtime);
    this.getSeat(this.selectedShowtime.showtimeId)
  }

  getSeat(showtimeId:string){
    console.log(showtimeId)
    this.seatService.getAll(showtimeId).subscribe((data:any) =>{
      this.seats = data.result
      console.log("seat",this.seats)
    },error => {
      console.log(error)
    })
  }
}
