import { Component } from '@angular/core';
import {AuthService} from '../../service/auth.service';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.component.html',
  standalone: true,
  styleUrl: './login.component.css'
})
export class LoginComponent {
    constructor(private authService: AuthService) {
    }

    login(username:string, password:string){
      this.authService.login(username,password).subscribe((data:any) =>{
        const tokenKey = data.result;
        localStorage.setItem("auth_token", tokenKey);
      })
    }
}
