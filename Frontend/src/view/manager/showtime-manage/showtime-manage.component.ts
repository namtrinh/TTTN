import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Showtime} from '../../../model/showtime.model';
import {ShowtimeService} from '../../../service/showtime.service';
import {MovieService} from '../../../service/movie.service';
import {Movie} from '../../../model/movie.model';
import {RoomService} from '../../../service/room.service';
import {Room} from '../../../model/room.model';
import {Seat} from '../../../model/seat.model';

@Component({
  selector: 'app-showtime-manage',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './showtime-manage.component.html',
  standalone: true,
  styleUrl: './showtime-manage.component.css'
})
export class ShowtimeManageComponent implements OnInit {
  constructor(private showtimeService: ShowtimeService,
              private movieService:MovieService,
              private roomService:RoomService) {
  }

  ngOnInit(): void {
    this.getRoom()
  }

  dateTime!: string;
  showtime: Showtime = new Showtime();
  showtimes: Showtime[] = [];
  movies:Movie[] =[]
  movie:Movie = new Movie()
  rooms:Room[] = [];
  room!:Room;
  body:Showtime = new Showtime()
  selectdRoomId:string = ''


  getAllShowtimeByTime(date:string, roomselected:string) {
    if (date) {
      this.showtimeService.getAll(date, roomselected, '').subscribe((data: any) => {
        this.showtimes = data.result;
        console.log(this.showtimes)
        this.getMovie();
        this.getRoom();
      }, error => {
        console.log(error.error.message)
      })
    }else{
      alert("Date is not empty!")
    }
  }

  createShowtime(data: Showtime) {
    console.log(data)
    console.log(data.roomId)
    this.showtimeService.createShowtime(data).subscribe((data: any) => {
      this.showtimes.push(data.result)
    }, error => {
      alert(error.error.message !)
    })
  }

  deleteShowtime(id: string) {
    if (window.confirm("Are you sure you want to delete this showtime")) {
      this.showtimeService.deleteById(id).subscribe(data => {
     // this.getAllShowtimeByTime(date);
      })
    }
  }

  getById(id:string){
    this.showtimeService.getById(id).subscribe((data:any) => {
      this.showtime = data.result;
    })
  }

  updateShowtime(id: string, data: Showtime){
    this.showtimeService.updateById(id, data).subscribe((data:any) =>{
      const index = this.showtimes.findIndex(showtime => showtime.showtimeId === id)
      if(index !== -1){
        this.showtimes[index]= data.result
      }
    }, error => {
      console.log(error)
    })
  }

  getMovie(){
    this.movieService.getTop4().subscribe((data:any) =>{
      this.movies = data.result;
    })
  }


  setMovietoShowtime(id: string, movieId: string , roomId: string){
    this.body.movieId = movieId
    this.body.roomId = roomId
    this.showtimeService.updateById(id, this.body).subscribe((data:any) =>{})
  }

  getRoom(){
    this.roomService.getAll().subscribe((data:any) =>{
      this.rooms = data.result;
      console.log(this.rooms)
    })
  }


}
