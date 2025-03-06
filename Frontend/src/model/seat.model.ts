export class Seat{
  seatId!: string;
 // theater: Theater;  // Assuming the Theater object is included in the Seat
  seatNumber!: string;
  seatType!: 'REGULAR' | 'VIP';
  seatStatus!: SeatStatus
  time_selected!:string;
}

export enum SeatStatus{
  AVAILABLE = 'AVAILABLE',
  RESERVED = 'RESERVED'
}
