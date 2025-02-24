import {Component, OnInit} from '@angular/core';
import {RoomService} from '../../../service/room.service';
import {Room, RoomType} from '../../../model/room.model';
import {FormsModule} from '@angular/forms';
import {CurrencyPipe, DecimalPipe} from '@angular/common';

@Component({
  selector: 'app-room-manage',
  imports: [
    FormsModule,
    DecimalPipe,
  ],
  templateUrl: './room-manage.component.html',
  standalone: true,
  styleUrl: './room-manage.component.css'
})
export class RoomManageComponent implements OnInit{
  ngOnInit(): void {
    this.getAll()
  }

  constructor(private roomService:RoomService) {
  }

  rooms:Room[] = []
  room:Room = new Room();

  getAll(){
    this.roomService.getAll().subscribe((data:any) =>{
      this.rooms = data.result;
      console.log(this.rooms)
    }, error => {
      console.log(error.error.message)
    })
  }

  createRoom(){
    this.roomService.createRoom(this.room).subscribe(data => this.getAll(),
      error => {
      alert(error.error.message)
      })
  }

  deleteRoom(id:string){
    window.confirm("Are you sure want to delete this room!")
    {
      this.roomService.deleteById(id).subscribe(data => {
        this.getAll();
      })
    }
  }

  findRoom(id:string){
    this.roomService.getById(id).subscribe((data:any) => {
      this.room = data.result;
      console.log(this.room.roomId)
    })
  }

  updateRoom(id: string, room: Room): void {
    console.log(id);
    this.roomService.updateById(id, room).subscribe((data: any) => {
      const index: number = this.rooms.findIndex(room => room.roomId === id);
      if (index !== -1) {
        this.rooms[index] = data.result;
      }
    });
  }

  protected readonly RoomType = RoomType;
}
