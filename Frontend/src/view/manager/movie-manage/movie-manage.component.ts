import {Component, HostListener, OnInit} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {MovieService} from '../../../service/movie.service';
import {Movie, MovieStatus} from '../../../model/movie.model';
import {RouterLink, RouterOutlet} from '@angular/router';
import {tap} from 'rxjs';
import {normalizeFileReplacements} from '@angular-devkit/build-angular/src/utils';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-movie-manage',
  imports: [FormsModule, CommonModule],
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

  searchTerm: string = '';
  isLoading: boolean = false;
  movies: Movie[] = [];
  page: number = 0;
  size: number = 10;
  film: Movie = new Movie();
  movie: Movie = new Movie();
  selectedFile: File | null = null;
  imageUrl: string | null | undefined;


  @HostListener('window:scroll', [])
  onScroll(): void {
    if (!this.searchTerm) {
      const {scrollTop, scrollHeight} = document.documentElement;
      const windowHeight = window.innerHeight;
      if (scrollTop + windowHeight >= scrollHeight - 100 && !this.isLoading) {
        this.isLoading = true;
        this.page++;
        this.getAll();
      }
    }
  }

  getAll() {
    this.isLoading = true;
    this.movieService.getAll(this.page, this.size).subscribe((data: any) => {
      const newmovies = data.result.content;
      this.movies.push(...newmovies)
      console.log(this.movies);
      console.log(localStorage.getItem('auth_token'))
      this.isLoading = false;
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
        this.page = 0;
        this.getAll();
      }
    });
  }

  findById(id: string) {
    this.movieService.getById(id).subscribe((data: any) => {
      this.film = data.result;
      this.imageUrl = this.film.posterUrl;
      console.log(this.film)
    }, error => {
      console.log(error)
    })
  }

  updateMovie(id: string, movie: Movie) {
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
    if (movie.productionCompany) formData.append('productionCompany', movie.productionCompany);
    if (movie.trailerUrl) formData.append('trailerUrl', movie.trailerUrl);
    formData.append('movieStatus', movie.movieStatus);

    if (this.selectedFile) {
      formData.append('posterUrl', this.selectedFile);
    } else {
      if (this.imageUrl) {
        formData.append('posterUrl', this.imageUrl)
      }
    }
    this.movieService.updateById(id, formData).subscribe((data: any) => {
      const index = this.movies.findIndex(movie => movie.movieId === id);
      if (index !== -1) {
        this.movies[index] = data.result;
      }
      this.imageUrl = data.result.posterUrl;
    })
  }

  deleteMovie(id: string, name: string) {
    if (window.confirm("Are you sure you want to delete this movie: " + name)) {
      this.movieService.deleteById(id).subscribe((data) => {
        const index = this.movies.findIndex(movie => movie.movieId === id);
        if (index !== -1) {
          this.movies.splice(index, 1);
        }
      })
    }
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
