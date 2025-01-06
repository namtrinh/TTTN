import {Component} from '@angular/core';
import {AuthService} from '../../service/auth.service';
import {Router, RouterLink} from '@angular/router';
import {LoginRequest} from '../../modelDto/LoginRequest';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-register',
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './register.component.html',
  standalone: true,
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  constructor(private _router: Router, private authService: AuthService) {
  }

  email: string = '';
  password: string = '';
  message!: string;
  confirmPassword: string = '';

  register() {
    if (this.password != this.confirmPassword) {
      this.message = "ConfimPassword has been same as password"
    } else {
      this.authService.register(this.email, this.password).subscribe((data: any) => {
        this.message = data.message;
        console.log(this.message);
        setTimeout(() => {
          this._router.navigate(['/login']);
        }, 2000)
      }, (error: any) => {
        this.message = error.error.message
      })
    }
  }

  onSubmit() {
    this.register();
  }
}
