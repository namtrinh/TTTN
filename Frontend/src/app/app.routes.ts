import {Routes} from '@angular/router';
import {HomeComponent} from '../view/user/home/home.component';
import {LoginComponent} from '../auth/login/login.component';
import {FilmDetailComponent} from '../view/user/film-detail/film-detail.component';
import {ViewAdminComponent} from '../view/admin/view-admin/view-admin.component';
import {RegisterComponent} from '../auth/register/register.component';
import {_401Component} from '../auth/401/401.component';
import {CustomCanActiveService} from '../service/CustomCanActive.service';
import {ResetPasswordComponent} from '../auth/reset-password/reset-password.component';
import {ViewManageComponent} from '../view/manager/view-manage/view-manage.component';
import {MovieManageComponent} from '../view/manager/movie-manage/movie-manage.component';

export const routes: Routes = [

  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'film-detail/:movieTitle', component: FilmDetailComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'reset-password', component: ResetPasswordComponent},

  {path: 'home', component: HomeComponent},

  {path: 'admin', component: ViewAdminComponent, canActivate: [CustomCanActiveService], data: {role: 1}},

  {path:'manage', component:ViewManageComponent, children: [
      {path:'movie', component:MovieManageComponent},
    ]},



  {path: '???', component: _401Component}
];
