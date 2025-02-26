import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Showtime} from '../../../model/showtime.model';
import {ShowtimeService} from '../../../service/showtime.service';

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
  constructor(private showtimeService: ShowtimeService) {
  }

  ngOnInit(): void {
  }

  dateTime!: string;
  showtime: Showtime = new Showtime();
  showtimes: Showtime[] = []

  getAll() {
    this.showtimeService.getAll(this.dateTime).subscribe((data: any) => {
      this.showtimes = data.result;
      console.log(this.showtimes)
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
      this.getAll();
      })
    }
  }

  getById(id:string){
    this.showtimeService.getById(id).subscribe((data:any) => {
      this.showtime = data.result;
    })
  }

  updateShowtime(id:string, data:Showtime){
    console.log("data update"+data)
    this.showtimeService.updateById(id, data).subscribe((data:any) =>{
      const index = this.showtimes.findIndex(showtime => showtime.showtimeId === id)
      if(index !== -1){
        this.showtimes[index] = data.result
         console.log(data.result)
      }
    }, error => {
      alert(error.error.message)
    })
  }
}
