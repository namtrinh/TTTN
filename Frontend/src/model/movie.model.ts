import {Showtime} from './showtime.model';


export class Movie {
  movieId!: string;
  movieName!: string;
  movieTitle?: string;
  movieDescription?: string;
  genre?: string;
  director?: string;
  country?: string;
  releaseDate?: Date;
  duration?: number;
  rating?: number;
  posterUrl?: string;
  productionCompany?: string;
  trailerUrl?: string;
  movieStatus!: MovieStatus;
  showtimes!: Showtime[]
}

// Enum cho trạng thái phim
export enum MovieStatus {
  SHOWING = 'SHOWING',
  FINISH = 'FINISH',
}

