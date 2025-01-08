import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../service/auth.service';

@Component({
  selector: 'app-reset-password',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './reset-password.component.html',
  standalone: true,
  styleUrl: './reset-password.component.css'
})
export class ResetPasswordComponent implements OnInit {

  constructor(private activeRouter: ActivatedRoute,
              private authService: AuthService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.activeRouter.queryParams.subscribe(params => {
      this.email = params['email'];
      this.resetKey = params['reset_key'];
    })
  }

  email: string = '';
  password: string = '';
  message!: string;
  confirmPassword: string = '';
  resetKey!: string;

  resetPassword() {
    if (this.password === this.confirmPassword) {
      this.authService.resetPassword(this.email, this.password, this.resetKey).subscribe((data: any) => {
        alert("Change password successfully! Please login again.");
        this.router.navigate(['/login']);
      }, errror => {
        this.message = errror.error.message;
      })
    } else {
      this.message = "ConfimPassword has been same as password"
    }
  }


  onSubmit() {
    this.resetPassword();
  }


}
