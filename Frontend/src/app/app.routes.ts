import {Routes} from '@angular/router';
import {HomeComponent} from '../view/user/home/home.component';
import {LoginComponent} from '../auth/login/login.component';
import {FilmDetailComponent} from '../view/user/film-detail/film-detail.component';
import {RegisterComponent} from '../auth/register/register.component';
import {_401Component} from '../auth/401/401.component';
import {ResetPasswordComponent} from '../auth/reset-password/reset-password.component';
import {ViewManageComponent} from '../view/manager/view-manage/view-manage.component';
import {MovieManageComponent} from '../view/manager/movie-manage/movie-manage.component';
import {RoomManageComponent} from '../view/manager/room-manage/room-manage.component';
import {ShowtimeManageComponent} from '../view/manager/showtime-manage/showtime-manage.component';
import {CheckoutInfComponent} from '../view/user/payment/checkout-inf/checkout-inf.component';
import {PaymentSuccessComponent} from '../view/user/payment/payment-success/payment-success.component';
import {CheckinComponent} from '../view/manager/checkin/checkin.component';
import {CustomCanActiveService} from '../service/CustomCanActive.service';

export const routes: Routes = [

  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {
    path: 'login',
    loadComponent: () => import('../auth/login/login.component')
      .then(m => m.LoginComponent)
  },

  {
    path: 'film-detail/:movieTitle',
    loadComponent: () => import('../view/user/film-detail/film-detail.component')
      .then(m => m.FilmDetailComponent)
  },

  {
    path: 'register',
    loadComponent: () => import('../auth/register/register.component')
      .then(m => m.RegisterComponent)
  },

  {
    path: 'reset-password',
    loadComponent: () => import('../auth/reset-password/reset-password.component')
      .then(m => m.ResetPasswordComponent)
  },

  {
    path: 'home',
    loadComponent: () => import('../view/user/home/home.component')
      .then(m => m.HomeComponent)
  },

  {
    path: 'checkout',
    loadComponent: () => import('../view/user/payment/checkout-inf/checkout-inf.component')
      .then(m => m.CheckoutInfComponent)
  },


  {
    path: 'payment-success',
    loadComponent: () => import('../view/user/payment/payment-success/payment-success.component')
      .then(m => m.PaymentSuccessComponent)
  },

  {
    path: 'manage',
    loadChildren: () => import('../view/manager/manage.routes').then(m => m.routes),
    canActivate: [CustomCanActiveService],
    data: {role: 1}
  },

  {
    path: 'checkTicket',
    loadComponent: () => import('../view/manager/checkin/checkin.component')
      .then(m => m.CheckinComponent)
  },


  {path: '???', component: _401Component}
];
