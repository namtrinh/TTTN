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

export const routes: Routes = [

  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'film-detail/:movieTitle', component: FilmDetailComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'reset-password', component: ResetPasswordComponent},

  {path: 'home', component: HomeComponent},
  {path:'checkout', component:CheckoutInfComponent},
  {path:'payment-success', component:PaymentSuccessComponent},

  {path:'manage', component:ViewManageComponent, children: [
      {path:'movie', component:MovieManageComponent},
      {path:'room', component:RoomManageComponent},
      {path:'showtime', component:ShowtimeManageComponent},

    ]},

  {path:'checkTicket',component:CheckinComponent},



  {path: '???', component: _401Component}
];
