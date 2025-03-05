import {Component, Input, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Room, RoomType} from '../../../model/room.model';
import {Showtime} from '../../../model/showtime.model';
import {CurrencyPipe, DatePipe, DecimalPipe, NgClass} from '@angular/common';
import {ShowtimeService} from '../../../service/showtime.service';
import {RoomService} from '../../../service/room.service';
import {SeatService} from '../../../service/seat.service';
import {Seat} from '../../../model/seat.model';
import {FooterComponent} from '../footer/footer.component';


@Component({
  selector: 'app-seat',
  imports: [
    FormsModule,
    NgClass,
    DecimalPipe,
    FooterComponent
  ],
  templateUrl: './seat.component.html',
  standalone: true,
  styleUrl: './seat.component.css'
})
export class SeatComponent{

  constructor(private showtimeService: ShowtimeService,
              private roomService: RoomService,
              private seatService: SeatService) {
  }

  @Input() movie!: { movieId: string; movieName: string };
  date: string = '';
  showtimes: Showtime[] = [];
  inf_selected!: { time: Showtime; room: Room, seat: Seat[] }
  room: Room = new Room;
  seats: Seat[] = [];
  rooms: Room[] = [];
  showtimeMessage!: string;
  seat: Seat = new Seat()
  showPayment:boolean = false;
  selectedSeats!: Seat[];
  groupedShowtimes: any

  getShowtime(date: string) {
    if (date && this.movie.movieId) {
      this.showtimeService.getAll(this.date, '', this.movie.movieId).subscribe((data: any) => {
        this.showtimes = data.result;
        if (this.showtimes.length === 0) {
          this.rooms = [];
          this.showtimeMessage = "Currently, there are no showtimes available for this movie."
        } else {
          this.showtimeMessage = '';
        }
        this.groupedShowtimes = [];
        this.showtimes.forEach(showtime => {
          const existingGroup = this.groupedShowtimes.find((group: any) => group.roomId === showtime.roomId);
          if (!existingGroup) {
            this.groupedShowtimes.push({
              roomId: showtime.roomId,
              showtimes: [showtime]
            });
            this.rooms = [];
            this.getRoomById(showtime.roomId)
          } else {
            existingGroup.showtimes.push(showtime);
          }
        }, console.log(this.groupedShowtimes));

      }, error => {
        console.log(error.error.message);
      });
    }
  }

  getRoomById(id: string) {
    this.roomService.getById(id).subscribe((data: any) => {
      this.room = data.result;
      this.rooms.push(this.room)
      console.log("room", this.room)
    })
  }

  selectShowtime(time: any, room: any) {
    this.selectedSeats = []
    this.inf_selected = {
      time: time,
      room: room,
      seat: []
    };
    console.log("selectedShowtime", this.inf_selected)
    this.getSeat(this.inf_selected.time.showtimeId);
  }


  getSeat(showtimeId: string) {
    console.log(showtimeId)
    this.seatService.getAll(showtimeId).subscribe((data: any) => {
      this.seats = data.result
      console.log("seat", this.seats)
    }, error => {
      console.log(error)
    })
  }

  toggleSeatSelection(seat: Seat): void {
    this.showPayment = true
    const index = this.selectedSeats.findIndex(s => s.seatId === seat.seatId);
    if (index === -1) {
      this.selectedSeats.push(seat);
      this.inf_selected.seat = this.selectedSeats
      console.log("full selected", this.inf_selected)
      console.log(this.selectedSeats)
    } else {
      this.selectedSeats.splice(index, 1);
    }
  }

  isSelected(seat: Seat): boolean {
    return this.selectedSeats.some(s => s.seatId === seat.seatId);
  }

}
