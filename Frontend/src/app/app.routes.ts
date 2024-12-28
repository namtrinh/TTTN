import {Routes} from '@angular/router';
import {HomeComponent} from '../view/user/home/home.component';
import {LoginComponent} from '../auth/login/login.component';
import {FilmDetailComponent} from '../view/user/film-detail/film-detail.component';
import {ViewAdminComponent} from '../view/admin/view-admin/view-admin.component';

export const routes: Routes = [

  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent},
  {path: 'film-detail/:movieTitle', component: FilmDetailComponent},
  {path: 'admin', component: ViewAdminComponent}
];
