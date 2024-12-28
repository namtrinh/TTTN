import {Component, OnInit} from '@angular/core';
import {MovieService} from '../../../service/movie.service';
import {ActivatedRoute} from '@angular/router';
import {Movie} from '../../../model/movie.model';
import {DatePipe} from '@angular/common';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {SeatComponent} from '../seat/seat.component';

@Component({
  selector: 'app-film-detail',
  imports: [
    DatePipe,
    SeatComponent
  ],
  templateUrl: './film-detail.component.html',
  standalone: true,
  styleUrl: './film-detail.component.css'
})
export class FilmDetailComponent implements OnInit{
    constructor(private movieService: MovieService,
                private activeRouter: ActivatedRoute,
                private santizer: DomSanitizer) {}

  ngOnInit(): void {
    const title = this.activeRouter.snapshot.params['movieTitle'];
    this.getbyTitle(title);
  }

  movie:Movie = new Movie();
    trailer!:SafeResourceUrl;

  getbyTitle(title:string){
      this.movieService.getByTitle(title).subscribe((data:any) =>{
        this.movie = data.result;
        if (this.movie.trailerUrl != null) {
          this.trailer = this.santizer.bypassSecurityTrustResourceUrl(this.movie.trailerUrl);
        }
        console.log(this.movie);
      })
  }


}
