import {Component} from '@angular/core';
import {AuthService} from '../../service/auth.service';
import {FormsModule} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
  standalone: true,
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private authService: AuthService,
              private router: Router) {
  }

  email: string = '';
  password: string = '';
  message!: string;
  showFormCode: boolean = false;

  loginn() {
    this.authService.login(this.email, this.password).subscribe(
      (data: any) => {
        setTimeout(() => {
          this.showFormCode = true;
        }, 500)
      }, error => {
        this.message = error.error.message;
      }
    );
  }

  verifyCode(code:number){
    this.authService.verifyCode(this.email, code).subscribe((data:any) => {
      const tokenKey = data.result.token;
      if (tokenKey !== null) {
        localStorage.setItem("auth_token", tokenKey);
        console.log(tokenKey);
        this.router.navigate(['/home']);
      }
    },error => {
      this.message = error.error.message;
    })
  }

  onSubmit() {
    this.loginn();
  }

  getVerificationCode(form: any) {
    const code = +Object.values(form.value).join('');
    console.log('Verification Code:', code);
    this.verifyCode(code)
  }

  moveFocus(event: any, nextInput: HTMLInputElement | null): void {
    if (event.target.value.length === 1 && nextInput) {
      nextInput.focus();
    }
  }

}

