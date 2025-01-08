import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-view-manage',
  imports: [
    RouterOutlet,
    RouterLink
  ],
  templateUrl: './view-manage.component.html',
  standalone: true,
  styleUrl: './view-manage.component.css'
})
export class ViewManageComponent {

}
