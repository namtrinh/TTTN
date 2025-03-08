import {Component, Input, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Room} from '../../../model/room.model';
import {Showtime} from '../../../model/showtime.model';
import {DatePipe, DecimalPipe, NgClass} from '@angular/common';
import {ShowtimeService} from '../../../service/showtime.service';
import {RoomService} from '../../../service/room.service';
import {SeatService} from '../../../service/seat.service';
import {Seat} from '../../../model/seat.model';
import {FooterComponent} from '../footer/footer.component';
import {RouterLink} from '@angular/router';
import {SharedDataService} from '../../../service/sharedata.service';
import {MovieDT} from '../../../modelDto/movie';


@Component({
  selector: 'app-seat',
  imports: [
    FormsModule,
    NgClass,
    DecimalPipe,
    FooterComponent,
    RouterLink
  ],
  templateUrl: './seat.component.html',
  standalone: true,
  styleUrl: './seat.component.css'
})
export class SeatComponent {

  constructor(private showtimeService: ShowtimeService,
              private roomService: RoomService,
              private seatService: SeatService,
              private shareDataService: SharedDataService) {
  }

  @Input() movie!: MovieDT;
  date: string = '';
  showtimes: Showtime[] = [];
  inf_selected!: Partial<{ time: Showtime; room: Room, seat: Seat[], movie: MovieDT }>
  room: Room = new Room;
  seats: Seat[] = [];
  rooms: Room[] = [];
  showtimeMessage!: string;
  seat: Seat = new Seat()
  showPayment: boolean = false;
  selectedSeats!: Seat[];
  groupedShowtimes: any
  showSeat:boolean = false

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
    //FORMAT DATE NE ///////////////////////////////////////////////////////////////////////////////////////
    this.selectedSeats = []
   // const formattedDate = new Date(this.date).toLocaleDateString('en-GB');
  //  time.time_start = time.time_start + "  " + formattedDate
   // console.log("time start", time.time_start)
    this.showSeat = true
    this.inf_selected = {
      time: time,
      room: room,
      seat: [],
      movie: this.movie
    };
    console.log("selectedShowtime", this.inf_selected)
    if (this.inf_selected.time?.showtimeId) {
      this.getSeat(this.inf_selected.time.showtimeId);
    }
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
      this.shareDataService.changeData(this.inf_selected)
      this.shareDataService.currentData.subscribe(data => {
        console.log("share", data)
      })
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
