import {Component, OnInit} from '@angular/core';
import {FooterComponent} from '../footer/footer.component';

@Component({
  selector: 'app-home',
  imports: [
    FooterComponent
  ],
  templateUrl: './home.component.html',
  standalone: true,
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  videoUrl!: string;

  constructor() { }

  ngOnInit(): void {
    // Thêm tham số timestamp vào URL để làm mới video mỗi khi tải lại trang
    this.videoUrl = `https://www.youtube.com/embed/TcMBFSGVi1c?autoplay=1&mute=0&loop=1&playlist=TcMBFSGVi1c&timestamp=${new Date().getTime()}`;
  }

}
