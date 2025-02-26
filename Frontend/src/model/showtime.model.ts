import {Movie} from './movie.model';

export class Showtime{
  showtimeId!: string;
  time_start!:string;
  time_end!:string;
  movie!:{ movieStatus: string; showtime: string; movieId: string; movieName: string }
  movieId!:string;
}
