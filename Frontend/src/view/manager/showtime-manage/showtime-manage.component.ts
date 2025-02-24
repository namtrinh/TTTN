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
dateTime!:string;
  showtime: Showtime = new Showtime();
  showtimes: Showtime[] = []

  getAll() {
    this.showtimeService.getAll(this.dateTime).subscribe((data: any) => {
      this.showtimes = data.result;
      console.log(this.showtimes)
    })
  }
}
