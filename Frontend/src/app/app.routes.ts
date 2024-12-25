import { Routes } from '@angular/router';
import {HomeComponent} from '../view/home/home.component';
import {LoginComponent} from '../auth/login/login.component';

export const routes: Routes = [

  {path: '', redirectTo: 'login', pathMatch: 'full'},
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent }
];
