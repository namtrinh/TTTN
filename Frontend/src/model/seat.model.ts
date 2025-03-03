export class Seat{
  seatId!: string;
 // theater: Theater;  // Assuming the Theater object is included in the Seat
  seatNumber!: string;
  seatType!: 'REGULAR' | 'VIP';
  seatStatus!: 'AVAILABLE' | 'RESERVED';
  time_selected!:string;
}
