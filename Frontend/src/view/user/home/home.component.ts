import { Component } from '@angular/core';
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
export class HomeComponent {

}
