import {Seat} from './seat.model';

export class Room {
  roomId!: string;
  roomName!: string;
  totalSeat!: number;
  theaterId!: string;
  roomPrice!: number;
  roomType!:RoomType;
  seat!: Partial<Seat[]>;
}

export enum RoomType{
  STANDARD='STANDARD',
  IMAX ='IMAX',
  COUPLE ='COUPLE',
}
