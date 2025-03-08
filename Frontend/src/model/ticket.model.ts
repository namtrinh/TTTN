export class Ticket {
  ticketId!: string
  movieId!: string
  movieName!: string
  showtime!: string
  seatNumber!: string
  seatPrice!: number
  ticketStatus!: TicketStatus
}

export enum TicketStatus {
  CHECK_IN = "CHECK_IN",
  NOT_CHECK_IN = "NOT_CHECK_IN"
}
