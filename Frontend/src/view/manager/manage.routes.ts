import {Routes} from '@angular/router';
import {ViewManageComponent} from './view-manage/view-manage.component';
import {CustomCanActiveService} from '../../service/CustomCanActive.service';
import {MovieManageComponent} from './movie-manage/movie-manage.component';
import {RoomManageComponent} from './room-manage/room-manage.component';
import {ShowtimeManageComponent} from './showtime-manage/showtime-manage.component';

export const routes: Routes = [
  {
    path: '', component: ViewManageComponent, canActivate: [CustomCanActiveService], data: {role: 1}, children: [
      {
        path: '',
        loadComponent: () => import('../manager/dashboard-manage/dashboard-manage.component').then(m => m.DashboardManageComponent)
      },

      {
        path: 'movie',
        loadComponent: () => import('../manager/movie-manage/movie-manage.component').then(m => m.MovieManageComponent)
      },

      {
        path: 'room',
        loadComponent: () => import('../manager/room-manage/room-manage.component').then(m => m.RoomManageComponent)
      },

      {
        path: 'showtime',
        loadComponent: () => import('../manager/showtime-manage/showtime-manage.component').then(m => m.ShowtimeManageComponent)
      }
    ]
  },
]
