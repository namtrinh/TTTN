import { Component } from '@angular/core';
import {AuthService} from '../../service/auth.service';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule
  ],
  templateUrl: './login.component.html',
  standalone: true,
  styleUrl: './login.component.css'
})
export class LoginComponent {
    constructor(private authService: AuthService,
                private router:Router) {
    }
    username:string = '';
    password:string = '';
    message!:string;

  loginn() {
    this.authService.login(this.username, this.password).subscribe(
      (data: any) => {
        const tokenKey = data.result;
        if (tokenKey !== null) {
          localStorage.setItem("auth_token", tokenKey);
          console.log(tokenKey);
          this.router.navigate(['/home']);
        }else{
          alert(this.message)
        }
      },error => {
       this.message = error.error.message
      }
    );
  }
  onSubmit(){
      this.loginn();
    }
}
