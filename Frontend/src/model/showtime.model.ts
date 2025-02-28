import {Movie} from './movie.model';
import {Room} from './room.model';

export class Showtime{
  showtimeId!: string;
  time_start!:string;
  time_end!:string;

  movieId!:string;
  roomId!:string;
  roomName!:string;
  roomType!:string;
  movie!:Partial<Movie>
  room!:Partial<Room>
}
