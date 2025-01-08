import {Component, OnInit} from '@angular/core';
import {DatePipe} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {MovieService} from '../../../service/movie.service';
import {Movie, MovieStatus} from '../../../model/movie.model';

@Component({
  selector: 'app-movie-manage',
  imports: [
    DatePipe,
    FormsModule
  ],
  templateUrl: './movie-manage.component.html',
  standalone: true,
  styleUrl: './movie-manage.component.css'
})
export class MovieManageComponent implements OnInit {
  constructor(private movieService: MovieService) {
  }

  ngOnInit(): void {
    this.getAll();
  }

  movies: Movie[] = [];
  page: number = 0;
  size: number = 5;
  movie: Movie = new Movie();
  selectedFile: File | null = null;
  imageUrl!: string;

  getAll() {
    this.movieService.getAll(this.page, this.size).subscribe((data: any) => {
      this.movies = data.result.content;
      console.log(this.movies);
    })
  }

  createFilm(movie: Movie) {
    const formData = new FormData();
    formData.append('movieName', movie.movieName);

    if (movie.movieTitle) formData.append('movieTitle', movie.movieTitle);
    if (movie.movieDescription) formData.append('movieDescription', movie.movieDescription);
    if (movie.genre) formData.append('genre', movie.genre);
    if (movie.director) formData.append('director', movie.director);
    if (movie.country) formData.append('country', movie.country);
    if (movie.releaseDate) formData.append('releaseDate', movie.releaseDate.toString());
    if (movie.duration) formData.append('duration', movie.duration.toString());
    if (movie.rating) formData.append('rating', movie.rating.toString());
    if (movie.posterUrl) formData.append('posterUrl', movie.posterUrl);
    if (movie.productionCompany) formData.append('productionCompany', movie.productionCompany);
    if (movie.trailerUrl) formData.append('trailerUrl', movie.trailerUrl);
    formData.append('movieStatus', movie.movieStatus);

    if (this.selectedFile) {
      formData.append('posterUrl', this.selectedFile);
    }
    this.movieService.createMovie(formData).subscribe({
      next: (response) => {
        alert('Film created successfully!');
        this.getAll();
      },
      error: (error) => {
        alert('Failed to create film.');
        console.log(error)
      },
    });
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0] as File;
    const reader = new FileReader();
    reader.onload = (e: any) => {
      this.imageUrl = e.target.result;
    };
    reader.readAsDataURL(this.selectedFile);
  }
  protected readonly MovieStatus = MovieStatus;
}
