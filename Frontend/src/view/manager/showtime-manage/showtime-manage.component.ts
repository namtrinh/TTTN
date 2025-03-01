import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Showtime} from '../../../model/showtime.model';
import {ShowtimeService} from '../../../service/showtime.service';
import {MovieService} from '../../../service/movie.service';
import {Movie} from '../../../model/movie.model';
import {RoomService} from '../../../service/room.service';
import {Room} from '../../../model/room.model';

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
  }

  dateTime!: string;
  showtime: Showtime = new Showtime();
  showtimes: Showtime[] = [];
  movies:Movie[] =[]
  movie:Movie = new Movie()
  rooms:Room[] = [];
  room!:Room;
  timeSet = {
    time_start:'',
    time_end:''
  }

  getAllShowtimeByTime() {
    this.showtimeService.getAll(this.dateTime).subscribe((data: any) => {
      this.showtimes = data.result;
      console.log(this.showtimes)
      this.getMovie();
      this.getRoom();
    }, error => {
      console.log(error.error.message)
    })
  }

  createShowtime(data: Showtime) {
    console.log("dâta cre" + data)
    this.showtimeService.createShowtime(data).subscribe((data: any) => {
      this.showtimes.push(data.result)
    }, error => {
      alert(error.error.message !)
    })
  }

  deleteShowtime(id: string) {
    if (window.confirm("Are you sure you want to delete this showtime")) {
      this.showtimeService.deleteById(id).subscribe(data => {
      this.getAllShowtimeByTime();
      })
    }
  }

  getById(id:string){
    this.showtimeService.getById(id).subscribe((data:any) => {
      this.showtime = data.result;
      this.timeSet = data.result;
      console.log("getById",this.showtime)
    })
  }

  updateShowtime(id: string, data: { time_start: string; time_end: string }){
   console.log(data)
    this.showtimeService.updateById(id, data).subscribe((data:any) =>{
      console.log("update ",data)
      const index = this.showtimes.findIndex(showtime => showtime.showtimeId === id)
      if(index !== -1){
        this.showtimes[index].time_start = data.result.time_start
        this.showtimes[index].time_end = data.result.time_end


      }
    }, error => {
      console.log(error)
    })
  }

  getMovie(){
    this.movieService.getTop4().subscribe((data:any) =>{
      this.movies = data.result;
      console.log(this.movies)
    })
  }

  body:Showtime = new Showtime()
  setMovietoShowtime(id: string, movieId: string , roomId: string){
    console.log(movieId)
    this.body.movieId = movieId
    this.body.roomId = roomId
    this.showtimeService.setMovieToShowtime(id, this.body).subscribe((data:any) =>{
      console.log("setMovietoShowtime",data)
    })
  }

  getRoom(){
    this.roomService.getAll().subscribe((data:any) =>{
      this.rooms = data.result;
      console.log(this.rooms)
    })
  }
}
