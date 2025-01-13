import {Component, OnInit} from '@angular/core';
import {FooterComponent} from '../footer/footer.component';
import {MovieService} from '../../../service/movie.service';
import {Movie} from '../../../model/movie.model';
import {MovieResponse} from '../../../modelDto/response/movie.response';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [
    FooterComponent,
    RouterLink
  ],
  templateUrl: './home.component.html',
  standalone: true,
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  videoUrl!: string;
  movies: MovieResponse[] = []

  constructor(private movieService:MovieService) { }

  ngOnInit(): void {
    this.getAll();
  }

  getAll(){
    this.movieService.getAll().subscribe((data:any) =>{
        this.movies = data.result;
        console.log(this.movies);
    })
  }
}
