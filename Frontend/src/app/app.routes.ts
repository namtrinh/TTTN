import {Routes} from '@angular/router';

import {_401Component} from '../auth/401/401.component';


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
    loadChildren: () => import('../view/manager/manage.routes').then(m => m.routes)
  },

  {
    path: 'checkTicket',
    loadComponent: () => import('../view/manager/checkin/checkin.component')
      .then(m => m.CheckinComponent)
  },


  {path: '???', component: _401Component}
];
